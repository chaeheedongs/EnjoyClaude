package com.enjoy.EnjoyClaude.applications.auth;

import com.enjoy.EnjoyClaude.domains.auth.RefreshToken;
import com.enjoy.EnjoyClaude.domains.auth.RefreshTokenRepository;
import com.enjoy.EnjoyClaude.domains.common.exception.ExpiredTokenException;
import com.enjoy.EnjoyClaude.domains.common.exception.InvalidTokenException;
import com.enjoy.EnjoyClaude.domains.user.User;
import com.enjoy.EnjoyClaude.infrastructures.security.JwtProperties;
import com.enjoy.EnjoyClaude.infrastructures.security.JwtTokenProvider;
import com.enjoy.EnjoyClaude.interfaces.dto.response.TokenViewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final JwtTokenProvider tokenProvider;
    private final JwtProperties jwtProperties;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public TokenViewResponse generateTokens(User user) {
        String accessToken = tokenProvider.generateAccessToken(user.getEmail(), user.getId());
        String refreshToken = tokenProvider.generateRefreshToken(user.getEmail(), user.getId());

        // 기존 리프레시 토큰 삭제
        refreshTokenRepository.deleteByUserId(user.getId());

        // 새 리프레시 토큰 저장
        RefreshToken refreshTokenEntity = new RefreshToken(
                null,
                refreshToken,
                user.getId(),
                LocalDateTime.now().plusSeconds(jwtProperties.getRefreshTokenValidity()),
                LocalDateTime.now()
        );
        refreshTokenRepository.save(refreshTokenEntity);

        return new TokenViewResponse(
                accessToken,
                refreshToken,
                "Bearer",
                jwtProperties.getAccessTokenValidity()
        );
    }

    @Transactional
    public TokenViewResponse refreshAccessToken(String refreshToken) {
        // 토큰 검증
        tokenProvider.validateToken(refreshToken);

        // 리프레시 토큰 조회
        RefreshToken storedToken = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new InvalidTokenException("유효하지 않은 리프레시 토큰입니다."));

        // 만료 확인
        if (storedToken.isExpired()) {
            refreshTokenRepository.deleteByToken(refreshToken);
            throw new ExpiredTokenException("리프레시 토큰이 만료되었습니다.");
        }

        // 새 액세스 토큰 생성
        String email = tokenProvider.getEmailFromToken(refreshToken);
        Long userId = tokenProvider.getUserIdFromToken(refreshToken);
        String newAccessToken = tokenProvider.generateAccessToken(email, userId);

        return new TokenViewResponse(
                newAccessToken,
                refreshToken,
                "Bearer",
                jwtProperties.getAccessTokenValidity()
        );
    }
}
