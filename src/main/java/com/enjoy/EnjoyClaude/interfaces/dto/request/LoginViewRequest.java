package com.enjoy.EnjoyClaude.interfaces.dto.request;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
public class LoginViewRequest {
    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "올바른 이메일 형식이어야 합니다.")
    @Size(max = 100, message = "이메일은 100자 이하여야 합니다.")
    private final String email;

    @NotBlank(message = "비밀번호는 필수입니다.")
    @Size(max = 128, message = "비밀번호는 128자 이하여야 합니다.")
    private final String password;

    @Builder
    public LoginViewRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
