package com.siit.bankingapp.repository;

import com.siit.bankingapp.domain.entity.AccountEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

    @Query("SELECT a FROM AccountEntity a")
    AccountEntity findByCustomerId(@Param("customerId") long customerId);

    @Query("SELECT a FROM AccountEntity a where a.iban = :iban")
    AccountEntity findByIban(@Param("iban") String iban);

    @Query("select a from AccountEntity a")
    List<AccountEntity> getAllAccounts();
}
