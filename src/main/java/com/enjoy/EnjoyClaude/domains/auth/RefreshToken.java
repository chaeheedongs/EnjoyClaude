package com.enjoy.EnjoyClaude.domains.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {
    private Long id;
    private String token;
    private Long userId;
    private LocalDateTime expiresAt;
    private LocalDateTime createdAt;
    private String ipAddress;      // IP 주소 추가
    private String userAgent;      // User-Agent 추가

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }

    public void verify() {
        if (isExpired()) {
            throw new IllegalStateException("토큰이 만료되었습니다.");
        }
    }

    // IP/User-Agent 검증 메서드
    public boolean matchesClientInfo(String ipAddress, String userAgent) {
        return this.ipAddress.equals(ipAddress) && this.userAgent.equals(userAgent);
    }
}
