package com.siit.bankingapp.repository;

import com.siit.bankingapp.domain.entity.RoleEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {


    RoleEntity findByRole(String role);
}
