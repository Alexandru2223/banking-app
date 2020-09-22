package com.siit.bankingapp.repository;

import com.siit.bankingapp.domain.entity.TransactionEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {

    @Query("SELECT t from TransactionEntity t where t.senderIban = :iban")
    List<TransactionEntity> findByIban(@Param("iban") String iban);

    @Query("SELECT t from TransactionEntity t")
    List<TransactionEntity> getAll();
}
