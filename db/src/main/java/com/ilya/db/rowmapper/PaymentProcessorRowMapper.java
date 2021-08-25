package com.ilya.db.rowmapper;

import com.ilya.db.domain.PaymentProcessor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class PaymentProcessorRowMapper implements RowMapper<PaymentProcessor> {

    @Override
    public PaymentProcessor mapRow(ResultSet rs, int i) throws SQLException {
        PaymentProcessor paymentProcessor = new PaymentProcessor();

        paymentProcessor.setId(rs.getString("id"));
        paymentProcessor.setKey(rs.getString("key"));
        paymentProcessor.setName(rs.getString("name"));
        paymentProcessor.setDescription(rs.getString("description"));

        return paymentProcessor;
    }
}