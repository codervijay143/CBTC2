package com.banky.controllers;

import com.banky.dtos.AccountDTO;
import com.banky.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public AccountDTO createAccount(@RequestParam String owner){
        return accountService.createAccount(owner);
    }

    @GetMapping("/{id}")
    public Optional<AccountDTO> getAccount(@PathVariable Long id){
        return accountService.getAccount(id);
    }

    @PostMapping("/{id}/deposit")
    public AccountDTO deposit(@PathVariable Long id, @RequestParam BigDecimal amount){
        return accountService.deposit(id,amount);
    }

    @PostMapping("/{id}/withdraw")
    public AccountDTO withdraw(@PathVariable Long id, @RequestParam BigDecimal amount){
        return accountService.withdraw(id,amount);
    }

    @PostMapping("/transfer")
    public void transfer(@RequestParam Long fromAccountId, @RequestParam Long toAccountId,@RequestParam BigDecimal amount){
        this.accountService.transfer(fromAccountId,toAccountId,amount);
    }
}
