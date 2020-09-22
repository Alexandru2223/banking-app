package com.siit.bankingapp.domain.entity;

import com.siit.bankingapp.generator.CustomEmailGenerator;
import com.siit.bankingapp.generator.CustomPasswordGenerator;

import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GeneratorType;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "employee", schema = "banking_database")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeEntity implements Serializable {

    @Column(name = "email")
    private String email;

    private String firstName;

    @Id
    @GenericGenerator(name = "employee_id", strategy = "com.siit.bankingapp.generator.EmployeeIdGenerator")
    @GeneratedValue(generator = "employee_id")
    @Column(name = "employee_id")
    private long id;

    private String lastName;

    @Column(name = "phone")
    private String phoneNumber;

    @GeneratorType(type = CustomEmailGenerator.class, when = GenerationTime.INSERT)
    @Column(name = "user_email")
    private String userEmail;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "auth_user_id")
    private UserEntity userEntity1;

    @GeneratorType(type = CustomPasswordGenerator.class, when = GenerationTime.INSERT)
    @Column(name = "user_password")
    private String userPassword;
}
