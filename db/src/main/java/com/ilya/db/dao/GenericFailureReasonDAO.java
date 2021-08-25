package com.ilya.db.dao;

import com.ilya.db.domain.GenericPaymentFailureReason;
import com.ilya.db.rowmapper.GenericFailureReasonRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class GenericFailureReasonDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private GenericFailureReasonRowMapper genericFailureReasonRowMapper;

    public void insertGenericFailureReason(GenericPaymentFailureReason genericPaymentFailureReason) {
        String query = "INSERT INTO generic_failure_reason (id, code, name, description) VALUES (?,?,?,?)";

        jdbcTemplate.update(query, UUID.fromString(genericPaymentFailureReason.getId()), genericPaymentFailureReason.getCode(),
                genericPaymentFailureReason.getName(), genericPaymentFailureReason.getDescription());

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

    public boolean isGenericFailureReasonNonExists(String id) {
        String query = "SELECT * FROM generic_failure_reason where id=?";
        List<GenericPaymentFailureReason> genericPaymentFailureReasons = jdbcTemplate.query(query, new Object[]{UUID.fromString(id)}, genericFailureReasonRowMapper);

        return genericPaymentFailureReasons.isEmpty();
    }

    public boolean isGenericFailureReasonWithCodeExists(String code) {
        String query = "SELECT * FROM generic_failure_reason where code=?";
        List<GenericPaymentFailureReason> genericPaymentFailureReasons = jdbcTemplate.query(query, new Object[]{code}, genericFailureReasonRowMapper);

        return !genericPaymentFailureReasons.isEmpty();
    }

    public GenericPaymentFailureReason getGenericFailureReason(String id) {
        String query1 = "SELECT * FROM generic_failure_reason WHERE id = ?";

        return jdbcTemplate.queryForObject(query1, genericFailureReasonRowMapper, UUID.fromString(id));
    }

    public List<GenericPaymentFailureReason> getAllGenericFailureReasons(Long pageNumber, Long pageSize) {
        String query = "SELECT * FROM generic_failure_reason LIMIT ? OFFSET ?";

        return jdbcTemplate.query(
                query,
                genericFailureReasonRowMapper, pageSize, pageSize * (pageNumber - 1));
    }

    public void deleteGenericFailureReason(String id) {
        String query = "DELETE FROM generic_failure_reason WHERE id = ?";
        jdbcTemplate.update(query, UUID.fromString(id));
    }

    public void updateGenericFailureReason(String id, GenericPaymentFailureReason genericPaymentFailureReason) {
        String query = "UPDATE generic_failure_reason SET code=?, name=?, description=? WHERE id = ? ";

        jdbcTemplate.update(query, genericPaymentFailureReason.getCode(),
                genericPaymentFailureReason.getName(), genericPaymentFailureReason.getDescription(), UUID.fromString(id));
    }
}
