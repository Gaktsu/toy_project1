package com.toyproject.shoppingManage.Order.Exception;

import com.toyproject.shoppingManage.ErrorCode;
import lombok.Getter;

@Getter
public class OrderNotFoundException extends RuntimeException {
    private final ErrorCode errorCode;

    public OrderNotFoundException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
