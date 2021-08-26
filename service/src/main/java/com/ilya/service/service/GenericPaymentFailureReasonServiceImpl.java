package com.ilya.service.service;

import com.ilya.db.dao.GenericFailureReasonDAO;
import com.ilya.db.domain.GenericPaymentFailureReason;
import com.ilya.service.exception.AlreadyExistException;
import com.ilya.service.modelmapper.CreateGenericPaymentFailureReasonMapper;
import com.ilya.service.modelmapper.ReadGenericPaymentFailureReasonMapper;
import com.learn.dropwizard.model.CreateUpdateGenericPaymentFailureReasonDTO;
import com.learn.dropwizard.model.ReadGenericPaymentFailureReasonDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.UUID;

@Service
public class GenericPaymentFailureReasonServiceImpl implements GenericPaymentFailureReasonService{

    @Autowired
    private GenericFailureReasonDAO genericFailureReasonDAO;

    @Autowired
    private CreateGenericPaymentFailureReasonMapper createGenericPaymentFailureReasonMapper;

    @Autowired
    private ReadGenericPaymentFailureReasonMapper readGenericPaymentFailureReasonMapper;

    private static final String NOT_FOUND = "Generic payment reason with id %s not found";

    @Override
    public ReadGenericPaymentFailureReasonDTO createGenericPaymentFailureReason(CreateUpdateGenericPaymentFailureReasonDTO createUpdateGenericPaymentFailureReasonDTO) {
        GenericPaymentFailureReason genericPaymentFailureReason = createGenericPaymentFailureReasonMapper.convertToEntity(createUpdateGenericPaymentFailureReasonDTO);

        if(genericFailureReasonDAO.isGenericFailureReasonWithCodeExists(genericPaymentFailureReason.getCode())){
            throw new AlreadyExistException("Code: " + genericPaymentFailureReason.getCode() + " already exists");
        }
        genericPaymentFailureReason.setId(String.valueOf(UUID.randomUUID()));

        genericFailureReasonDAO.insertGenericFailureReason(genericPaymentFailureReason);

        return readGenericPaymentFailureReasonMapper.convertToDto(genericPaymentFailureReason);
    }

    @Override
    public List<ReadGenericPaymentFailureReasonDTO> getAllGenericPaymentFailureReasons(Long pageNumber, Long pageSize) {
        return readGenericPaymentFailureReasonMapper.convertListToDto(genericFailureReasonDAO.getAllGenericFailureReasons(pageNumber,pageSize));
    }

    @Override
    public void deleteGenericPaymentFailureReason(String id) {
        if(genericFailureReasonDAO.isGenericFailureReasonNonExists(id)){
            throw new NotFoundException(String.format(NOT_FOUND,id));
        }

        genericFailureReasonDAO.deleteGenericFailureReason(id);
    }

    @Override
    public ReadGenericPaymentFailureReasonDTO updateGenericPaymentFailureReason(String id, CreateUpdateGenericPaymentFailureReasonDTO createUpdateGenericPaymentFailureReasonDTO) {
        if(genericFailureReasonDAO.isGenericFailureReasonNonExists(id)){
            throw new NotFoundException(String.format(NOT_FOUND,id));
        }

        GenericPaymentFailureReason genericPaymentFailureReason = createGenericPaymentFailureReasonMapper.convertToEntity(createUpdateGenericPaymentFailureReasonDTO);

        genericFailureReasonDAO.updateGenericFailureReason(id, genericPaymentFailureReason);

        return readGenericPaymentFailureReasonMapper.convertToDto(genericPaymentFailureReason);
    }

    @Override
    public ReadGenericPaymentFailureReasonDTO getGenericPaymentFailureReason(String id) {
        if(genericFailureReasonDAO.isGenericFailureReasonNonExists(id)){
            throw new NotFoundException(String.format(NOT_FOUND,id));
        }

        GenericPaymentFailureReason genericPaymentFailureReason = genericFailureReasonDAO.getGenericFailureReason(id);

        return readGenericPaymentFailureReasonMapper.convertToDto(genericPaymentFailureReason);

    }

    @Override
    public void isGenericReasonExists(String id) {
        if(genericFailureReasonDAO.isGenericFailureReasonNonExists(id)){
            throw new NotFoundException(String.format(NOT_FOUND,id));
        }
    }
}
