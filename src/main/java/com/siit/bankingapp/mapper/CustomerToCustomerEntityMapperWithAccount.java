package com.siit.bankingapp.mapper;

import com.siit.bankingapp.domain.entity.AccountEntity;
import com.siit.bankingapp.domain.entity.CustomerEntity;
import com.siit.bankingapp.domain.entity.UserEntity;
import com.siit.bankingapp.domain.model.Account;
import com.siit.bankingapp.domain.model.Customer;
import com.siit.bankingapp.domain.model.User;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CustomerToCustomerEntityMapperWithAccount implements Converter<Customer, CustomerEntity> {
    private AccountToAccountEntityMapper accountToAccountEntityMapper;
    private UserEntityToUserMapper userEntityToUserMapper;
    @Override
    public CustomerEntity convert(Customer source) {

        return CustomerEntity.builder()
                             .firstName(source.getFirstName())
                             .lastName(source.getLastName())
                             .cnp(source.getCnp())
                             .email(source.getEmail())
                             .adress(source.getAdress())
                             .customerId(source.getCustomerId())
                             .phone(source.getPhone())
                             .accountEntity(mapAccount(source.getAccount()))
                             // .user(mapUser(source.getUserEntity()))
                             //   .accountEntity(source.getAccountEntity())
                             .build();
    }
    private AccountEntity mapAccount(Account account){
        return accountToAccountEntityMapper.convert(account);
    }
    private User mapUser(UserEntity userEntity){
        return userEntityToUserMapper.convert(userEntity);
    }
}
