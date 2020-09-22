package com.siit.bankingapp.mapper;

import com.siit.bankingapp.domain.entity.TransactionEntity;
import com.siit.bankingapp.domain.model.Transaction;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TransactionEntityToTransactionMapper implements Converter<TransactionEntity, Transaction> {

    @Override
    public Transaction convert(TransactionEntity source) {

        return Transaction.builder()
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
