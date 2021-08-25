package com.ilya.service.modelmapper;

import com.ilya.db.domain.PaymentProcessor;
import com.learn.dropwizard.model.CreateUpdatePaymentProcessorDTO;
import org.springframework.stereotype.Component;

@Component
public class CreatePaymentProcessorMapper implements BaseMapper<CreateUpdatePaymentProcessorDTO, PaymentProcessor> {

    @Override
    public PaymentProcessor convertToEntity(CreateUpdatePaymentProcessorDTO input) {
        PaymentProcessor paymentProcessor = new PaymentProcessor();

        paymentProcessor.setKey(input.getKey());
        paymentProcessor.setName(input.getName());
        paymentProcessor.setDescription(input.getDescription());

        return paymentProcessor;
    }

    @Override
    public CreateUpdatePaymentProcessorDTO convertToDto(PaymentProcessor paymentProcessor) {
        throw new UnsupportedOperationException("Converter not supported");
    }
}
