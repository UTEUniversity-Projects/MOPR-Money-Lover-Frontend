package com.moneylover.constants;

import lombok.Getter;

@Getter
public enum ErrorCode {

    ACCOUNT_EMAIL_EXISTED("ERROR-ACCOUNT-0003", "Account existed by email");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
