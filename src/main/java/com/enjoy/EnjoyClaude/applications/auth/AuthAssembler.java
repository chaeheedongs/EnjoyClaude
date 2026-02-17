package com.enjoy.EnjoyClaude.applications.auth;

import com.enjoy.EnjoyClaude.domains.user.User;
import com.enjoy.EnjoyClaude.interfaces.dto.request.SignupViewRequest;
import com.enjoy.EnjoyClaude.interfaces.dto.response.UserDetailViewResponse;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class AuthAssembler {
    public User fromSignupRequest(SignupViewRequest request, String encodedPassword) {
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

    public UserDetailViewResponse toUserDetailResponse(User user) {
        return new UserDetailViewResponse(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                user.getRoles().stream()
                        .map(role -> role.getName())
                        .collect(Collectors.toSet()),
                user.isEnabled(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
