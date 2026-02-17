package com.enjoy.EnjoyClaude.interfaces.dto.response;

import lombok.Value;

import java.time.LocalDateTime;
import java.util.Set;

@Value
public class UserDetailViewResponse {
    Long id;
    String email;
    String username;
    Set<String> roles;
    boolean enabled;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
