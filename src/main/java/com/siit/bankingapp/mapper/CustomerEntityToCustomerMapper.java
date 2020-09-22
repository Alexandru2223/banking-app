package com.siit.bankingapp.mapper;

import com.siit.bankingapp.domain.entity.AccountEntity;
import com.siit.bankingapp.domain.entity.CustomerEntity;
import com.siit.bankingapp.domain.model.Account;
import com.siit.bankingapp.domain.model.Customer;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CustomerEntityToCustomerMapper implements Converter<CustomerEntity, Customer> {
   private AccountEntityToAccountMapper accountEntityToAccountMapper;
  //  private UserEntityToUserMapper userEntityToUserMapper;
    @Override
    public Customer convert(CustomerEntity source) {
        return Customer.builder()
                       .firstName(source.getFirstName())
                       .lastName(source.getLastName())
                       .cnp(source.getCnp())
                       .email(source.getEmail())
                       .adress(source.getAdress())
                       .customerId(source.getCustomerId())
                       .phone(source.getPhone())
                       .account(mapAccount(source.getAccountEntity()))
//                       .user(mapUser(source.getUserEntity()))
                       //   .accountEntity(source.getAccountEntity())
                       .build();
    }

    private Account mapAccount(AccountEntity accountEntity){
        return accountEntityToAccountMapper.convert(accountEntity);
    }
   /* private User mapUser(UserEntity userEntity){
        return userEntityToUserMapper.convert(userEntity);
    }*/
}
