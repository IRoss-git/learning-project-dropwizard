package com.ilya.db.rowmapper;

import com.ilya.db.domain.PaymentFailureReason;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class FailureReasonRowMapper implements RowMapper<PaymentFailureReason> {

    @Override
    public PaymentFailureReason mapRow(ResultSet rs, int i) throws SQLException {
        PaymentFailureReason paymentFailureReason = new PaymentFailureReason();

        paymentFailureReason.setId(rs.getString("id"));
        paymentFailureReason.setCode(rs.getString("code"));
        paymentFailureReason.setName(rs.getString("name"));
        paymentFailureReason.setDescription(rs.getString("description"));

        return paymentFailureReason;
    }
}
