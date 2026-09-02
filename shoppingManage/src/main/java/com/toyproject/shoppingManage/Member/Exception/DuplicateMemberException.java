package com.toyproject.shoppingManage.Member.Exception;

import com.toyproject.shoppingManage.ErrorCode;
import lombok.Getter;

@Getter
public class DuplicateMemberException extends RuntimeException {
    private final ErrorCode errorCode;

    public DuplicateMemberException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
