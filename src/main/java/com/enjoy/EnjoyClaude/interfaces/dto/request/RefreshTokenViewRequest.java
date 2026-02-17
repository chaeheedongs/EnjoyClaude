package com.enjoy.EnjoyClaude.interfaces.dto.request;

import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
public class RefreshTokenViewRequest {
    @NotBlank(message = "리프레시 토큰은 필수입니다.")
    String refreshToken;
}
