package com.example.bankingapi.config;

import com.example.bankingapi.model.Account;
import com.example.bankingapi.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public void run(String... args) throws Exception {
        if (accountRepository.count() == 0) {
            Account account1 = new Account();
            account1.setOwnerName("Иван Иванов");
            account1.setPin("1234");
            account1.setBalance(new BigDecimal("1000.00"));
            accountRepository.save(account1);

            Account account2 = new Account();
            account2.setOwnerName("Мария Петрова");
            account2.setPin("5678");
            account2.setBalance(new BigDecimal("2000.00"));
            accountRepository.save(account2);

            System.out.println("Исходные данные успешно загружены.");
        } else {
            System.out.println("Исходные данные уже существуют. Загрузка пропущена.");
        }
    }
}
