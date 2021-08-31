package com.ilya.service.service;

import com.ilya.db.dao.PaymentFailureReasonDAOImpl;
import com.ilya.db.domain.PaymentFailureReason;
import com.ilya.service.exception.AlreadyExistException;
import com.ilya.service.modelmapper.CreatePaymentFailureReasonMapper;
import com.ilya.service.modelmapper.ReadPaymentFailureReasonMapper;
import com.ilya.service.modelmapper.ReadPaymentProcessorMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.learn.dropwizard.model.CreateUpdatePaymentFailureReasonDTO;
import com.learn.dropwizard.model.ReadPaymentFailureReasonDTO;
import com.learn.dropwizard.model.RefPaymentFailureReasonDTO;

import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentFailureReasonServiceImplTest {

    @Mock
    private PaymentFailureReasonDAOImpl paymentFailureReasonDAO;

    @Mock
    private CreatePaymentFailureReasonMapper createPaymentFailureReasonMapper;

    @Mock
    private ReadPaymentFailureReasonMapper readPaymentFailureReasonMapper;

    @Mock
    private ReadPaymentProcessorMapper readPaymentProcessorMapper;

    @Mock
    private PaymentProcessorService paymentProcessorService;

    @Mock
    private GenericPaymentFailureReasonService genericPaymentFailureReasonService;

    @InjectMocks
    private PaymentFailureReasonServiceImpl paymentFailureReasonService;

    private static final String TEST_UUID = "123e4567-e89b-12d3-a456-426614174000";

    @Test
    void createPaymentFailureReason() {
        CreateUpdatePaymentFailureReasonDTO createUpdatePaymentFailureReasonDTO = new CreateUpdatePaymentFailureReasonDTO();
        ReadPaymentFailureReasonDTO readPaymentFailureReasonDTO = new ReadPaymentFailureReasonDTO();
        PaymentFailureReason paymentFailureReason = new PaymentFailureReason();

        when(createPaymentFailureReasonMapper.convertToEntity(createUpdatePaymentFailureReasonDTO)).thenReturn(paymentFailureReason);
        when(readPaymentFailureReasonMapper.convertToDto(paymentFailureReason)).thenReturn(readPaymentFailureReasonDTO);

        ReadPaymentFailureReasonDTO expected = paymentFailureReasonService.createPaymentFailureReason(TEST_UUID, createUpdatePaymentFailureReasonDTO);

        assertThat(expected).isNotNull();
        assertThat(expected).isSameAs(readPaymentFailureReasonDTO);

    }

    @Test
    void getAllPaymentFailureReasonsByProcessor() {
        List<PaymentFailureReason> paymentFailureReasons = new ArrayList<>();
        List<ReadPaymentFailureReasonDTO> readPaymentFailureReasonDTOS = new ArrayList<>();

        when(paymentFailureReasonDAO.getAllPaymentFailureReasonsByPaymentProcessor(TEST_UUID, 1L, 3L)).thenReturn(paymentFailureReasons);
        when(readPaymentFailureReasonMapper.convertListToDto(paymentFailureReasons)).thenReturn(readPaymentFailureReasonDTOS);

        List<ReadPaymentFailureReasonDTO> expected = paymentFailureReasonService.getAllPaymentFailureReasonsByProcessor(TEST_UUID, 1L, 3L);
        assertThat(expected).isNotNull();
        assertThat(expected).isSameAs(readPaymentFailureReasonDTOS);
    }

    @Test
    void getAllPaymentFailureReasonsByGenericReason() {
        List<PaymentFailureReason> paymentFailureReasons = new ArrayList<>();
        List<ReadPaymentFailureReasonDTO> readPaymentFailureReasonDTOS = new ArrayList<>();

        when(paymentFailureReasonDAO.getAllPaymentFailureReasonsByGenericReason(TEST_UUID, 1L, 3L)).thenReturn(paymentFailureReasons);
        when(readPaymentFailureReasonMapper.convertListToDto(paymentFailureReasons)).thenReturn(readPaymentFailureReasonDTOS);

        List<ReadPaymentFailureReasonDTO> expected = paymentFailureReasonService.getAllPaymentFailureReasonsByGenericReason(TEST_UUID, 1L, 3L);
        assertThat(expected).isNotNull();
        assertThat(expected).isSameAs(readPaymentFailureReasonDTOS);
    }

    @Test
    void deletePaymentFailureReason() {

        paymentFailureReasonService.deletePaymentFailureReason(TEST_UUID, TEST_UUID);

        verify(paymentFailureReasonDAO, times(1)).deletePaymentFailureReason(TEST_UUID);
    }

    @Test
    void updatePaymentFailureReason() {
        CreateUpdatePaymentFailureReasonDTO createUpdatePaymentFailureReasonDTO = new CreateUpdatePaymentFailureReasonDTO();
        ReadPaymentFailureReasonDTO readPaymentFailureReasonDTO = new ReadPaymentFailureReasonDTO();
        PaymentFailureReason paymentFailureReason = new PaymentFailureReason();

        when(createPaymentFailureReasonMapper.convertToEntity(createUpdatePaymentFailureReasonDTO)).thenReturn(paymentFailureReason);
        when(readPaymentFailureReasonMapper.convertToDto(paymentFailureReason)).thenReturn(readPaymentFailureReasonDTO);

        ReadPaymentFailureReasonDTO expected = paymentFailureReasonService.updatePaymentFailureReason(TEST_UUID, TEST_UUID, createUpdatePaymentFailureReasonDTO);

        assertThat(expected).isNotNull();
        verify(paymentFailureReasonDAO, times(1)).updatePaymentFailureReason(TEST_UUID, paymentFailureReason);
    }

    @Test
    void getPaymentFailureReason() {
        ReadPaymentFailureReasonDTO readPaymentFailureReasonDTO = new ReadPaymentFailureReasonDTO();
        PaymentFailureReason paymentFailureReason = new PaymentFailureReason();

        when(paymentFailureReasonDAO.getPaymentFailureReason(TEST_UUID)).thenReturn(paymentFailureReason);
        when(readPaymentFailureReasonMapper.convertToDto(paymentFailureReason)).thenReturn(readPaymentFailureReasonDTO);

        ReadPaymentFailureReasonDTO expected = paymentFailureReasonService.getPaymentFailureReason(TEST_UUID, TEST_UUID);

        assertThat(expected).isNotNull();
        assertThat(expected).isSameAs(readPaymentFailureReasonDTO);
    }

    @Test
    void addFailureReasonToGenericGroup() {
        List <RefPaymentFailureReasonDTO> refPaymentFailureReasonDTO = new ArrayList<>();

        paymentFailureReasonService.addFailureReasonToGenericGroup(null, refPaymentFailureReasonDTO);
        verify(paymentFailureReasonDAO, times(1)).batchInsertMapping(null, refPaymentFailureReasonDTO.stream().map(RefPaymentFailureReasonDTO::getReasonId).collect(Collectors.toList()));
    }

    @Test
    void deleteMapping() {
        paymentFailureReasonService.deleteMapping(TEST_UUID, TEST_UUID);

        verify(paymentFailureReasonDAO, times(1)).deleteReasonMappingByGenericIdAndReasonId(TEST_UUID, TEST_UUID);
    }

    @Test
    void getNonExisting() {
        when(paymentFailureReasonDAO.isPaymentFailureReasonNonExists(TEST_UUID)).thenReturn(true);

        assertThrows(NotFoundException.class, () -> paymentFailureReasonService.getPaymentFailureReason(TEST_UUID, TEST_UUID));
    }

    @Test
    void updateNonExisting() {
        when(paymentFailureReasonDAO.isPaymentFailureReasonNonExists(TEST_UUID)).thenReturn(true);

        assertThrows(NotFoundException.class, () -> paymentFailureReasonService.updatePaymentFailureReason(TEST_UUID, TEST_UUID, null));
    }

    @Test
    void deleteNonExisting() {
        when(paymentFailureReasonDAO.isPaymentFailureReasonNonExists(TEST_UUID)).thenReturn(true);

        assertThrows(NotFoundException.class, () -> paymentFailureReasonService.deletePaymentFailureReason(TEST_UUID, TEST_UUID));
    }

    @Test
    void addAlreadyExistingMapping() {
        List <RefPaymentFailureReasonDTO> refPaymentFailureReasonDTOs = new ArrayList<>();
        RefPaymentFailureReasonDTO refPaymentFailureReasonDTO = new RefPaymentFailureReasonDTO();
        refPaymentFailureReasonDTOs.add(refPaymentFailureReasonDTO);

        when(paymentFailureReasonDAO.isMappingExists(null, null)).thenReturn(true);

        assertThrows(AlreadyExistException.class, () -> paymentFailureReasonService.addFailureReasonToGenericGroup(null, refPaymentFailureReasonDTOs));
    }

    @Test
    void deleteNonExistingMapping() {
        when(paymentFailureReasonDAO.isPaymentFailureReasonNonExists(TEST_UUID)).thenReturn(true);

        assertThrows(NotFoundException.class, () -> paymentFailureReasonService.deleteMapping(TEST_UUID, TEST_UUID));
    }

    @Test
    void createWithExistingCode() {
        CreateUpdatePaymentFailureReasonDTO createUpdatePaymentFailureReasonDTO = new CreateUpdatePaymentFailureReasonDTO();
        PaymentFailureReason paymentFailureReason = new PaymentFailureReason();

        when(createPaymentFailureReasonMapper.convertToEntity(createUpdatePaymentFailureReasonDTO)).thenReturn(paymentFailureReason);
        when(paymentFailureReasonDAO.isPaymentFailureReasonWithCodeExists(null,TEST_UUID)).thenReturn(true);

        assertThrows(AlreadyExistException.class, () -> paymentFailureReasonService.createPaymentFailureReason(TEST_UUID,createUpdatePaymentFailureReasonDTO));
    }
}
