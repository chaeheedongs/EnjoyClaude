package com.enjoy.EnjoyClaude.interfaces.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 비밀번호 복잡도 검증 어노테이션
 * ISMS 인증심사 기준 준수:
 * - 최소 10자 이상
 * - 영문 대소문자, 숫자, 특수문자 중 3종류 이상 조합
 * - 연속된 문자/숫자 4자 이상 금지
 */
@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordConstraint {
    String message() default "비밀번호는 10자 이상이며, 영문 대소문자, 숫자, 특수문자 중 3종류 이상을 조합해야 합니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
