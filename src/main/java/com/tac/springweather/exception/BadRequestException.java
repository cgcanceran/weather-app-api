package com.tac.springweather.exception;

public class BadRequestException extends RuntimeException {
    public BadRequestException() {
        super("Bad request.");
    }
    public BadRequestException(String msg) {
        super(msg);
    }
}
