package com.enjoy.EnjoyClaude.interfaces.dto.request;

import com.enjoy.EnjoyClaude.interfaces.validation.PasswordConstraint;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
public class SignupViewRequest {
    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "올바른 이메일 형식이어야 합니다.")
    @Size(max = 100, message = "이메일은 100자 이하여야 합니다.")
    private final String email;

    @NotBlank(message = "사용자명은 필수입니다.")
    @Size(min = 2, max = 50, message = "사용자명은 2-50자 사이여야 합니다.")
    @Pattern(regexp = "^[a-zA-Z0-9가-힣_-]+$", message = "사용자명은 한글, 영문, 숫자, _, - 만 사용 가능합니다.")
    private final String username;

    @NotBlank(message = "비밀번호는 필수입니다.")
    @PasswordConstraint  // ISMS 기준 비밀번호 복잡도 검증
    private final String password;

    @Builder
    public SignupViewRequest(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }
}
