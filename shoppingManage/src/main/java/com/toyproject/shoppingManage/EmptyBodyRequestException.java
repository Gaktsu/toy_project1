package com.toyproject.shoppingManage;

import lombok.Getter;

@Getter
public class EmptyBodyRequestException extends RuntimeException {
    private final ErrorCode errorCode;

    public EmptyBodyRequestException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
