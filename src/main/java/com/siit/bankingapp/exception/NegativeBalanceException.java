package com.siit.bankingapp.exception;

import org.springframework.core.NestedRuntimeException;

public class NegativeBalanceException extends NestedRuntimeException {

    public NegativeBalanceException(String message) {

        super(message);
    }

}
