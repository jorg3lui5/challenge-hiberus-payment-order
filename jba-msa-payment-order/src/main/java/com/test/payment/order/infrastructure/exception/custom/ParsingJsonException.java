package com.test.payment.order.infrastructure.exception.custom;

public class ParsingJsonException extends RuntimeException{
    public ParsingJsonException(String message) {
        super(message);
    }
}
