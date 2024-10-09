package com.example.bankingapi.exception;

public class InvalidPinException extends RuntimeException {
    public InvalidPinException() {
        super("Неверный PIN-код.");
    }
}
