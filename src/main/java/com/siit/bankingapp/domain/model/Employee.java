package com.siit.bankingapp.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    private String email;

    private String firstName;

    private long id;

    private String lastName;

    private String phoneNumber;

    private User user;

    private String userEmail;

    private String userPassword;
}
