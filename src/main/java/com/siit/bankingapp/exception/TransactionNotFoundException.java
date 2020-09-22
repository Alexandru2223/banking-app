package com.siit.bankingapp.exception;

import org.springframework.core.NestedRuntimeException;

public class TransactionNotFoundException extends NestedRuntimeException {

    public TransactionNotFoundException(String message){
        super(message);
    }

}
