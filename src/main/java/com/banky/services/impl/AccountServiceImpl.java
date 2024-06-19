package com.banky.services.impl;

import com.banky.dtos.AccountDTO;
import com.banky.entities.AccountEntity;
import com.banky.repositories.AccountRepository;
import com.banky.services.AccountService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public AccountDTO createAccount(String owner) {
        AccountEntity accountEntity=new AccountEntity();
        accountEntity.setOwner(owner);
        accountEntity.setBalance(BigDecimal.ZERO);
        AccountEntity accountEntity1 = accountRepository.save(accountEntity);
        return this.entityToDTO(accountEntity1);
    }

    @Override
    public Optional<AccountDTO> getAccount(Long id) {
        Optional<AccountEntity> accountEntity = this.accountRepository.findById(id);
        return Optional.of(this.entityToDTO(accountEntity.get()));
    }

    @Override
    @Transactional
    public AccountDTO deposit(Long id, BigDecimal amount) {
        AccountEntity accountEntity = this.accountRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Account ID"));
        accountEntity.setBalance(accountEntity.getBalance().add(amount));
        AccountEntity accountEntity1 = this.accountRepository.save(accountEntity);
        return this.entityToDTO(accountEntity1);
    }

    @Override
    @Transactional
    public AccountDTO withdraw(Long id, BigDecimal amount) {
        AccountEntity accountEntity = this.accountRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Account ID"));
        if(accountEntity.getBalance().compareTo(amount)<0){
            throw new IllegalArgumentException("Insufficient funds");
        }
        accountEntity.setBalance(accountEntity.getBalance().subtract(amount));
        AccountEntity accountEntity1 = this.accountRepository.save(accountEntity);
        return this.entityToDTO(accountEntity1);
    }

    @Override
    public void transfer(Long fromAccountId, Long toAccountId, BigDecimal amount) {
        AccountEntity accountEntity = this.accountRepository.findById(fromAccountId).orElseThrow(() -> new IllegalArgumentException("Invalid from account ID"));
        AccountEntity accountEntity1 = this.accountRepository.findById(toAccountId).orElseThrow(() -> new IllegalArgumentException("Invalid to account ID"));

        if (accountEntity.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient funds");
        }

        accountEntity.setBalance(accountEntity.getBalance().subtract(amount));
        accountEntity1.setBalance(accountEntity1.getBalance().add(amount));

        this.accountRepository.save(accountEntity);
        this.accountRepository.save(accountEntity1);
    }

    private AccountDTO entityToDTO(AccountEntity accountEntity){
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(accountEntity.getId());
        accountDTO.setOwner(accountEntity.getOwner());
        accountDTO.setBalance(accountEntity.getBalance());
        return accountDTO;
    }

    private AccountEntity DTOToEntity(AccountDTO accountDTO){
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setId(accountDTO.getId());
        accountEntity.setOwner(accountDTO.getOwner());
        accountEntity.setBalance(accountDTO.getBalance());
        return accountEntity;
    }
}
