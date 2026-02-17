package com.enjoy.EnjoyClaude.domains.common.exception;

public class RoleNotFoundException extends BusinessException {
    public RoleNotFoundException(String message) {
        super(message);
    }

    public RoleNotFoundException(Long roleId) {
        super("역할을 찾을 수 없습니다: " + roleId);
    }
}
