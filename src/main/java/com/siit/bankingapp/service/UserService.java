package com.siit.bankingapp.service;

import com.siit.bankingapp.domain.entity.AccountEntity;
import com.siit.bankingapp.domain.entity.CustomerEntity;
import com.siit.bankingapp.domain.entity.EmployeeEntity;
import com.siit.bankingapp.domain.entity.RoleEntity;
import com.siit.bankingapp.domain.entity.TransactionEntity;
import com.siit.bankingapp.domain.entity.UserEntity;
import com.siit.bankingapp.domain.model.Account;
import com.siit.bankingapp.domain.model.Employee;
import com.siit.bankingapp.domain.model.User;
import com.siit.bankingapp.mapper.AccountToAccountEntityMapper;
import com.siit.bankingapp.mapper.EmployeeEntityToEmployeeMapper;
import com.siit.bankingapp.mapper.UserEntityToUserMapper;
import com.siit.bankingapp.mapper.UserEntityToUserWithCustomerMapper;
import com.siit.bankingapp.mapper.UserEntityToUserWithoutCustomerMapper;
import com.siit.bankingapp.mapper.UserToUserEntityWithoutCustomerMapper;
import com.siit.bankingapp.repository.AccountRepository;
import com.siit.bankingapp.repository.CustomerRepository;
import com.siit.bankingapp.repository.RoleRepository;
import com.siit.bankingapp.repository.TransactionRepository;
import com.siit.bankingapp.repository.UserRepository;

import javassist.NotFoundException;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

    private final AccountRepository accountRepository;

    private final AccountToAccountEntityMapper accountToAccountEntityMapper;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final CustomerRepository customerRepository;

    private final EmployeeEntityToEmployeeMapper employeeEntityToEmployeeMapper;

    private final RoleRepository roleRepository;

    private final TransactionRepository transactionRepository;

    private final UserEntityToUserMapper userEntityToUserMapper;

    private final UserEntityToUserWithCustomerMapper userEntityToUserWithCustomerMapper;

    private final UserEntityToUserWithoutCustomerMapper userEntityToUserWithoutCustomerMapper;

    private final UserRepository userRepository;

    private final UserToUserEntityWithoutCustomerMapper userToUserEntityWithoutCustomerMapper;

    public void create(UserEntity userEntity) {

        Account account = Account.builder().balance(BigDecimal.ZERO).build();
        AccountEntity accountEntity = accountToAccountEntityMapper.convert(account);
        AccountEntity savedAccountEntity = accountRepository.save(accountEntity);


        CustomerEntity customerEntity = userEntity.getCustomerEntity();
        customerEntity.setEmail(userEntity.getEmail());
        customerEntity.setAccountEntity(savedAccountEntity);
        CustomerEntity savedCustomerEntity = customerRepository.save(customerEntity);


        userEntity.setPassword(bCryptPasswordEncoder.encode(userEntity.getPassword()));
        userEntity.setStatus("VERIFIED");
        RoleEntity userRole = roleRepository.findByRole("SITE_USER");
        userEntity.setRole(userRole);
        userEntity.setCustomerEntity(savedCustomerEntity);

        userRepository.save(userEntity);
    }

    public UserEntity createAdmin(EmployeeEntity employeeEntity) {

        User user = new User();
        RoleEntity userRole = roleRepository.findByRole("ADMIN_USER");
        user.setRole(userRole);
        Employee employee = employeeEntityToEmployeeMapper.convert(employeeEntity);
        user.setEmail(employee.getUserEmail());
        user.setPassword(employee.getUserPassword());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setStatus("VERIFIED");
        UserEntity userEntity = userToUserEntityWithoutCustomerMapper.convert(user);
        userEntity.setEmployeeEntity(employeeEntity);
        return userRepository.save(userEntity);
    }

    public void delete(long id) {

        /*User user1 = userRepository.getAll().stream()
                                   .map(userEntityToUserWithoutCustomerMapper::convert)
                                   .filter(user -> user.getId() == id)
                                   .findFirst()
                                   .get();
        userRepository.delete(userToUserEntityWithoutCustomerMapper.convert(user1));*/

        userRepository.deleteById(id);
    }

    public List<User> getAllUsers() {

        return userRepository.getAll()
                             .stream()
                             .map(userEntityToUserWithoutCustomerMapper::convert)
                             .collect(Collectors.toList());
    }

    public User getOneUser(String userEmail) {

        User convert = userEntityToUserWithCustomerMapper.convert(userRepository.findByEmail(userEmail));
        return convert;

    }

    public boolean isUserAlreadyPresent(UserEntity userEntity) {

        User user = userEntityToUserMapper.convert(userEntity);
        boolean isUserAlreadyExists = false;
        UserEntity existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser != null) {
            isUserAlreadyExists = true;
        }
        return isUserAlreadyExists;
    }

    @Transactional
    public void updateAccounts(String senderIban, String consigneeIban, BigDecimal amount, Long transactionId) {

        TransactionEntity transactionEntity = transactionRepository.findById(transactionId).get();
        UserEntity userEntitySender = userRepository.findByIban(senderIban);
        UserEntity userEntityConsignee = userRepository.findByIban(consigneeIban);
        BigDecimal senderBalance = userEntitySender.getCustomerEntity().getAccountEntity().getBalance();
        BigDecimal subtractedSenderBalance = senderBalance.subtract(amount);
        BigDecimal consigneeBalance = userEntityConsignee.getCustomerEntity().getAccountEntity().getBalance();
        BigDecimal addedConsigneeBalance = consigneeBalance.add(amount);

        userEntitySender.getCustomerEntity().getAccountEntity().setBalance(subtractedSenderBalance);
        userEntityConsignee.getCustomerEntity().getAccountEntity().setBalance(addedConsigneeBalance);

    }


    @Transactional
    public void updatePassword(User user) throws NotFoundException {

        System.out.println(user);
        UserEntity userEntity = userRepository.findByEmail(user.getEmail());
        updateFields(userEntity,user);

    }

    private void updateFields(UserEntity userEntity, User user) {

        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        userEntity.setPassword(encodedPassword);

    }


}
