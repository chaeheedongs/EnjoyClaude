package com.enjoy.EnjoyClaude.applications.auth;

import com.enjoy.EnjoyClaude.domains.auth.RefreshTokenRepository;
import com.enjoy.EnjoyClaude.domains.auth.TokenBlacklist;
import com.enjoy.EnjoyClaude.domains.auth.TokenBlacklistRepository;
import com.enjoy.EnjoyClaude.domains.common.exception.*;
import com.enjoy.EnjoyClaude.domains.user.User;
import com.enjoy.EnjoyClaude.domains.user.UserRepository;
import com.enjoy.EnjoyClaude.domains.user.UserService;
import com.enjoy.EnjoyClaude.infrastructures.security.JwtProperties;
import com.enjoy.EnjoyClaude.infrastructures.security.JwtTokenProvider;
import com.enjoy.EnjoyClaude.interfaces.dto.request.LoginViewRequest;
import com.enjoy.EnjoyClaude.interfaces.dto.request.RefreshTokenViewRequest;
import com.enjoy.EnjoyClaude.interfaces.dto.request.SignupViewRequest;
import com.enjoy.EnjoyClaude.interfaces.dto.response.TokenViewResponse;
import com.enjoy.EnjoyClaude.interfaces.dto.response.UserDetailViewResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthApplicationService {
    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenBlacklistRepository tokenBlacklistRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtProperties jwtProperties;
    private final AuthAssembler authAssembler;

    @Transactional
    public UserDetailViewResponse signup(final SignupViewRequest request) {
        // 이메일 중복 확인
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException(request.getEmail());
        }

        // 사용자 생성
        final String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = authAssembler.fromSignupRequest(request, encodedPassword);

        // 기본 역할 부여
        user = userService.createUserWithDefaultRole(user);

        // 저장
        final User savedUser = userRepository.save(user);

        return authAssembler.toUserDetailResponse(savedUser);
    }

    @Transactional(readOnly = true)
    public TokenViewResponse login(final LoginViewRequest request, final HttpServletRequest httpRequest) {
        try {
            // 인증
            final Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            // 사용자 조회
            final User user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다: " + request.getEmail()));

            // 활성화 확인
            if (!user.isEnabled()) {
                throw new AccountDisabledException();
            }

            // JWT 토큰 생성
            return jwtService.generateTokens(user, httpRequest);
        } catch (final AuthenticationException e) {
            throw new InvalidCredentialsException();
        }
    }

    @Transactional
    public TokenViewResponse refreshToken(final RefreshTokenViewRequest request, final HttpServletRequest httpRequest) {
        return jwtService.refreshAccessToken(request.getRefreshToken(), httpRequest);
    }

    @Transactional
    public void logout(final String refreshToken, final String accessToken) {
        // Refresh Token 삭제
        if (refreshToken != null && !refreshToken.isEmpty()) {
            refreshTokenRepository.deleteByToken(refreshToken);
        }

        // Access Token을 블랙리스트에 추가
        if (accessToken != null && !accessToken.isEmpty()) {
            try {
                jwtTokenProvider.validateToken(accessToken);

                // Access Token 유효시간만큼 블랙리스트에 보관
                final TokenBlacklist blacklistedToken = new TokenBlacklist(
                        null,
                        accessToken,
                        java.time.LocalDateTime.now().plusSeconds(jwtProperties.getAccessTokenValidity()),
                        java.time.LocalDateTime.now()
                );
                tokenBlacklistRepository.save(blacklistedToken);
            } catch (final Exception e) {
                // 이미 만료된 토큰은 블랙리스트에 추가하지 않음
                log.debug("토큰이 이미 만료되었거나 유효하지 않습니다: {}", e.getMessage());
            }
        }
    }
}
