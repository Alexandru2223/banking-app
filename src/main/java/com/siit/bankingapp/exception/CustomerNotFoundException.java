package com.siit.bankingapp.exception;

import org.springframework.core.NestedRuntimeException;

public class CustomerNotFoundException extends NestedRuntimeException {

    public CustomerNotFoundException(String message){
        super(message);
    }

}
