package com.ilya.db.dao;

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
public class PaymentProcessorDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PaymentProcessorRowMapper paymentProcessorRowMapper;

    public void insertPaymentProcessor(PaymentProcessor paymentProcessor) {
        String query = "INSERT INTO payment_processor (id, key, name, description) VALUES (?,?,?,?)";

        jdbcTemplate.update(query, UUID.fromString(paymentProcessor.getId()), paymentProcessor.getKey(),
                paymentProcessor.getName(), paymentProcessor.getDescription());

//        KeyHolder keyHolder = new GeneratedKeyHolder();
//
//        jdbcTemplate.update(connection -> {
//            PreparedStatement ps = connection.prepareStatement(query, new String[]{"id"});
//            ps.setString(1,paymentProcessor.getKey());
//            ps.setString(2,paymentProcessor.getName());
//            ps.setString(3,paymentProcessor.getDescription());
//            return ps;
//        }, keyHolder);
//
//        return String.valueOf(keyHolder.getKeyList().get(0).get("id"));
    }

    public boolean isPaymentProcessorNonExists(String id) {
        String query = "SELECT * FROM payment_processor where id=?";
        List<PaymentProcessor> paymentProcessors = jdbcTemplate.query(query, new Object[]{UUID.fromString(id)}, paymentProcessorRowMapper);

        return paymentProcessors.isEmpty();
    }

    public boolean isPaymentProcessorWithKeyExists(String key) {
        String query = "SELECT * FROM payment_processor where key=?";
        List<PaymentProcessor> paymentProcessors = jdbcTemplate.query(query, new Object[]{key}, paymentProcessorRowMapper);

        return !paymentProcessors.isEmpty();
    }

    public PaymentProcessor getPaymentProcessor(String id) {
        String query1 = "SELECT * FROM payment_processor WHERE id = ?";

        return jdbcTemplate.queryForObject(query1, paymentProcessorRowMapper, UUID.fromString(id));
    }

    public List<PaymentProcessor> getAllPaymentProcessors(Long pageNumber, Long pageSize) {
        String query = "SELECT * FROM payment_processor LIMIT ? OFFSET ?";

        return jdbcTemplate.query(
                query,
                paymentProcessorRowMapper, pageSize, pageSize * (pageNumber - 1));
    }

    public void deletePaymentProcessor(String id) {
        String query = "DELETE FROM payment_processor WHERE id = ?";
        jdbcTemplate.update(query, UUID.fromString(id));
    }

    public void updatePaymentProcessor(String id, PaymentProcessor paymentProcessor) {
        String query = "UPDATE payment_processor SET key=?, name=?, description=? WHERE id = ? ";

        jdbcTemplate.update(query, paymentProcessor.getKey(),
                paymentProcessor.getName(), paymentProcessor.getDescription(), UUID.fromString(id));
    }
}
