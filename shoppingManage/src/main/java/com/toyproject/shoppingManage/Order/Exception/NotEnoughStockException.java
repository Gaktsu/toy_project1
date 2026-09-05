package com.toyproject.shoppingManage.Order.Exception;

import com.toyproject.shoppingManage.ErrorCode;
import lombok.Getter;

@Getter
public class NotEnoughStockException extends RuntimeException {
    private final ErrorCode errorCode;

    public NotEnoughStockException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
