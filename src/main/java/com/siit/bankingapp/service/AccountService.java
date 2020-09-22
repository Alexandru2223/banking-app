package com.siit.bankingapp.service;

import com.siit.bankingapp.domain.entity.AccountEntity;
import com.siit.bankingapp.domain.model.Account;
import com.siit.bankingapp.mapper.AccountEntityToAccountMapper;
import com.siit.bankingapp.mapper.AccountToAccountEntityMapper;
import com.siit.bankingapp.repository.AccountRepository;

import javassist.NotFoundException;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import javax.transaction.Transactional;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AccountService {

    private final AccountEntityToAccountMapper accountEntityToAccountMapper;

    private final AccountRepository accountRepository;

    private final AccountToAccountEntityMapper accountToAccountEntityMapper;

    public boolean checkIfIbanExists(String iban) {

        AccountEntity accountEntity = accountRepository.findByIban(iban);
        if (accountEntity == null) {
            return false;
        } else {
                return true;
        }
    }

   /* public Account findByCustomerId(long id) throws NotFoundException {
        AccountEntity accountEntity = accountRepository.findByCustomerId(id);
        return accountEntityToAccountMapper.convert(accountEntity);
    }*/

    public Account create(Account account) {
        //   Account account = new Account().builder().balance(new BigDecimal(0)).build();
        AccountEntity accountEntity = accountToAccountEntityMapper.convert(account);
        AccountEntity savedEntity = accountRepository.save(accountEntity);
        return accountEntityToAccountMapper.convert(savedEntity);

    }

    public Account findById(long id) throws NotFoundException {

        AccountEntity accountEntity = accountRepository.findById(id).get();
        return accountEntityToAccountMapper.convert(accountEntity);
    }

    @Transactional
    public void updateBalance(Account account) throws NotFoundException {

        AccountEntity accountEntity = accountRepository.findById(account.getAccountId()).get();
        updateFieldBalance(accountEntity, account);

    }

    private void updateFieldBalance(AccountEntity accountEntity, Account account) {

        long val = accountEntity.getBalance().longValue() + account.getBalance().longValue();
        accountEntity.setBalance(new BigDecimal(val));
    }


    public boolean checkEnoughBalance(String senderIban, BigDecimal amount){

        AccountEntity accountEntity = accountRepository.findByIban(senderIban);
        Account account = accountEntityToAccountMapper.convert(accountEntity);
        BigDecimal subtractedAmount = account.getBalance().subtract(amount);
        if (subtractedAmount.compareTo(BigDecimal.ZERO) == -1){
            return false;
        } else {
            return true;
        }
    }
}
