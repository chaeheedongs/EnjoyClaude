package com.enjoy.EnjoyClaude.applications.auth;

import com.enjoy.EnjoyClaude.domains.auth.RefreshToken;
import com.enjoy.EnjoyClaude.domains.auth.RefreshTokenRepository;
import com.enjoy.EnjoyClaude.domains.common.exception.ExpiredTokenException;
import com.enjoy.EnjoyClaude.domains.common.exception.InvalidTokenException;
import com.enjoy.EnjoyClaude.domains.common.exception.PermissionDeniedException;
import com.enjoy.EnjoyClaude.domains.user.User;
import com.enjoy.EnjoyClaude.infrastructures.security.ClientInfoExtractor;
import com.enjoy.EnjoyClaude.infrastructures.security.JwtProperties;
import com.enjoy.EnjoyClaude.infrastructures.security.JwtTokenProvider;
import com.enjoy.EnjoyClaude.interfaces.dto.response.TokenViewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final JwtTokenProvider tokenProvider;
    private final JwtProperties jwtProperties;
    private final RefreshTokenRepository refreshTokenRepository;
    private final ClientInfoExtractor clientInfoExtractor;

    @Transactional
    public TokenViewResponse generateTokens(User user, HttpServletRequest request) {
        String accessToken = tokenProvider.generateAccessToken(user.getEmail(), user.getId());
        String refreshToken = tokenProvider.generateRefreshToken(user.getEmail(), user.getId());

        // 클라이언트 정보 추출
        String ipAddress = clientInfoExtractor.getClientIpAddress(request);
        String userAgent = clientInfoExtractor.getUserAgent(request);

        // 기존 리프레시 토큰 삭제
        refreshTokenRepository.deleteByUserId(user.getId());

        // 새 리프레시 토큰 저장 (IP, User-Agent 포함)
        RefreshToken refreshTokenEntity = new RefreshToken(
                null,
                refreshToken,
                user.getId(),
                LocalDateTime.now().plusSeconds(jwtProperties.getRefreshTokenValidity()),
                LocalDateTime.now(),
                ipAddress,
                userAgent
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
    public TokenViewResponse refreshAccessToken(String refreshToken, HttpServletRequest request) {
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

        // IP/User-Agent 검증
        String currentIp = clientInfoExtractor.getClientIpAddress(request);
        String currentUserAgent = clientInfoExtractor.getUserAgent(request);
        if (!storedToken.matchesClientInfo(currentIp, currentUserAgent)) {
            // 클라이언트 정보가 다르면 보안상 토큰 삭제 및 거부
            refreshTokenRepository.deleteByToken(refreshToken);
            throw new PermissionDeniedException("다른 기기에서 토큰이 사용되었습니다. 다시 로그인해주세요.");
        }

        // 새 액세스 토큰 생성
        String email = tokenProvider.getEmailFromToken(refreshToken);
        Long userId = tokenProvider.getUserIdFromToken(refreshToken);
        String newAccessToken = tokenProvider.generateAccessToken(email, userId);

        // ✅ Refresh Token Rotation: 새로운 Refresh Token 발급
        String newRefreshToken = tokenProvider.generateRefreshToken(email, userId);

        // 기존 Refresh Token 삭제
        refreshTokenRepository.deleteByToken(refreshToken);

        // 새로운 Refresh Token 저장
        RefreshToken newRefreshTokenEntity = new RefreshToken(
                null,
                newRefreshToken,
                userId,
                LocalDateTime.now().plusSeconds(jwtProperties.getRefreshTokenValidity()),
                LocalDateTime.now(),
                currentIp,
                currentUserAgent
        );
        refreshTokenRepository.save(newRefreshTokenEntity);

        return new TokenViewResponse(
                newAccessToken,
                newRefreshToken,  // 새로운 Refresh Token 반환
                "Bearer",
                jwtProperties.getAccessTokenValidity()
        );
    }
}
