package com.enjoy.EnjoyClaude.interfaces.dto.response;

import lombok.Value;

@Value
public class TokenViewResponse {
    String accessToken;
    String refreshToken;
    String tokenType;
    Long expiresIn;
}
