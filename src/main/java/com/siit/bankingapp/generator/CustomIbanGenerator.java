package com.siit.bankingapp.generator;

import org.hibernate.Session;
import org.hibernate.tuple.ValueGenerator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

public class CustomIbanGenerator implements ValueGenerator<String> {

    @Override
    public String generateValue(Session session, Object owner) {

        String prefix = "IBAN";
        String generatedValue = "";
        AtomicReference<String> atomicStringReference =
                new AtomicReference<>();

        session.doWork(connection -> {
            try (PreparedStatement stmt = connection.prepareStatement("SELECT count(employee_id) FROM employee")) {
                ResultSet rs = stmt.executeQuery();
                rs.next();
                final String alphabet = "123456789";
                final int N = alphabet.length();
                Random rd = new Random();
                int iLength = 5;
                StringBuilder sb = new StringBuilder(iLength);
                for (int i = 0; i < iLength; i++) {
                    sb.append(alphabet.charAt(rd.nextInt(N)));
                }
                atomicStringReference.set(generatedValue.concat(prefix + sb + rs.getInt(1) + 10 + "ICBR"));
            }
        });
        return atomicStringReference.get();
    }
}
