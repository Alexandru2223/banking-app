package com.siit.bankingapp.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    private Account account;

    private String adress;

    private String cnp;

    private long customerId;

    private String email;

    private String firstName;

    private String lastName;

    private String phone;

    private User user;
}
