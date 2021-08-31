package com.ilya.db.dao.impl;

import com.ilya.db.dao.GenericFailureReasonDAO;
import com.ilya.db.domain.GenericPaymentFailureReason;
import com.ilya.db.rowmapper.GenericFailureReasonRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class GenericFailureReasonDAOImpl implements GenericFailureReasonDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private GenericFailureReasonRowMapper genericFailureReasonRowMapper;

    @Override
    public void insertGenericFailureReason(GenericPaymentFailureReason genericPaymentFailureReason) {
        String query = "INSERT INTO generic_failure_reason (id, code, name, description) VALUES (?,?,?,?)";

        jdbcTemplate.update(query, UUID.fromString(genericPaymentFailureReason.getId()), genericPaymentFailureReason.getCode(),
                genericPaymentFailureReason.getName(), genericPaymentFailureReason.getDescription());
    }

    @Override
    public boolean isGenericFailureReasonNonExists(String id) {
        String query = "SELECT * FROM generic_failure_reason where id=?";
        List<GenericPaymentFailureReason> genericPaymentFailureReasons = jdbcTemplate.query(query, new Object[]{UUID.fromString(id)}, genericFailureReasonRowMapper);

        return genericPaymentFailureReasons.isEmpty();
    }

    @Override
    public boolean isGenericFailureReasonWithCodeExists(String code) {
        String query = "SELECT * FROM generic_failure_reason where code=?";
        List<GenericPaymentFailureReason> genericPaymentFailureReasons = jdbcTemplate.query(query, new Object[]{code}, genericFailureReasonRowMapper);

        return !genericPaymentFailureReasons.isEmpty();
    }

    @Override
    public GenericPaymentFailureReason getGenericFailureReason(String id) {
        String query1 = "SELECT * FROM generic_failure_reason WHERE id = ?";

        return jdbcTemplate.queryForObject(query1, genericFailureReasonRowMapper, UUID.fromString(id));
    }

    @Override
    public List<GenericPaymentFailureReason> getAllGenericFailureReasons(Long pageNumber, Long pageSize) {
        String query = "SELECT * FROM generic_failure_reason LIMIT ? OFFSET ?";

        return jdbcTemplate.query(
                query,
                genericFailureReasonRowMapper, pageSize, pageSize * (pageNumber - 1));
    }

    @Override
    public void deleteGenericFailureReason(String id) {
        String query = "DELETE FROM generic_failure_reason WHERE id = ?";
        jdbcTemplate.update(query, UUID.fromString(id));
    }

    @Override
    public void updateGenericFailureReason(String id, GenericPaymentFailureReason genericPaymentFailureReason) {
        String query = "UPDATE generic_failure_reason SET code=?, name=?, description=? WHERE id = ? ";

        jdbcTemplate.update(query, genericPaymentFailureReason.getCode(),
                genericPaymentFailureReason.getName(), genericPaymentFailureReason.getDescription(), UUID.fromString(id));
    }
}
