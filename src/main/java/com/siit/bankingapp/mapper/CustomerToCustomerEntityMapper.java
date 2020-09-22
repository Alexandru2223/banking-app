package com.siit.bankingapp.mapper;

import com.siit.bankingapp.domain.entity.AccountEntity;
import com.siit.bankingapp.domain.entity.CustomerEntity;
import com.siit.bankingapp.domain.entity.UserEntity;
import com.siit.bankingapp.domain.model.Account;
import com.siit.bankingapp.domain.model.Customer;
import com.siit.bankingapp.domain.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CustomerToCustomerEntityMapper implements Converter<Customer, CustomerEntity> {

    @Autowired
    private AccountToAccountEntityMapper accountToAccountEntityMapper;
    @Autowired
    private UserToUserEntityMapper userToUserEntityMapper;

    @Override
    public CustomerEntity convert(Customer source) {

        return CustomerEntity.builder()
                             .firstName(source.getFirstName())
                             .lastName(source.getLastName())
                             .cnp(source.getCnp())
                             .email(source.getEmail())
                           //  .accountEntity(mapAccount(source.getAccount()))
                            // .userEntity(mapUser(source.getUser()))
                             .adress(source.getAdress())
                             .customerId(source.getCustomerId())
                             .phone(source.getPhone())
                             .build();
    }

    private AccountEntity mapAccount(Account account){

        return accountToAccountEntityMapper.convert(account);
    }
    private UserEntity mapUser(User user){
        return userToUserEntityMapper.convert(user);
    }
}
