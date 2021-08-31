package com.ilya.service.service;

import com.ilya.db.dao.PaymentProcessorDAO;
import com.ilya.db.domain.PaymentProcessor;
import com.ilya.service.exception.AlreadyExistException;
import com.ilya.service.modelmapper.CreatePaymentProcessorMapper;
import com.ilya.service.modelmapper.ReadPaymentProcessorMapper;
import com.learn.dropwizard.model.CreateUpdatePaymentProcessorDTO;
import com.learn.dropwizard.model.ReadPaymentProcessorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.UUID;

@Service
public class PaymentProcessorServiceImpl implements PaymentProcessorService {

    @Autowired
    private PaymentProcessorDAO paymentProcessorDAO;

    @Autowired
    private CreatePaymentProcessorMapper createPaymentProcessorMapper;

    @Autowired
    private ReadPaymentProcessorMapper readPaymentProcessorMapper;

    private static final String NOT_FOUND = "Payment processor with id %s not found";

    @Override
    public ReadPaymentProcessorDTO createPaymentProcessor(CreateUpdatePaymentProcessorDTO createUpdatePaymentProcessorDTO) {
        PaymentProcessor paymentProcessor = createPaymentProcessorMapper.convertToEntity(createUpdatePaymentProcessorDTO);

        if(paymentProcessorDAO.isPaymentProcessorWithKeyExists(paymentProcessor.getKey())){
            throw new AlreadyExistException("Key: " + paymentProcessor.getKey() + " already exists");
        }
        paymentProcessor.setId(String.valueOf(UUID.randomUUID()));

        paymentProcessorDAO.insertPaymentProcessor(paymentProcessor);

        return readPaymentProcessorMapper.convertToDto(paymentProcessor);
    }

    @Override
    public List<ReadPaymentProcessorDTO> getAllPaymentProcessors(Long pageNumber, Long pageSize) {
        return readPaymentProcessorMapper.convertListToDto(paymentProcessorDAO.getAllPaymentProcessors(pageNumber,pageSize));
    }

    @Override
    public void deletePaymentProcessor(String id) {
        if(paymentProcessorDAO.isPaymentProcessorNonExists(id)){
            throw new NotFoundException(String.format(NOT_FOUND,id));
        }

        paymentProcessorDAO.deletePaymentProcessor(id);
    }

    @Override
    public ReadPaymentProcessorDTO updatePaymentProcessor(String id, CreateUpdatePaymentProcessorDTO createUpdatePaymentProcessorDTO) {
        if(paymentProcessorDAO.isPaymentProcessorNonExists(id)){
            throw new NotFoundException(String.format(NOT_FOUND,id));
        }

        if(paymentProcessorDAO.isPaymentProcessorWithKeyExists(createUpdatePaymentProcessorDTO.getKey())){
            throw new AlreadyExistException("Key: " + createUpdatePaymentProcessorDTO.getKey() + " already exists");
        }
        PaymentProcessor paymentProcessor = createPaymentProcessorMapper.convertToEntity(createUpdatePaymentProcessorDTO);

        paymentProcessorDAO.updatePaymentProcessor(id, paymentProcessor);

        return readPaymentProcessorMapper.convertToDto(paymentProcessor);
    }

    @Override
    public ReadPaymentProcessorDTO getPaymentProcessor(String id) {
        if(paymentProcessorDAO.isPaymentProcessorNonExists(id)){
            throw new NotFoundException(String.format(NOT_FOUND,id));
        }
        PaymentProcessor paymentProcessor = paymentProcessorDAO.getPaymentProcessor(id);

        return readPaymentProcessorMapper.convertToDto(paymentProcessor);
    }

    public void isPaymentProcessorExists(String id) {
        if(paymentProcessorDAO.isPaymentProcessorNonExists(id)){
            throw new NotFoundException(String.format(NOT_FOUND,id));
        }
    }
}
