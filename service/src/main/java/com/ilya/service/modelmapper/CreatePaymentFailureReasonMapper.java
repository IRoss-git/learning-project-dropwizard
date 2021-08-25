package com.ilya.service.modelmapper;

import com.ilya.db.domain.GenericPaymentFailureReason;
import com.ilya.db.domain.PaymentFailureReason;
import com.learn.dropwizard.model.CreateUpdatePaymentFailureReasonDTO;
import org.springframework.stereotype.Component;

@Component
public class CreatePaymentFailureReasonMapper implements BaseMapper <CreateUpdatePaymentFailureReasonDTO, PaymentFailureReason>  {

    @Override
    public PaymentFailureReason convertToEntity(CreateUpdatePaymentFailureReasonDTO input) {
        PaymentFailureReason paymentFailureReason = new PaymentFailureReason();

        paymentFailureReason.setCode(input.getCode());
        paymentFailureReason.setName(input.getName());
        paymentFailureReason.setDescription(input.getDescription());

        return paymentFailureReason;
    }

    @Override
    public CreateUpdatePaymentFailureReasonDTO convertToDto(PaymentFailureReason input) {
        throw new UnsupportedOperationException("Converter not supported");
    }
}
