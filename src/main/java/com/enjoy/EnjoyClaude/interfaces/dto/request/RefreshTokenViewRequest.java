package com.enjoy.EnjoyClaude.interfaces.dto.request;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class RefreshTokenViewRequest {
    @NotBlank(message = "리프레시 토큰은 필수입니다.")
    private final String refreshToken;

    @Builder
    public RefreshTokenViewRequest(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
