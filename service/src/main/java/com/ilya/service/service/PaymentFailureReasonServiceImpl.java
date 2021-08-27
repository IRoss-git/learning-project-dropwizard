package com.ilya.service.service;

import com.ilya.db.dao.PaymentFailureReasonDAO;
import com.ilya.db.domain.PaymentFailureReason;
import com.ilya.db.domain.PaymentProcessor;
import com.ilya.service.exception.AlreadyExistException;
import com.ilya.service.modelmapper.CreatePaymentFailureReasonMapper;
import com.ilya.service.modelmapper.ReadPaymentFailureReasonMapper;
import com.ilya.service.modelmapper.ReadPaymentProcessorMapper;
import com.learn.dropwizard.model.RefPaymentFailureReasonDTO;
import com.learn.dropwizard.model.CreateUpdatePaymentFailureReasonDTO;
import com.learn.dropwizard.model.ReadPaymentFailureReasonDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PaymentFailureReasonServiceImpl implements PaymentFailureReasonService {

    @Autowired
    private PaymentFailureReasonDAO paymentFailureReasonDAO;

    @Autowired
    private CreatePaymentFailureReasonMapper createPaymentFailureReasonMapper;

    @Autowired
    private ReadPaymentFailureReasonMapper readPaymentFailureReasonMapper;

    @Autowired
    private ReadPaymentProcessorMapper readPaymentProcessorMapper;

    @Autowired
    private PaymentProcessorService paymentProcessorService;

    @Autowired
    private GenericPaymentFailureReasonService genericPaymentFailureReasonService;

    private static final String NOT_FOUND = "Payment failure reason with id %s not found";

    @Override
    public ReadPaymentFailureReasonDTO createPaymentFailureReason(String paymentProcessorId, CreateUpdatePaymentFailureReasonDTO createUpdatePaymentFailureReasonDTO) {
        PaymentProcessor paymentProcessor = readPaymentProcessorMapper.convertToEntity(paymentProcessorService.getPaymentProcessor(paymentProcessorId));

        PaymentFailureReason paymentFailureReason = createPaymentFailureReasonMapper.convertToEntity(createUpdatePaymentFailureReasonDTO);

        checkIfCodeExists(paymentFailureReason.getCode(), paymentProcessorId);

        paymentFailureReason.setId(String.valueOf(UUID.randomUUID()));
        paymentFailureReason.setPaymentProcessor(paymentProcessor);

        paymentFailureReasonDAO.insertPaymentFailureReason(paymentFailureReason);

        return readPaymentFailureReasonMapper.convertToDto(paymentFailureReason);
    }

    @Override
    public List<ReadPaymentFailureReasonDTO> getAllPaymentFailureReasonsByProcessor(String paymentProcessorId, Long pageNumber, Long pageSize) {
        paymentProcessorService.isPaymentProcessorExists(paymentProcessorId);

        return readPaymentFailureReasonMapper.convertListToDto(paymentFailureReasonDAO.getAllPaymentFailureReasonsByPaymentProcessor(paymentProcessorId, pageNumber, pageSize));
    }

    @Override
    public List<ReadPaymentFailureReasonDTO> getAllPaymentFailureReasonsByGenericReason(String genericReasonId, Long pageNumber, Long pageSize) {
        return readPaymentFailureReasonMapper.convertListToDto(paymentFailureReasonDAO.getAllPaymentFailureReasonsByGenericReason(genericReasonId, pageNumber, pageSize));
    }

    @Override
    public void deletePaymentFailureReason(String paymentProcessorId, String id) {
        paymentProcessorService.isPaymentProcessorExists(paymentProcessorId);

        if (paymentFailureReasonDAO.isPaymentFailureReasonNonExists(id)) {
            throw new NotFoundException(String.format(NOT_FOUND, id));
        }

        paymentFailureReasonDAO.deletePaymentFailureReason(id);
    }

    @Override
    public ReadPaymentFailureReasonDTO updatePaymentFailureReason(String paymentProcessorId, String id, CreateUpdatePaymentFailureReasonDTO createUpdatePaymentFailureReasonDTO) {
        paymentProcessorService.isPaymentProcessorExists(paymentProcessorId);

        if (paymentFailureReasonDAO.isPaymentFailureReasonNonExists(id)) {
            throw new NotFoundException(String.format(NOT_FOUND, id));
        }
        checkIfCodeExists(createUpdatePaymentFailureReasonDTO.getCode(), paymentProcessorId);

        PaymentFailureReason paymentFailureReason = createPaymentFailureReasonMapper.convertToEntity(createUpdatePaymentFailureReasonDTO);

        paymentFailureReasonDAO.updatePaymentFailureReason(id, paymentFailureReason);

        return readPaymentFailureReasonMapper.convertToDto(paymentFailureReason);
    }

    @Override
    public ReadPaymentFailureReasonDTO getPaymentFailureReason(String paymentProcessorId, String id) {
        paymentProcessorService.isPaymentProcessorExists(paymentProcessorId);

        if (paymentFailureReasonDAO.isPaymentFailureReasonNonExists(id)) {
            throw new NotFoundException(String.format(NOT_FOUND, id));
        }

        PaymentFailureReason paymentFailureReason = paymentFailureReasonDAO.getPaymentFailureReason(id);

        return readPaymentFailureReasonMapper.convertToDto(paymentFailureReason);
    }

    public void addFailureReasonToGenericGroup(String genericReasonId, List<RefPaymentFailureReasonDTO> refPaymentFailureReasonDTO) {
        List<String> ids = refPaymentFailureReasonDTO.stream()
                .map(RefPaymentFailureReasonDTO::getReasonId)
                .collect(Collectors.toList());

        validateMapping(genericReasonId, ids);
        paymentFailureReasonDAO.batchInsertMapping(genericReasonId, ids);
    }

    @Override
    public void deleteMapping(String genericReasonId, String reasonId) {
        checkGenericReasonAndReasonExistence(genericReasonId, reasonId);

        paymentFailureReasonDAO.deleteReasonMappingByGenericIdAndReasonId(genericReasonId, reasonId);
    }

    private void validateMapping(String genericReasonId, List<String> reasonIds) {
        for (String id : reasonIds) {
            checkGenericReasonAndReasonExistence(genericReasonId, id);
            checkIfMappingAlreadyExists(genericReasonId, id);
        }
    }

    private void checkGenericReasonAndReasonExistence(String genericReasonId, String reasonId) {
        genericPaymentFailureReasonService.isGenericReasonExists(genericReasonId);

        if (paymentFailureReasonDAO.isPaymentFailureReasonNonExists(reasonId)) {
            throw new NotFoundException(String.format(NOT_FOUND, reasonId));
        }
    }

    private void checkIfMappingAlreadyExists(String genericReasonId, String reasonId) {
        if (paymentFailureReasonDAO.isMappingExists(genericReasonId, reasonId)) {
            throw new AlreadyExistException("Mapping already exists between " + genericReasonId + " and " + reasonId);
        }
    }

    private void checkIfCodeExists(String code, String processorId) {
        if (paymentFailureReasonDAO.isPaymentFailureReasonWithCodeExists(code, processorId)) {
            throw new AlreadyExistException("Code: " + code + " already exists");
        }
    }
}
