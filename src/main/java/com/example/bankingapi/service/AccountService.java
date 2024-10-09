package com.example.bankingapi.service;

import com.example.bankingapi.exception.*;
import com.example.bankingapi.model.Account;
import com.example.bankingapi.model.Transaction;
import com.example.bankingapi.repository.AccountRepository;
import com.example.bankingapi.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AccountService {
    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    // Регулярное выражение для проверки PIN-кода (4 цифры)
    private static final String PIN_REGEX = "\\d{4}";

    public Account createAccount(String ownerName, String pin) {
        logger.info("Creating account for owner: {}", ownerName);
        if (!pin.matches(PIN_REGEX)) {
            logger.warn("Invalid PIN format for owner {}: {}", ownerName, pin);
            throw new InvalidPinFormatException(pin);
        }

        Account account = new Account();
        account.setOwnerName(ownerName);
        account.setPin(pin);
        Account savedAccount = accountRepository.save(account);
        logger.info("Account created successfully with ID: {}", savedAccount.getId());
        return savedAccount;
    }

    @Transactional
    public void deposit(Long accountId, BigDecimal amount) {
        logger.info("Depositing {} to account ID: {}", amount, accountId);
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            logger.warn("Invalid deposit amount: {}", amount);
            throw new InvalidAmountException(amount);
        }

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> {
                    logger.error("Account not found for deposit. ID: {}", accountId);
                    return new AccountNotFoundException(accountId);
                });

        account.setBalance(account.getBalance().add(amount));
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(amount);
        transaction.setTransactionType("DEPOSIT");
        transaction.setTimestamp(LocalDateTime.now());
        transactionRepository.save(transaction);

        accountRepository.save(account);
        logger.info("Deposit successful. New balance for account ID {}: {}", accountId, account.getBalance());
    }

    @Transactional
    public void withdraw(Long accountId, BigDecimal amount, String pin) {
        logger.info("Withdrawing {} from account ID: {}", amount, accountId);
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            logger.warn("Invalid withdrawal amount: {}", amount);
            throw new InvalidAmountException(amount);
        }

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> {
                    logger.error("Account not found for withdrawal. ID: {}", accountId);
                    return new AccountNotFoundException(accountId);
                });

        if (!account.getPin().equals(pin)) {
            logger.warn("Invalid PIN attempt for account ID: {}", accountId);
            throw new InvalidPinException();
        }

        if (account.getBalance().compareTo(amount) < 0) {
            logger.warn("Insufficient funds for withdrawal. Account ID: {}, Current balance: {}, Withdrawal amount: {}",
                    accountId, account.getBalance(), amount);
            throw new InsufficientFundsException(accountId, account.getBalance(), amount);
        }

        account.setBalance(account.getBalance().subtract(amount));
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(amount);
        transaction.setTransactionType("WITHDRAW");
        transaction.setTimestamp(LocalDateTime.now());
        transactionRepository.save(transaction);

        accountRepository.save(account);
        logger.info("Withdrawal successful. New balance for account ID {}: {}", accountId, account.getBalance());
    }

    public List<Transaction> getTransactions(Long accountId) {
        logger.info("Fetching transactions for account ID: {}", accountId);
        // Проверяем, существует ли счет
        accountRepository.findById(accountId)
                .orElseThrow(() -> {
                    logger.error("Account not found for fetching transactions. ID: {}", accountId);
                    return new AccountNotFoundException(accountId);
                });
        List<Transaction> transactions = transactionRepository.findByAccount_Id(accountId);
        logger.info("Fetched {} transactions for account ID: {}", transactions.size(), accountId);
        return transactions;
    }

    public List<Account> getAllAccounts() {
        logger.info("Fetching all accounts");
        List<Account> accounts = accountRepository.findAll();
        logger.info("Fetched {} accounts", accounts.size());
        return accounts;
    }
}
