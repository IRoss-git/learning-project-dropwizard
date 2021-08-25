package com.ilya.service.modelmapper;

import com.ilya.db.domain.PaymentFailureReason;
import org.springframework.stereotype.Component;
import com.learn.dropwizard.model.ReadPaymentFailureReasonDTO;

@Component
public class ReadPaymentFailureReasonMapper implements BaseMapper<ReadPaymentFailureReasonDTO, PaymentFailureReason> {

    @Override
    public PaymentFailureReason convertToEntity(ReadPaymentFailureReasonDTO input) {
        throw new UnsupportedOperationException("Converter not supported");
    }

    @Override
    public ReadPaymentFailureReasonDTO convertToDto(PaymentFailureReason input) {
        ReadPaymentFailureReasonDTO failureReasonDTO = new ReadPaymentFailureReasonDTO();

        failureReasonDTO.setId(input.getId());
        failureReasonDTO.setCode(input.getCode());
        failureReasonDTO.setName(input.getName());
        failureReasonDTO.setDescription(input.getDescription());

        return failureReasonDTO;
    }
}
