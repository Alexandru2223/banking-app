package com.siit.bankingapp.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    private long accountId;

    private BigDecimal balance;

    private LocalDate createdDate;

    private Customer customer;

    private String iban;
}
