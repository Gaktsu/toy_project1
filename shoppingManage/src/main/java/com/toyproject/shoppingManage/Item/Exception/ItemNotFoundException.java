package com.toyproject.shoppingManage.Item.Exception;

import com.toyproject.shoppingManage.ErrorCode;
import lombok.Getter;

@Getter
public class ItemNotFoundException extends RuntimeException {
    private final ErrorCode errorCode;

    public ItemNotFoundException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
