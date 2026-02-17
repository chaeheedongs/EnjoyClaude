package com.enjoy.EnjoyClaude.domains.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 기본값 Enum
 * 시스템에서 사용되는 기본값들을 정의합니다.
 */
@Getter
@RequiredArgsConstructor
public enum DefaultValue {
    UNKNOWN("unknown", "알 수 없음"),
    ANONYMOUS("anonymous", "익명 사용자");

    private final String value;
    private final String description;
}
