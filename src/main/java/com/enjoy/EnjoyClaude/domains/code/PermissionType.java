package com.enjoy.EnjoyClaude.domains.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 권한 타입 Enum
 * 시스템에서 사용되는 권한(Permission)의 종류를 정의합니다.
 */
@Getter
@RequiredArgsConstructor
public enum PermissionType {
    // 사용자 관련 권한
    READ_USERS("READ:users", "사용자 읽기"),
    WRITE_USERS("WRITE:users", "사용자 쓰기"),

    // 역할 관련 권한
    READ_ROLES("READ:roles", "역할 읽기"),
    WRITE_ROLES("WRITE:roles", "역할 쓰기"),

    // 게시물 관련 권한
    READ_POSTS("READ:posts", "게시물 읽기"),
    WRITE_POSTS("WRITE:posts", "게시물 쓰기");

    private final String code;
    private final String description;

    /**
     * 코드 값으로 PermissionType을 찾습니다.
     *
     * @param code 권한 코드 (예: "READ:users")
     * @return PermissionType enum
     * @throws IllegalArgumentException 존재하지 않는 코드인 경우
     */
    public static PermissionType fromCode(final String code) {
        for (final PermissionType type : values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown permission code: " + code);
    }
}
