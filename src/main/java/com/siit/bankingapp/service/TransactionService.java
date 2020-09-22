package com.siit.bankingapp.service;

import com.siit.bankingapp.domain.entity.TransactionEntity;
import com.siit.bankingapp.domain.model.Transaction;
import com.siit.bankingapp.mapper.TransactionEntityToTransactionMapper;
import com.siit.bankingapp.mapper.TransactionToTransactionEntityMapper;
import com.siit.bankingapp.repository.TransactionRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TransactionService {


    private final TransactionRepository repository;

    private final TransactionEntityToTransactionMapper transactionEntityToTransactionMapper;

    private final TransactionToTransactionEntityMapper transactionToTransactionEntityMapper;

    private final UserService userService;

    public Transaction create(Transaction transaction) {

        TransactionEntity transactionEntity = transactionToTransactionEntityMapper.convert(transaction);
        TransactionEntity savedEntity = repository.save(transactionEntity);
        return transactionEntityToTransactionMapper.convert(savedEntity);
    }

    public List<Transaction> getAllTransactions() {

        return repository.getAll()
                         .stream()
                         .map(transactionEntityToTransactionMapper::convert)
                         .collect(Collectors.toList());
    }

    public List<Transaction> getUserTransactions(String email) {

        String iban = userService.getOneUser(email).getCustomer().getAccount().getIban();
        return repository.findByIban(iban).stream()
                         .map(transactionEntityToTransactionMapper::convert)
                         .collect(Collectors.toList());

    }

}
