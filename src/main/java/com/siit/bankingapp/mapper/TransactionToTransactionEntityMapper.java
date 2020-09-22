package com.siit.bankingapp.mapper;

import com.siit.bankingapp.domain.entity.TransactionEntity;
import com.siit.bankingapp.domain.model.Transaction;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TransactionToTransactionEntityMapper implements Converter<Transaction, TransactionEntity> {

    @Override
    public TransactionEntity convert(Transaction source) {

        return TransactionEntity.builder()
                                .id(source.getId())
                                .senderIban(source.getSenderIban())
                                .consigneeIban(source.getConsigneeIban())
                                .amount(source.getAmount())
                                .transactionDate(source.getTransactionDate())
                                .description(source.getDescription())
                                .status(source.getStatus())
                                .build();
    }
}
