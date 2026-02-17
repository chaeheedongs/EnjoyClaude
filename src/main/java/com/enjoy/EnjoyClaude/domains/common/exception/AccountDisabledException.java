package com.enjoy.EnjoyClaude.domains.common.exception;

public class AccountDisabledException extends BusinessException {
    public AccountDisabledException() {
        super("비활성화된 계정입니다.");
    }
}
