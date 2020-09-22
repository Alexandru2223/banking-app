package com.siit.bankingapp.domain.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "auth_user")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Valid
    @OneToOne(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private CustomerEntity customerEntity;

    @Email(message = "Invalid email adress")
    @NotEmpty(message = "Email can't be empty")
    @Column(name = "user_email")
    private String email;

    @OneToOne(mappedBy = "userEntity1", cascade = CascadeType.ALL, orphanRemoval = true)
    private EmployeeEntity employeeEntity;

    @Id
    @GenericGenerator(name = "auth_role_id", strategy = "com.siit.bankingapp.generator.UserIdGenerator")
    @GeneratedValue(generator = "auth_role_id")
    @Column(name = "auth_user_id")
    private long id;

    @NotEmpty(message = "Password can't be empty")
    @Length(min = 6, message = "Invalid password, minim 6 characters")
    @Column(name = "user_password")
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id")
    private RoleEntity role;

    @Column(name = "status")
    private String status;

    public void setCustomerEntity(CustomerEntity customerEntity) {

        this.customerEntity = customerEntity;
        customerEntity.setUserEntity(this);
    }

    public void setEmployeeEntity(EmployeeEntity employeeEntity) {

        this.employeeEntity = employeeEntity;
        employeeEntity.setUserEntity1(this);
    }
}
