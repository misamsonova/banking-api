package com.example.bankingapi.repository;

import com.example.bankingapi.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByOwnerName(String ownerName);
}
