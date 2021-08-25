package com.ilya.db.rowmapper;

import com.ilya.db.domain.GenericPaymentFailureReason;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class GenericFailureReasonRowMapper implements RowMapper<GenericPaymentFailureReason> {

    @Override
    public GenericPaymentFailureReason mapRow(ResultSet rs, int i) throws SQLException {
        GenericPaymentFailureReason genericPaymentFailureReason = new GenericPaymentFailureReason();

        genericPaymentFailureReason.setId(rs.getString("id"));
        genericPaymentFailureReason.setCode(rs.getString("code"));
        genericPaymentFailureReason.setName(rs.getString("name"));
        genericPaymentFailureReason.setDescription(rs.getString("description"));

        return genericPaymentFailureReason;
    }
}
