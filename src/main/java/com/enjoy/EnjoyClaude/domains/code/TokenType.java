package com.enjoy.EnjoyClaude.domains.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 토큰 타입 Enum
 * JWT 인증에서 사용되는 토큰 타입을 정의합니다.
 */
@Getter
@RequiredArgsConstructor
public enum TokenType {
    BEARER("Bearer", "Bearer 토큰");

    private final String code;
    private final String description;

    /**
     * Bearer 토큰인지 확인합니다.
     *
     * @param value 확인할 문자열
     * @return Bearer로 시작하면 true
     */
    public boolean matches(final String value) {
        return value != null && value.startsWith(this.code + " ");
    }

    /**
     * Bearer 토큰에서 실제 토큰 값을 추출합니다.
     *
     * @param bearerToken "Bearer {token}" 형식의 문자열
     * @return 추출된 토큰 값
     */
    public String extractToken(final String bearerToken) {
        if (bearerToken != null && bearerToken.startsWith(this.code + " ")) {
            return bearerToken.substring(this.code.length() + 1);
        }
        return null;
    }

    /**
     * 토큰에 Bearer 접두사를 추가합니다.
     *
     * @param token 토큰 값
     * @return "Bearer {token}" 형식의 문자열
     */
    public String withPrefix(final String token) {
        return this.code + " " + token;
    }
}
