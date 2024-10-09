package com.example.bankingapi.exception;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(Long accountId) {
        super("Счет с ID " + accountId + " не найден.");
    }
}
