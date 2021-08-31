package com.ilya.db.dao;

import com.ilya.db.domain.GenericPaymentFailureReason;

import java.util.List;

public interface GenericFailureReasonDAO {

    void insertGenericFailureReason(GenericPaymentFailureReason genericPaymentFailureReason);

    boolean isGenericFailureReasonNonExists(String id);

    boolean isGenericFailureReasonWithCodeExists(String code);

    GenericPaymentFailureReason getGenericFailureReason(String id);

    List<GenericPaymentFailureReason> getAllGenericFailureReasons(Long pageNumber, Long pageSize);

    void deleteGenericFailureReason(String id);

    void updateGenericFailureReason(String id, GenericPaymentFailureReason genericPaymentFailureReason);
}
