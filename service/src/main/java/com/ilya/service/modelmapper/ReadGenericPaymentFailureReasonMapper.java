package com.ilya.service.modelmapper;

import com.ilya.db.domain.GenericPaymentFailureReason;
import com.learn.dropwizard.model.ReadGenericPaymentFailureReasonDTO;
import org.springframework.stereotype.Component;

@Component
public class ReadGenericPaymentFailureReasonMapper implements BaseMapper<ReadGenericPaymentFailureReasonDTO, GenericPaymentFailureReason> {

    @Override
    public GenericPaymentFailureReason convertToEntity(ReadGenericPaymentFailureReasonDTO input) {
        throw new UnsupportedOperationException("Converter not supported");
    }

    @Override
    public ReadGenericPaymentFailureReasonDTO convertToDto(GenericPaymentFailureReason input) {
        ReadGenericPaymentFailureReasonDTO genericPaymentFailureReasonDTO = new ReadGenericPaymentFailureReasonDTO();

        genericPaymentFailureReasonDTO.setId(input.getId());
        genericPaymentFailureReasonDTO.setCode(input.getCode());
        genericPaymentFailureReasonDTO.setName(input.getName());
        genericPaymentFailureReasonDTO.setDescription(input.getDescription());

        return genericPaymentFailureReasonDTO;
    }
}
