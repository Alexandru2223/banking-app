package com.siit.bankingapp.mapper;

import com.siit.bankingapp.domain.entity.CustomerEntity;
import com.siit.bankingapp.domain.entity.UserEntity;
import com.siit.bankingapp.domain.model.Customer;
import com.siit.bankingapp.domain.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class UserEntityToUserWithCustomerMapper implements Converter<UserEntity, User> {
    @Autowired
    private CustomerEntityToCustomerMapper customerEntityToCustomerMapper;
    @Override
    public User convert(UserEntity source) {
        return User.builder()
                   .id(source.getId())
                   .email(source.getEmail())
                   .password(source.getPassword())
                   .role(source.getRole())
                   .status(source.getStatus())
                   .customer(mapCustomer(source.getCustomerEntity()))
                   .build();
    }
    public Customer mapCustomer(CustomerEntity customerEntity){
        return customerEntityToCustomerMapper.convert(customerEntity);
    }

}
