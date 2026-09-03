package com.toyproject.shoppingManage;

import com.toyproject.shoppingManage.Item.Exception.ItemNotFoundException;
import com.toyproject.shoppingManage.Member.Exception.DuplicateMemberException;
import com.toyproject.shoppingManage.Member.Exception.MemberNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateMemberException.class)
    public ResponseEntity<?> handleDuplicateMemberException(DuplicateMemberException e){
        ErrorCode errorCode = e.getErrorCode();
        return returnResponseEntity(errorCode);
    }

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<?> handleMemberNotFoundException(MemberNotFoundException e){
        ErrorCode errorCode = e.getErrorCode();
        return returnResponseEntity(errorCode);
    }

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<?> handleItemNotFoundException(ItemNotFoundException e){
        ErrorCode errorCode = e.getErrorCode();
        return returnResponseEntity(errorCode);
    }

    public ResponseEntity<?> returnResponseEntity(ErrorCode errorCode){
        return ResponseEntity.status(errorCode.getHttpStatus()).body(new ErrorResponse(errorCode.name(), errorCode.getMessage()));
    }
}

record ErrorResponse(String code, String msg) {}