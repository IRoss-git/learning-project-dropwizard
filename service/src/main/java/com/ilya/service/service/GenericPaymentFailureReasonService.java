package com.ilya.service.service;

import com.learn.dropwizard.model.ReadGenericPaymentFailureReasonDTO;
import com.learn.dropwizard.model.CreateUpdateGenericPaymentFailureReasonDTO;

import java.util.List;

public interface GenericPaymentFailureReasonService {

    ReadGenericPaymentFailureReasonDTO createGenericPaymentFailureReason(CreateUpdateGenericPaymentFailureReasonDTO createUpdateGenericPaymentFailureReasonDTO);

    List<ReadGenericPaymentFailureReasonDTO> getAllGenericPaymentFailureReasons(Long pageNumber, Long pageSize);

    void deleteGenericPaymentFailureReason(String id);

    ReadGenericPaymentFailureReasonDTO updateGenericPaymentFailureReason(String id, CreateUpdateGenericPaymentFailureReasonDTO createUpdateGenericPaymentFailureReasonDTO);

    ReadGenericPaymentFailureReasonDTO getGenericPaymentFailureReason(String id);

    void isGenericReasonExists(String id);
}
