package com.siit.bankingapp.domain.entity;

import com.siit.bankingapp.generator.CreatedDateGenerator;
import com.siit.bankingapp.generator.CustomIbanGenerator;

import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GeneratorType;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "account")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private long accountId;

    @Column(name = "balance")
    private BigDecimal balance;

    @GeneratorType(type = CreatedDateGenerator.class, when = GenerationTime.INSERT)
    @Column(name = "created_date")
    private LocalDate createdDate;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "customer_id")
    private CustomerEntity customerEntity;

    @GeneratorType(type = CustomIbanGenerator.class, when = GenerationTime.INSERT)
    @Column(name = "iban")
    private String iban;


}