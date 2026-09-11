package com.toyproject.shoppingManage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    // ----------------------- 400 : BAD REQUEST --------------------------//

    NOT_ENOUGH_STOCK(HttpStatus.BAD_REQUEST, "재고 수량이 부족합니다."),

    // ----------------------- 404 : NOT FOUND --------------------------//

    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다."),
    ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 상품입니다."),
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 주문입니다."),

    // ----------------------- 409 : CONFLICT --------------------------//

    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "중복된 이메일입니다."),

    // -------------------------- NOTHING ------------------------------//

    DEFAULT(null, null);

    // ----------------------- FIELD --------------------------//
    private final HttpStatus httpStatus;
    private final String message;
}
