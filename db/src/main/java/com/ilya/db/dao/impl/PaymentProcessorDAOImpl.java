package com.ilya.db.dao.impl;

import com.ilya.db.dao.PaymentProcessorDAO;
import com.ilya.db.domain.PaymentProcessor;
import com.ilya.db.rowmapper.PaymentProcessorRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Repository
public class PaymentProcessorDAOImpl implements PaymentProcessorDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PaymentProcessorRowMapper paymentProcessorRowMapper;

    @Override
    public void insertPaymentProcessor(PaymentProcessor paymentProcessor) {
        String query = "INSERT INTO payment_processor (id, key, name, description) VALUES (?,?,?,?)";

        jdbcTemplate.update(query, UUID.fromString(paymentProcessor.getId()), paymentProcessor.getKey(),
                paymentProcessor.getName(), paymentProcessor.getDescription());
    }

    @Override
    public boolean isPaymentProcessorNonExists(String id) {
        String query = "SELECT * FROM payment_processor where id=?";
        List<PaymentProcessor> paymentProcessors = jdbcTemplate.query(query, new Object[]{UUID.fromString(id)}, paymentProcessorRowMapper);

        return paymentProcessors.isEmpty();
    }
    @Override
    public boolean isPaymentProcessorWithKeyExists(String key) {
        String query = "SELECT * FROM payment_processor where key=?";
        List<PaymentProcessor> paymentProcessors = jdbcTemplate.query(query, new Object[]{key}, paymentProcessorRowMapper);

        return !paymentProcessors.isEmpty();
    }
    @Override
    public PaymentProcessor getPaymentProcessor(String id) {
        String query1 = "SELECT * FROM payment_processor WHERE id = ?";

        return jdbcTemplate.queryForObject(query1, paymentProcessorRowMapper, UUID.fromString(id));
    }
    @Override
    public List<PaymentProcessor> getAllPaymentProcessors(Long pageNumber, Long pageSize) {
        String query = "SELECT * FROM payment_processor LIMIT ? OFFSET ?";

        return jdbcTemplate.query(
                query,
                paymentProcessorRowMapper, pageSize, pageSize * (pageNumber - 1));
    }
    @Override
    public void deletePaymentProcessor(String id) {
        String query = "DELETE FROM payment_processor WHERE id = ?";
        jdbcTemplate.update(query, UUID.fromString(id));
    }
    @Override
    public void updatePaymentProcessor(String id, PaymentProcessor paymentProcessor) {
        String query = "UPDATE payment_processor SET key=?, name=?, description=? WHERE id = ? ";

        jdbcTemplate.update(query, paymentProcessor.getKey(),
                paymentProcessor.getName(), paymentProcessor.getDescription(), UUID.fromString(id));
    }
}
