package com.enjoy.EnjoyClaude.interfaces.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TokenViewResponse {
    private final String accessToken;
    private final String refreshToken;
    private final String tokenType;
    private final Long expiresIn;

    @Builder
    public TokenViewResponse(String accessToken, String refreshToken, String tokenType, Long expiresIn) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
    }
}
