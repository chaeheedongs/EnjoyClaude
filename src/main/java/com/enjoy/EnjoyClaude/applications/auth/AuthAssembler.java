package com.enjoy.EnjoyClaude.applications.auth;

import com.enjoy.EnjoyClaude.domains.user.User;
import com.enjoy.EnjoyClaude.interfaces.dto.request.SignupViewRequest;
import com.enjoy.EnjoyClaude.interfaces.dto.response.UserDetailViewResponse;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class AuthAssembler {
    public User fromSignupRequest(final SignupViewRequest request, final String encodedPassword) {
        return new User(
                null,
                request.getEmail(),
                request.getUsername(),
                encodedPassword,
                true,
                null,
                null,
                null
        );
    }

    public UserDetailViewResponse toUserDetailResponse(final User user) {
        return UserDetailViewResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .roles(user.getRoles().stream()
                        .map(role -> role.getName())
                        .collect(Collectors.toSet()))
                .enabled(user.isEnabled())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
