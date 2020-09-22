package com.siit.bankingapp.repository;

import com.siit.bankingapp.domain.entity.CustomerEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    @Query("SELECT c FROM CustomerEntity c WHERE c.cnp = :cnp")
    CustomerEntity findByCnp(@Param("cnp") String cnp);

    @Query("select c from CustomerEntity c")
    List<CustomerEntity> getAll();


}
