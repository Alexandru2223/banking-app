package com.siit.bankingapp.generator;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EmployeeIdGenerator implements IdentifierGenerator {


    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {

        Connection connection = session.connection();

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select count(employee_id) as Id from employee");
            if (rs.next()) {
                return (long) rs.getInt(1) + 101;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


}
