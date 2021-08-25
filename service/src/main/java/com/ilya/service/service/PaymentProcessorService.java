package com.ilya.service.service;

import java.util.List;
import com.learn.dropwizard.model.ReadPaymentProcessorDTO;
import com.learn.dropwizard.model.CreateUpdatePaymentProcessorDTO;

public interface PaymentProcessorService {

    ReadPaymentProcessorDTO createPaymentProcessor(CreateUpdatePaymentProcessorDTO createUpdatePaymentProcessorDTO);

    List<ReadPaymentProcessorDTO> getAllPaymentProcessors(Long pageNumber, Long pageSize);

    void deletePaymentProcessor(String id);

    ReadPaymentProcessorDTO updatePaymentProcessor(String id, CreateUpdatePaymentProcessorDTO createUpdatePaymentProcessorDTO);

    ReadPaymentProcessorDTO getPaymentProcessor(String id);

    void isPaymentProcessorExists(String id);
}
