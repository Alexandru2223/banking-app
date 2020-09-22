package com.siit.bankingapp.domain.entity;

import org.hibernate.validator.constraints.Length;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customer", schema = "banking_database")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerEntity {

    @OneToOne(mappedBy = "customerEntity", cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private AccountEntity accountEntity;

    @NotEmpty(message = "Adrees can't be empty")
    @Column(name = "adress")
    private String adress;

    @NotEmpty(message = "CNP can't be empty")
    @Length(min = 13,max = 13, message = "Invalid CNP" )
    @Column(name = "cnp")
    private String cnp;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private long customerId;


    @Column(name = "email")
    private String email;

    @NotEmpty(message = "First name can't be empty")
    @Column(name = "first_name")
    private String firstName;

    @NotEmpty(message = "Last name can't be empty")
    @Column(name = "last_name")
    private String lastName;

    @NotEmpty(message = "Phone can't be empty")
    @Length(min = 10,max = 10, message = "Invalid phone number")
    @Column(name = "phone")
    private String phone;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "auth_user_id")
    private UserEntity userEntity;

    public void setAccountEntity(AccountEntity accountEntity) {

        this.accountEntity = accountEntity;
        accountEntity.setCustomerEntity(this);
    }
}
