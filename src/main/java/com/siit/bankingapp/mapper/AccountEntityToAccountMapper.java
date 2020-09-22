package com.siit.bankingapp.mapper;

import com.siit.bankingapp.domain.entity.AccountEntity;
import com.siit.bankingapp.domain.model.Account;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AccountEntityToAccountMapper implements Converter<AccountEntity, Account> {


    @Override
    public Account convert(AccountEntity source) {

        return Account.builder()
                      // .customerId(source.getCustomerId())
                      .accountId(source.getAccountId())
                      .balance(source.getBalance())
                      .createdDate(source.getCreatedDate())
                      // .customer(mapCustomer(source.getCustomerEntity()))
                      .iban(source.getIban())
                      .build();
    }
}
