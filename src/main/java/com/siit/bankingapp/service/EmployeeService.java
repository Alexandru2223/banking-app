package com.siit.bankingapp.service;

import com.siit.bankingapp.domain.entity.EmployeeEntity;
import com.siit.bankingapp.domain.entity.RoleEntity;
import com.siit.bankingapp.domain.entity.UserEntity;
import com.siit.bankingapp.domain.model.Employee;
import com.siit.bankingapp.domain.model.User;
import com.siit.bankingapp.mapper.EmployeeEntityToEmployeeMapper;
import com.siit.bankingapp.mapper.EmployeeToEmployeeEntityMapper;
import com.siit.bankingapp.mapper.UserEntityToUserMapper;
import com.siit.bankingapp.mapper.UserEntityToUserWithEmployeeMapper;
import com.siit.bankingapp.mapper.UserToUserEntityWithoutCustomerMapper;
import com.siit.bankingapp.repository.EmployeeRepository;
import com.siit.bankingapp.repository.RoleRepository;
import com.siit.bankingapp.repository.UserRepository;

import javassist.NotFoundException;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmployeeService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final EmployeeEntityToEmployeeMapper employeeEntityToEmployeeMapper;

    private final EmployeeToEmployeeEntityMapper employeeToEmployeeEntityMapper;

    private final EmployeeRepository repository;

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final UserToUserEntityWithoutCustomerMapper userToUserEntityWithoutCustomerMapper;

    private final UserEntityToUserMapper userEntityToUserMapper;

    private final UserEntityToUserWithEmployeeMapper userToUserEntityWithEmployeeMapper;

    public Employee create(Employee employee) {

        EmployeeEntity savedEmployeeEntity = employeeToEmployeeEntityMapper.convert(employee);
        EmployeeEntity savedEmployee = repository.save(savedEmployeeEntity);

        Employee employee1 = employeeEntityToEmployeeMapper.convert(savedEmployee);
        User user = new User();
        user.setPassword(bCryptPasswordEncoder.encode(employee1.getUserPassword()));
        user.setStatus("VERIFIED");
        RoleEntity userRole = roleRepository.findByRole("ADMIN_USER");
        user.setRole(userRole);
        user.setEmail(employee1.getUserEmail());

        UserEntity userEntity = userToUserEntityWithoutCustomerMapper.convert(user);
        userEntity.setEmployeeEntity(savedEmployee);
        userRepository.save(userEntity);

        return employeeEntityToEmployeeMapper.convert(savedEmployee);
    }

    public void delete(long employeeId) throws NotFoundException {
       /* EmployeeEntity employeeEntity = repository.findById(employeeId).orElseThrow(() -> new NotFoundException("Employee with id provided cannot be found"));
        repository.delete(employeeEntity);
    //    String email = userRepository.findByEmail(employeeEntity.getUserEmail()).getEmail();
        UserEntity userRepositoryByEmail = userRepository.findByEmail(employeeEntity.getUserEmail());
        //userRepository.deleteByEmail(email);
        userRepository.delete(userRepositoryByEmail);*/

        //repository.getAll().forEach(employeeEntity -> System.out.println(employeeEntity.getUserEntity1().getId()));
       /* UserEntity userEntity = userRepository.getAll().stream()
                                              .filter(userEntity1 -> userEntity1.getEmployeeEntity().getId() == employeeId)
                                              .findFirst()
                                              .get();*/

        /*UserEntity userEntity = repository.getAll().stream()
                                          .filter(employeeEntity -> employeeEntity.getId() == employeeId)
                                          .map(employeeEntity -> employeeEntity.getUserEntity1())
                                          .findFirst().get();*/

        EmployeeEntity employeeEntity1 = repository.getAll().stream()
                                                   .filter(employeeEntity -> employeeEntity.getId() == employeeId)
                                                   .findFirst()
                                                   .get();
        employeeEntity1.setUserEntity1(null);
        repository.delete(employeeEntity1);


    }

    public Employee findById(long employeeId) throws NotFoundException {

        return repository.findById(employeeId)
                         .map(employeeEntityToEmployeeMapper::convert)
                         .orElseThrow(() -> new NotFoundException("Employee with id provided cannot be found"));
    }

    public Employee findByLastName(String employeeLastName) throws NotFoundException {

        Optional<EmployeeEntity> employeeEntityOptional = repository.getAll().stream()
                                                                    .filter(x -> x.getLastName().equals(employeeLastName))
                                                                    .findFirst();
        return repository.findById(employeeEntityOptional.get().getId())
                         .map(employeeEntityToEmployeeMapper::convert)
                         .orElseThrow(() -> new NotFoundException("Employee with id provided cannot be found"));
    }

    public List<Employee> getAll() {

        return repository.getAll()
                         .stream()
                         .map(employeeEntityToEmployeeMapper::convert)
                         .collect(Collectors.toList());
    }

    @Transactional
    public void update(Employee employee) throws NotFoundException {

        EmployeeEntity employeeEntity =
                repository.findById(employee.getId()).orElseThrow(() -> new NotFoundException("Employee with id provided cannot be found"));
        updateFields(employeeEntity, employee);

    }

    private void updateFields(EmployeeEntity employeeEntity, Employee employee) {

        employeeEntity.setLastName(employee.getLastName());
        employeeEntity.setFirstName(employee.getFirstName());
        employeeEntity.setPhoneNumber(employee.getPhoneNumber());
    }
}
