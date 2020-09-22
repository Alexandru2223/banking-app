package com.siit.bankingapp.mapper;

import com.siit.bankingapp.domain.entity.EmployeeEntity;
import com.siit.bankingapp.domain.model.Employee;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class EmployeeToEmployeeEntityMapper implements Converter<Employee, EmployeeEntity> {
    @Override
    public EmployeeEntity convert(Employee source) {
        return EmployeeEntity.builder()
                             .lastName(source.getLastName())
                             .firstName(source.getFirstName())
                             .phoneNumber(source.getPhoneNumber())
                             .email(source.getEmail())
                             .userEmail(source.getUserEmail())
                             .userPassword(source.getUserPassword())
                             .build();
    }
}
