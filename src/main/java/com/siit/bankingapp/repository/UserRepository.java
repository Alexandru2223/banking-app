package com.siit.bankingapp.repository;

import com.siit.bankingapp.domain.entity.UserEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import javax.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM UserEntity e WHERE e.id = :id")
    void deleteById(@Param("id") long id);

    @Query("SELECT e FROM UserEntity e left join fetch e.customerEntity c left join fetch c.accountEntity WHERE e.email = :email")
    UserEntity findByEmail(@Param("email") String email);

    @Query("SELECT e FROM UserEntity e left join fetch e.customerEntity c left join fetch c.accountEntity d WHERE d.iban = :iban")
    UserEntity findByIban(@Param("iban") String iban);

    @Query("select u from UserEntity u")
    List<UserEntity> getAll();


}
