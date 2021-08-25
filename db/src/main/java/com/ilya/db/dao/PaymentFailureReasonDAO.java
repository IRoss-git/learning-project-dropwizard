package com.ilya.db.dao;

import com.ilya.db.domain.PaymentFailureReason;
import com.ilya.db.rowmapper.FailureReasonRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class PaymentFailureReasonDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private FailureReasonRowMapper failureReasonRowMapper;

    public void insertPaymentFailureReason(PaymentFailureReason paymentFailureReason) {
        String query = "INSERT INTO payment_failure_reason (id, code, name, description, processor_id) VALUES (?,?,?,?,?)";

        jdbcTemplate.update(query, UUID.fromString(paymentFailureReason.getId()), paymentFailureReason.getCode(),
                paymentFailureReason.getName(), paymentFailureReason.getDescription(),UUID.fromString(paymentFailureReason.getPaymentProcessor().getId()));

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

    public boolean isPaymentFailureReasonNonExists(String id) {
        String query = "SELECT * FROM payment_failure_reason where id=?";
        List<PaymentFailureReason> paymentFailureReasons = jdbcTemplate.query(query, new Object[]{UUID.fromString(id)}, failureReasonRowMapper);

        return paymentFailureReasons.isEmpty();
    }

    public boolean isMappingExists(String genericId, String reasonId) {
        String query = "SELECT EXISTS(SELECT FROM failure_reason_mapping where payment_failure_reason_id = ? AND generic_failure_reason_id = ?)";
        System.out.println(jdbcTemplate.queryForObject(query, Boolean.class, UUID.fromString(reasonId), UUID.fromString(genericId)));
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(query, Boolean.class, UUID.fromString(reasonId), UUID.fromString(genericId)));
    }

    public boolean isPaymentFailureReasonWithCodeExists(String code, String processorId) {
        String query = "SELECT * FROM payment_failure_reason where code=? and processor_id = ?";
        List<PaymentFailureReason> paymentFailureReasons = jdbcTemplate.query(query, new Object[]{code,UUID.fromString(processorId)}, failureReasonRowMapper);

        return !paymentFailureReasons.isEmpty();
    }

    public PaymentFailureReason getPaymentFailureReason(String id) {
        String query1 = "SELECT * FROM payment_failure_reason WHERE id = ?";

        return jdbcTemplate.queryForObject(query1, failureReasonRowMapper, UUID.fromString(id));
    }

    public void deletePaymentFailureReason(String id) {
        String query = "DELETE FROM payment_failure_reason WHERE id = ?";
        jdbcTemplate.update(query, UUID.fromString(id));
    }

    public void updatePaymentFailureReason(String id, PaymentFailureReason paymentFailureReason) {
        String query = "UPDATE payment_failure_reason SET code=?, name=?, description=? WHERE id = ? ";

        jdbcTemplate.update(query, paymentFailureReason.getCode(),
                paymentFailureReason.getName(), paymentFailureReason.getDescription(), UUID.fromString(id));
    }

    public List<PaymentFailureReason> getAllPaymentFailureReasonsByPaymentProcessor(String paymentProcessorId, Long pageNumber, Long pageSize) {
        String query = "SELECT * FROM payment_failure_reason WHERE payment_failure_reason.processor_id = ? LIMIT ? OFFSET ?;";

        return jdbcTemplate.query(
                query,
                failureReasonRowMapper, UUID.fromString(paymentProcessorId), pageSize, pageSize * (pageNumber - 1));
    }

    public List<PaymentFailureReason> getAllPaymentFailureReasonsByGenericReason(String genericReasonId, Long pageNumber, Long pageSize) {
        String query = "SELECT * FROM payment_failure_reason JOIN failure_reason_mapping ON payment_failure_reason.id = failure_reason_mapping.payment_failure_reason_id  WHERE failure_reason_mapping.generic_failure_reason_id = ? LIMIT ? OFFSET ?;";

        return jdbcTemplate.query(
                query,
                failureReasonRowMapper, UUID.fromString(genericReasonId), pageSize, pageSize * (pageNumber - 1));
    }

    public void createReasonMapping(String genericReasonId, String reasonId) {
        String query = "INSERT INTO failure_reason_mapping VALUES (?,?)";
        jdbcTemplate.update(query, UUID.fromString(reasonId), UUID.fromString(genericReasonId));
    }

    public void deleteReasonMappingByGenericIdAndReasonId(String genericReasonId, String reasonId) {
        String query = "DELETE FROM failure_reason_mapping WHERE failure_reason_mapping.payment_failure_reason_id = ? AND failure_reason_mapping.generic_failure_reason_id = ?";
        jdbcTemplate.update(query, UUID.fromString(reasonId), UUID.fromString(genericReasonId));
    }
}
