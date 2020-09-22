package com.siit.bankingapp.generator;

import org.hibernate.Session;
import org.hibernate.tuple.ValueGenerator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.atomic.AtomicReference;

public class CustomEmailGenerator implements ValueGenerator<String> {

    //ToDO JDBC template
    @Override
    public String generateValue(Session session, Object owner) {

        String prefix = "admin";
        String generatedValue = "";
        AtomicReference<String> atomicStringReference =
                new AtomicReference<>();

        session.doWork(connection -> {
            try (PreparedStatement stmt = connection.prepareStatement("SELECT count(employee_id) FROM employee")) {
                ResultSet rs = stmt.executeQuery();
                rs.next();
                atomicStringReference.set(generatedValue.concat(prefix + rs.getInt(1) + 12 + "@yahoo.com"));
            }
        });
        return atomicStringReference.get();
    }
}
