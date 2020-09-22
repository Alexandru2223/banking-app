package com.siit.bankingapp.mapper;

import com.siit.bankingapp.domain.entity.EmployeeEntity;
import com.siit.bankingapp.domain.model.Employee;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class EmployeeEntityToEmployeeMapper implements Converter<EmployeeEntity, Employee> {

    @Override
    public Employee convert(EmployeeEntity source) {
        return Employee.builder()
                       .id(source.getId())
                       .lastName(source.getLastName())
                       .firstName(source.getFirstName())
                       .phoneNumber(source.getPhoneNumber())
                       .email(source.getEmail())
                       .userEmail(source.getUserEmail())
                       .userPassword(source.getUserPassword())
                       .build();
    }
}
