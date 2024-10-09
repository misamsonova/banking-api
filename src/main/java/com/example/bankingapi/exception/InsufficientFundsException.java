package com.example.bankingapi.exception;

import java.math.BigDecimal;

public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(Long accountId, BigDecimal currentBalance, BigDecimal withdrawalAmount) {
        super("Недостаточно средств на счете с ID " + accountId + ". Текущий баланс: " + currentBalance + ", требуется: " + withdrawalAmount);
    }
}
