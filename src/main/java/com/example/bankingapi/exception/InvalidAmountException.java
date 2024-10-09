package com.example.bankingapi.exception;

import java.math.BigDecimal;

public class InvalidAmountException extends RuntimeException {
    public InvalidAmountException(BigDecimal amount) {
        super("Неверная сумма операции: " + amount + ". Сумма должна быть положительной.");
    }
}
