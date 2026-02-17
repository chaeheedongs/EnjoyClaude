package com.enjoy.EnjoyClaude.domains.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 역할 타입 Enum
 * 시스템에서 사용되는 역할(Role)의 종류를 정의합니다.
 */
@Getter
@RequiredArgsConstructor
public enum RoleType {
    USER("ROLE_USER", "일반 사용자"),
    ADMIN("ROLE_ADMIN", "관리자");

    private final String code;
    private final String description;

    /**
     * 코드 값으로 RoleType을 찾습니다.
     *
     * @param code 역할 코드 (예: "ROLE_USER")
     * @return RoleType enum
     * @throws IllegalArgumentException 존재하지 않는 코드인 경우
     */
    public static RoleType fromCode(final String code) {
        for (final RoleType type : values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown role code: " + code);
    }
}
