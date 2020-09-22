package com.siit.bankingapp.repository;

import com.siit.bankingapp.domain.entity.EmployeeEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {

    @Query("select e from EmployeeEntity e left join fetch e.userEntity1")
    List<EmployeeEntity> getAll();
}
