package com.banky.services;

import com.banky.dtos.AccountDTO;

import java.math.BigDecimal;
import java.util.Optional;

public interface AccountService {
    AccountDTO createAccount(String owner);

    Optional<AccountDTO> getAccount(Long id);

    AccountDTO deposit(Long id, BigDecimal amount);

    AccountDTO withdraw(Long id, BigDecimal amount);

    void transfer(Long fromAccountId, Long toAccountId, BigDecimal amount);
}
