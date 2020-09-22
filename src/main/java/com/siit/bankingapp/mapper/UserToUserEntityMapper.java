package com.siit.bankingapp.mapper;

import com.siit.bankingapp.domain.entity.CustomerEntity;
import com.siit.bankingapp.domain.entity.UserEntity;
import com.siit.bankingapp.domain.model.Customer;
import com.siit.bankingapp.domain.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserToUserEntityMapper implements Converter<User, UserEntity> {

    @Autowired
    private CustomerToCustomerEntityMapper customerToCustomerEntityMapper;
    @Override
    public UserEntity convert(User source) {

        return UserEntity.builder()
                         .id(source.getId())
                         .email(source.getEmail())
                         .password(source.getPassword())
                         .role(source.getRole())
                         .status(source.getStatus())
                         .customerEntity(mapCustomer(source.getCustomer()))
                         .build();
    }

    public CustomerEntity mapCustomer(Customer customer){

        return customerToCustomerEntityMapper.convert(customer);
    }
}
