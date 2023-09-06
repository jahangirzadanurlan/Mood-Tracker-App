package com.example.userservice.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum Exceptions {
    TOKEN_IS_INVALID_EXCEPTION(HttpStatus.BAD_REQUEST, "Token is invalid!"),
    USER_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND, "User not found!"),
    STOCK_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND, "Stock not found!"),
    WALLET_NOT_ENOUGH_EXCEPTION(HttpStatus.BAD_REQUEST, "You don't have enough money in your wallet"),
    HIGH_PRICE_EXCEPTION(HttpStatus.BAD_REQUEST, "The stock price is higher than your bid!"),
    STOCK_STATUS_IS_FALSE_EXCEPTION(HttpStatus.BAD_REQUEST, "Stock buyStatus is false. You can't sell it!"),
    TOKEN_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND,"Token not found");

    private final HttpStatus httpStatus;
    private final String message;
}
