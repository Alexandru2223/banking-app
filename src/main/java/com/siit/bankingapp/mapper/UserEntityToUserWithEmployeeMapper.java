package com.siit.bankingapp.mapper;

import com.siit.bankingapp.domain.entity.EmployeeEntity;
import com.siit.bankingapp.domain.entity.UserEntity;
import com.siit.bankingapp.domain.model.Employee;
import com.siit.bankingapp.domain.model.User;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class UserEntityToUserWithEmployeeMapper implements Converter<UserEntity, User> {

    private final EmployeeEntityToEmployeeMapper employeeEntityToEmployeeMapper;
    @Override
    public User convert(UserEntity source) {

        return User.builder()
                         .id(source.getId())
                         .email(source.getEmail())
                         .password(source.getPassword())
                         .role(source.getRole())
                         .status(source.getStatus())
                           .employee(mapEmployee(source.getEmployeeEntity()))
                         //   .customerEntity(mapCustomer(source.getCustomer()))
                         .build();
    }

    public Employee mapEmployee(EmployeeEntity employeeEntity){
        return employeeEntityToEmployeeMapper.convert(employeeEntity);
    }


}

