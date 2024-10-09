package com.example.bankingapi.exception;

public class InvalidPinFormatException extends RuntimeException {
    public InvalidPinFormatException(String pin) {
        super("Неверный формат PIN-кода: " + pin + ". PIN должен состоять из 4 цифр.");
    }
}