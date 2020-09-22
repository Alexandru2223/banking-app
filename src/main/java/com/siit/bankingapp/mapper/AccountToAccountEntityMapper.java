package com.siit.bankingapp.mapper;

import com.siit.bankingapp.domain.entity.AccountEntity;
import com.siit.bankingapp.domain.entity.CustomerEntity;
import com.siit.bankingapp.domain.model.Account;
import com.siit.bankingapp.domain.model.Customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AccountToAccountEntityMapper implements Converter<Account, AccountEntity> {

    @Autowired
    private CustomerToCustomerEntityMapper customerToCustomerEntityMapper;

    @Override
    public AccountEntity convert(Account source) {

        return AccountEntity.builder()
                          // .customerId(source.getCustomerId())
                            .balance(source.getBalance())
                            .createdDate(source.getCreatedDate())
                            //.customerEntity(mapCustomer(source.getCustomer()))
                            .iban(source.getIban())
                .build();
    }

    public CustomerEntity mapCustomer(Customer customer){


        return customerToCustomerEntityMapper.convert(customer);
    }
}
