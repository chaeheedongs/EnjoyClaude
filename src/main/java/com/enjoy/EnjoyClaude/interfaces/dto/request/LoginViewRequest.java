package com.enjoy.EnjoyClaude.interfaces.dto.request;

import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Value
public class LoginViewRequest {
    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "올바른 이메일 형식이어야 합니다.")
    String email;

    @NotBlank(message = "비밀번호는 필수입니다.")
    String password;
}
