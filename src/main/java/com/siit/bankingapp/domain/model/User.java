package com.siit.bankingapp.domain.model;

import com.siit.bankingapp.domain.entity.RoleEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Customer customer;

    private String email;

    private Employee employee;

    private long id;

    private String password;

    private RoleEntity role;

    private String status;
}
