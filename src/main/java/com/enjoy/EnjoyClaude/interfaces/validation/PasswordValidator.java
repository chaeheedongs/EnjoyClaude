package com.enjoy.EnjoyClaude.interfaces.validation;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * 비밀번호 복잡도 검증기
 * ISMS 인증심사 기준 적용
 */
public class PasswordValidator implements ConstraintValidator<PasswordConstraint, String> {

    // 영문 대문자 패턴
    private static final Pattern UPPERCASE_PATTERN = Pattern.compile("[A-Z]");
    // 영문 소문자 패턴
    private static final Pattern LOWERCASE_PATTERN = Pattern.compile("[a-z]");
    // 숫자 패턴
    private static final Pattern DIGIT_PATTERN = Pattern.compile("[0-9]");
    // 특수문자 패턴
    private static final Pattern SPECIAL_CHAR_PATTERN = Pattern.compile("[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]");
    // 연속된 문자/숫자 패턴 (4자 이상)
    private static final Pattern SEQUENTIAL_PATTERN = Pattern.compile("(.)\\1{3,}");
    // 연속된 숫자 패턴 (1234, 4321 등)
    private static final Pattern SEQUENTIAL_NUMBERS = Pattern.compile("(0123|1234|2345|3456|4567|5678|6789|7890|9876|8765|7654|6543|5432|4321|3210)");
    // 연속된 문자 패턴 (abcd, dcba 등)
    private static final Pattern SEQUENTIAL_CHARS = Pattern.compile("(abcd|bcde|cdef|defg|efgh|fghi|ghij|hijk|ijkl|jklm|klmn|lmno|mnop|nopq|opqr|pqrs|qrst|rstu|stuv|tuvw|uvwx|vwxy|wxyz|zyxw|yxwv|xwvu|wvut|vuts|utsr|tsrq|srqp|rqpo|qpon|ponm|onml|nmlk|mlkj|lkji|kjih|jihg|ihgf|hgfe|gfed|fedc|edcb|dcba)");

    @Override
    public void initialize(final PasswordConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(final String password, final ConstraintValidatorContext context) {
        if (StringUtils.isBlank(password)) {
            return false;
        }

        // 1. 최소 길이 검증 (10자 이상)
        if (password.length() < 10) {
            setCustomMessage(context, "비밀번호는 최소 10자 이상이어야 합니다.");
            return false;
        }

        // 2. 최대 길이 검증 (128자 이하)
        if (password.length() > 128) {
            setCustomMessage(context, "비밀번호는 최대 128자 이하여야 합니다.");
            return false;
        }

        // 3. 문자 종류 조합 검증 (3종류 이상)
        int typeCount = 0;
        if (UPPERCASE_PATTERN.matcher(password).find()) typeCount++;
        if (LOWERCASE_PATTERN.matcher(password).find()) typeCount++;
        if (DIGIT_PATTERN.matcher(password).find()) typeCount++;
        if (SPECIAL_CHAR_PATTERN.matcher(password).find()) typeCount++;

        if (typeCount < 3) {
            setCustomMessage(context, "비밀번호는 영문 대소문자, 숫자, 특수문자 중 3종류 이상을 조합해야 합니다.");
            return false;
        }

        // 4. 연속된 문자/숫자 금지 (aaaa, 1111 등)
        if (SEQUENTIAL_PATTERN.matcher(password).find()) {
            setCustomMessage(context, "동일한 문자가 4회 이상 연속으로 사용될 수 없습니다.");
            return false;
        }

        // 5. 연속된 숫자 금지 (1234, 4321 등)
        if (SEQUENTIAL_NUMBERS.matcher(password.toLowerCase()).find()) {
            setCustomMessage(context, "연속된 숫자(1234 등)를 사용할 수 없습니다.");
            return false;
        }

        // 6. 연속된 문자 금지 (abcd, dcba 등)
        if (SEQUENTIAL_CHARS.matcher(password.toLowerCase()).find()) {
            setCustomMessage(context, "연속된 문자(abcd 등)를 사용할 수 없습니다.");
            return false;
        }

        // 7. 공백 문자 금지
        if (password.contains(" ")) {
            setCustomMessage(context, "비밀번호에 공백을 사용할 수 없습니다.");
            return false;
        }

        return true;
    }

    private void setCustomMessage(final ConstraintValidatorContext context, final String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    }
}
