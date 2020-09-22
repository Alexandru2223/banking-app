package com.siit.bankingapp.domain.entity;

import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "transaction")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionEntity {

    @DecimalMin(value = "0.0", inclusive = false, message = "Amount can't be 0.")
    @Digits(integer = 100, fraction = 2, message = "Maxim 2 digits after separator")
    @Column(name = "amount")
    private BigDecimal amount;

    @NotEmpty(message = "Enter consignee IBAN")
    @Column(name = "consignee_iban")
    private String consigneeIban;

    @NotEmpty(message = "Enter description")
    @Column(name = "description")
    private String description;

    @Id
    @GenericGenerator(name = "transaction_id", strategy = "com.siit.bankingapp.generator.TransactionIdGenerator")
    @GeneratedValue(generator = "transaction_id")
    @Column(name = "transaction_id")
    private long id;

    @Column(name = "sender_iban")
    private String senderIban;

    @Column(name = "status")
    private String status;

    @Column(name = "date")
    private LocalDate transactionDate;

}
