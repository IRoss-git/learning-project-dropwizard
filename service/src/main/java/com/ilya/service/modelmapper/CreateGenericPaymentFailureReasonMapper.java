package com.ilya.service.modelmapper;

import com.ilya.db.domain.GenericPaymentFailureReason;
import com.ilya.db.domain.PaymentProcessor;
import com.learn.dropwizard.model.CreateUpdateGenericPaymentFailureReasonDTO;
import org.springframework.stereotype.Component;

@Component
public class CreateGenericPaymentFailureReasonMapper implements BaseMapper <CreateUpdateGenericPaymentFailureReasonDTO, GenericPaymentFailureReason>  {
    @Override
    public GenericPaymentFailureReason convertToEntity(CreateUpdateGenericPaymentFailureReasonDTO input) {
        GenericPaymentFailureReason genericPaymentFailureReason = new GenericPaymentFailureReason();

        genericPaymentFailureReason.setCode(input.getCode());
        genericPaymentFailureReason.setName(input.getName());
        genericPaymentFailureReason.setDescription(input.getDescription());

        return genericPaymentFailureReason;
    }

    @Override
    public CreateUpdateGenericPaymentFailureReasonDTO convertToDto(GenericPaymentFailureReason input) {
        throw new UnsupportedOperationException("Converter not supported");
    }
}
