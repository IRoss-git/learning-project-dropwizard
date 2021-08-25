package com.ilya.service.modelmapper;

import com.ilya.db.domain.PaymentProcessor;
import com.learn.dropwizard.model.ReadPaymentProcessorDTO;
import org.springframework.stereotype.Component;

@Component
public class ReadPaymentProcessorMapper implements BaseMapper<ReadPaymentProcessorDTO, PaymentProcessor> {

    @Override
    public PaymentProcessor convertToEntity(ReadPaymentProcessorDTO input) {
        PaymentProcessor paymentProcessor = new PaymentProcessor();

        paymentProcessor.setId(input.getId());
        paymentProcessor.setKey(input.getKey());
        paymentProcessor.setName(input.getName());
        paymentProcessor.setDescription(input.getDescription());

        return paymentProcessor;
    }

    @Override
    public ReadPaymentProcessorDTO convertToDto(PaymentProcessor input) {
        ReadPaymentProcessorDTO paymentProcessorDTO = new ReadPaymentProcessorDTO();

        paymentProcessorDTO.setId(input.getId());
        paymentProcessorDTO.setKey(input.getKey());
        paymentProcessorDTO.setName(input.getName());
        paymentProcessorDTO.setDescription(input.getDescription());

        return paymentProcessorDTO;
    }
}
