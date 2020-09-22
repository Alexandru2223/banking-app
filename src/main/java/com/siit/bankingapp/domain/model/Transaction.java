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
public class Transaction {

    private BigDecimal amount;

    private String consigneeIban;

    private String description;

    private long id;

    private String senderIban;

    private String status;

    private LocalDate transactionDate;

}
