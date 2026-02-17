package com.enjoy.EnjoyClaude.interfaces.dto.response;

import lombok.Value;

import java.time.LocalDateTime;
import java.util.Map;

@Value
public class ErrorViewResponse {
    LocalDateTime timestamp;
    int status;
    String error;
    String message;
    String path;
    Map<String, String> fieldErrors;

    public ErrorViewResponse(int status, String error, String message, String path, Map<String, String> fieldErrors) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.fieldErrors = fieldErrors;
    }
}
