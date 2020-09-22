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
public class UserToUserEntityWithoutCustomerMapper implements Converter<User, UserEntity> {

    private final EmployeeToEmployeeEntityMapper employeeToEmployeeEntityMapper;
    @Override
    public UserEntity convert(User source) {

        return UserEntity.builder()
                         .id(source.getId())
                         .email(source.getEmail())
                         .password(source.getPassword())
                         .role(source.getRole())
                         .status(source.getStatus())
                       //  .employeeEntity(mapEmployee(source.getEmployee()))
                      //   .customerEntity(mapCustomer(source.getCustomer()))
                         .build();
    }

    public EmployeeEntity mapEmployee(Employee employee){
        return employeeToEmployeeEntityMapper.convert(employee);
    }


}

