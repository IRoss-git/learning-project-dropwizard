package com.ilya.service.service;

import com.learn.dropwizard.model.ReadPaymentFailureReasonDTO;
import com.learn.dropwizard.model.CreateUpdatePaymentFailureReasonDTO;
import com.learn.dropwizard.model.RefPaymentFailureReasonDTO;


import java.util.List;

public interface PaymentFailureReasonService {

    ReadPaymentFailureReasonDTO createPaymentFailureReason(String paymentProcessorId, CreateUpdatePaymentFailureReasonDTO createUpdatePaymentFailureReasonDTO);

    List<ReadPaymentFailureReasonDTO> getAllPaymentFailureReasonsByProcessor(String paymentProcessorId, Long pageNumber, Long pageSize);

    List <ReadPaymentFailureReasonDTO> getAllPaymentFailureReasonsByGenericReason(String genericReasonId, Long pageNumber, Long pageSize);

    void deletePaymentFailureReason(String paymentProcessorId, String id);

    ReadPaymentFailureReasonDTO updatePaymentFailureReason(String paymentProcessorId, String id, CreateUpdatePaymentFailureReasonDTO createUpdatePaymentFailureReasonDTO);

    ReadPaymentFailureReasonDTO getPaymentFailureReason(String paymentProcessorId, String id);

    ReadPaymentFailureReasonDTO addFailureReasonToGenericGroup(String genericReasonId, RefPaymentFailureReasonDTO refPaymentFailureReasonDTO);
}
