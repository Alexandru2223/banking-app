package com.siit.bankingapp.generator;

import org.hibernate.Session;
import org.hibernate.tuple.ValueGenerator;

import java.time.LocalDate;

public class CreatedDateGenerator implements ValueGenerator<LocalDate> {

    @Override
    public LocalDate generateValue(Session session, Object owner) {

        LocalDate date = LocalDate.now();
        return date;
    }
}
