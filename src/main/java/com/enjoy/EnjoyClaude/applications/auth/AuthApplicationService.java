package com.enjoy.EnjoyClaude.applications.auth;

import com.enjoy.EnjoyClaude.domains.auth.RefreshTokenRepository;
import com.enjoy.EnjoyClaude.domains.common.exception.*;
import com.enjoy.EnjoyClaude.domains.user.User;
import com.enjoy.EnjoyClaude.domains.user.UserRepository;
import com.enjoy.EnjoyClaude.domains.user.UserService;
import com.enjoy.EnjoyClaude.interfaces.dto.request.LoginViewRequest;
import com.enjoy.EnjoyClaude.interfaces.dto.request.RefreshTokenViewRequest;
import com.enjoy.EnjoyClaude.interfaces.dto.request.SignupViewRequest;
import com.enjoy.EnjoyClaude.interfaces.dto.response.TokenViewResponse;
import com.enjoy.EnjoyClaude.interfaces.dto.response.UserDetailViewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthApplicationService {
    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthAssembler authAssembler;

    @Transactional
    public UserDetailViewResponse signup(SignupViewRequest request) {
        // 이메일 중복 확인
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException(request.getEmail());
        }

        // 사용자 생성
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = authAssembler.fromSignupRequest(request, encodedPassword);

        // 기본 역할 부여
        user = userService.createUserWithDefaultRole(user);

        // 저장
        User savedUser = userRepository.save(user);

        return authAssembler.toUserDetailResponse(savedUser);
    }

    @Transactional(readOnly = true)
    public TokenViewResponse login(LoginViewRequest request) {
        try {
            // 인증
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            // 사용자 조회
            User user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다: " + request.getEmail()));

            // 활성화 확인
            if (!user.isEnabled()) {
                throw new AccountDisabledException();
            }

            // JWT 토큰 생성
            return jwtService.generateTokens(user);
        } catch (AuthenticationException e) {
            throw new InvalidCredentialsException();
        }
    }

    @Transactional
    public TokenViewResponse refreshToken(RefreshTokenViewRequest request) {
        return jwtService.refreshAccessToken(request.getRefreshToken());
    }

    @Transactional
    public void logout(String refreshToken) {
        if (refreshToken != null && !refreshToken.isEmpty()) {
            refreshTokenRepository.deleteByToken(refreshToken);
        }
    }
}
