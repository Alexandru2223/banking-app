package com.siit.bankingapp.service;

import com.siit.bankingapp.domain.entity.CustomerEntity;
import com.siit.bankingapp.domain.model.Customer;
import com.siit.bankingapp.exception.CustomerNotFoundException;
import com.siit.bankingapp.mapper.CustomerEntityToCustomerMapper;
import com.siit.bankingapp.mapper.CustomerEntityToCustomerWithAccountMapper;
import com.siit.bankingapp.mapper.CustomerToCustomerEntityMapper;
import com.siit.bankingapp.repository.CustomerRepository;

import javassist.NotFoundException;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomerService {


    private final CustomerEntityToCustomerMapper customerEntityToCustomerMapper;

    private final CustomerEntityToCustomerWithAccountMapper customerEntityToCustomerWithAccountMapper;

    private final CustomerRepository customerRepository;

    private final CustomerToCustomerEntityMapper customerToCustomerEntityMapper;

    public Customer create(Customer customer) {

        CustomerEntity customerEntity = customerToCustomerEntityMapper.convert(customer);
        CustomerEntity savedEntity = customerRepository.save(customerEntity);
        Customer convert = customerEntityToCustomerMapper.convert(savedEntity);
        return convert;

    }

  /*  public List<Customer> getAll() {
        return customerRepository.getAll()
                         .stream()
                         .map(customerEntityToCustomerMapper::convert)
                         .collect(Collectors.toList());
    }*/

   /* public void addMoney(BigDecimal value, String cnp){
        CustomerEntity byCnp = customerRepository.findByCnp(cnp);
        accountRepository.findById(byCnp.getCustomerId()).get().setBalance(value);
    }*/

    public Customer findByCnp(String cnp) {

        CustomerEntity customerEntity = customerRepository.findByCnp(cnp);
        return customerEntityToCustomerWithAccountMapper.convert(customerEntity);
    }

    public List<Customer> getAll() {

        return customerRepository.getAll()
                                 .stream()
                                 .map(customerEntityToCustomerMapper::convert)
                                 .collect(Collectors.toList());
    }

    public Customer getOneCustomer(String customerEmail) {

        List<CustomerEntity> entities = customerRepository.getAll();
        CustomerEntity customerEntity = entities.stream().findFirst().get();
        entities.forEach(customerEntity1 -> System.out.println(customerEntity1));
        /*System.out.println("---------------------");
        System.out.println(customerRepository.getAll());
        System.out.println(customer.getAccount().getBalance());
        System.out.println("---------------------");*/


        return customerEntityToCustomerMapper.convert(customerEntity);

        //return customerEntityToCustomerMapper.convert(customerRepository.findByEmail(customerEmail));
    }

    public Customer findById(long id){
        return customerEntityToCustomerMapper.convert((CustomerEntity) customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException("Customer with id provided doesn't exist")));
    }

    @Transactional
    public void update(Customer customer) throws NotFoundException {

        CustomerEntity customerEntity = customerRepository.findById(customer.getCustomerId())
                                                          .orElseThrow(() -> new CustomerNotFoundException("Customer with id provided doesn't exist"));
        updateFields(customerEntity,customer);

    }

    private void updateFields(CustomerEntity customerEntity, Customer customer) {

        customerEntity.setLastName(customer.getLastName());
        customer.setFirstName(customer.getFirstName());
        customerEntity.setEmail(customer.getEmail());
        customerEntity.setPhone(customer.getPhone());
        customerEntity.setAdress(customer.getAdress());
    }
}
