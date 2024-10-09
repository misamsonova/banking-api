package com.example.bankingapi.controller;

import com.example.bankingapi.dto.CreateAccountRequest;
import com.example.bankingapi.model.Account;
import com.example.bankingapi.model.Transaction;
import com.example.bankingapi.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@Tag(name = "Account Controller", description = "Operations related to bank accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @Operation(summary = "Create a new account", description = "Creates a new bank account with the provided owner name and PIN")
    @PostMapping
    public ResponseEntity<Account> createAccount(@Valid @RequestBody CreateAccountRequest request) {
        Account account = accountService.createAccount(request.getOwnerName(), request.getPin());
        return ResponseEntity.ok(account);
    }

    @Operation(summary = "Deposit money", description = "Deposits a specified amount into the account")
    @PostMapping("/{id}/deposit")
    public ResponseEntity<Void> deposit(@PathVariable Long id, @RequestParam BigDecimal amount) {
        accountService.deposit(id, amount);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Withdraw money", description = "Withdraws a specified amount from the account using PIN authentication")
    @PostMapping("/{id}/withdraw")
    public ResponseEntity<Void> withdraw(@PathVariable Long id, @RequestParam BigDecimal amount, @RequestParam String pin) {
        accountService.withdraw(id, amount, pin);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get transaction history", description = "Retrieves all transactions for a specific account")
    @GetMapping("/{id}/transactions")
    public ResponseEntity<List<Transaction>> getTransactions(@PathVariable Long id) {
        List<Transaction> transactions = accountService.getTransactions(id);
        return ResponseEntity.ok(transactions);
    }

    @Operation(summary = "Get all accounts", description = "Retrieves all bank accounts with owner names and current balances")
    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts() {
        List<Account> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }
}
