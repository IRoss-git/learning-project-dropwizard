package com.ilya.service.service;

import com.ilya.db.dao.PaymentFailureReasonDAO;
import com.ilya.db.domain.PaymentFailureReason;
import com.ilya.db.domain.PaymentProcessor;
import com.ilya.service.exception.AlreadyExistException;
import com.ilya.service.modelmapper.CreatePaymentFailureReasonMapper;
import com.ilya.service.modelmapper.ReadPaymentFailureReasonMapper;
import com.ilya.service.modelmapper.ReadPaymentProcessorMapper;
import liquibase.pro.packaged.T;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.learn.dropwizard.model.CreateUpdatePaymentFailureReasonDTO;
import com.learn.dropwizard.model.ReadPaymentFailureReasonDTO;
import com.learn.dropwizard.model.RefPaymentFailureReasonDTO;

import javax.ws.rs.NotFoundException;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentFailureReasonServiceImplTest {

    @Mock
    private PaymentFailureReasonDAO paymentFailureReasonDAO;

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
        CreateUpdatePaymentFailureReasonDTO createUpdatePaymentFailureReasonDTO = mock(CreateUpdatePaymentFailureReasonDTO.class);
        ReadPaymentFailureReasonDTO readPaymentFailureReasonDTO = mock(ReadPaymentFailureReasonDTO.class);
        PaymentFailureReason paymentFailureReason = mock(PaymentFailureReason.class);

        when(createPaymentFailureReasonMapper.convertToEntity(createUpdatePaymentFailureReasonDTO)).thenReturn(paymentFailureReason);
        when(readPaymentFailureReasonMapper.convertToDto(paymentFailureReason)).thenReturn(readPaymentFailureReasonDTO);

        ReadPaymentFailureReasonDTO expected = paymentFailureReasonService.createPaymentFailureReason(TEST_UUID, createUpdatePaymentFailureReasonDTO);

        assertThat(expected).isNotNull();
        assertThat(expected).isSameAs(readPaymentFailureReasonDTO);

    }

    @Test
    void getAllPaymentFailureReasonsByProcessor() {
        List<PaymentFailureReason> paymentFailureReasons = mock(List.class);
        List<ReadPaymentFailureReasonDTO> readPaymentFailureReasonDTOS = mock(List.class);

        when(paymentFailureReasonDAO.getAllPaymentFailureReasonsByPaymentProcessor(TEST_UUID, 1L, 3L)).thenReturn(paymentFailureReasons);
        when(readPaymentFailureReasonMapper.convertListToDto(paymentFailureReasons)).thenReturn(readPaymentFailureReasonDTOS);

        List<ReadPaymentFailureReasonDTO> expected = paymentFailureReasonService.getAllPaymentFailureReasonsByProcessor(TEST_UUID, 1L, 3L);
        assertThat(expected).isNotNull();
        assertThat(expected).isSameAs(readPaymentFailureReasonDTOS);
    }

    @Test
    void getAllPaymentFailureReasonsByGenericReason() {
        List<PaymentFailureReason> paymentFailureReasons = mock(List.class);
        List<ReadPaymentFailureReasonDTO> readPaymentFailureReasonDTOS = mock(List.class);

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
        CreateUpdatePaymentFailureReasonDTO createUpdatePaymentFailureReasonDTO = mock(CreateUpdatePaymentFailureReasonDTO.class);
        ReadPaymentFailureReasonDTO readPaymentFailureReasonDTO = mock(ReadPaymentFailureReasonDTO.class);
        PaymentFailureReason paymentFailureReason = mock(PaymentFailureReason.class);

        when(createPaymentFailureReasonMapper.convertToEntity(createUpdatePaymentFailureReasonDTO)).thenReturn(paymentFailureReason);
        when(readPaymentFailureReasonMapper.convertToDto(paymentFailureReason)).thenReturn(readPaymentFailureReasonDTO);

        ReadPaymentFailureReasonDTO expected = paymentFailureReasonService.updatePaymentFailureReason(TEST_UUID, TEST_UUID, createUpdatePaymentFailureReasonDTO);

        assertThat(expected).isNotNull();
        verify(paymentFailureReasonDAO, times(1)).updatePaymentFailureReason(TEST_UUID, paymentFailureReason);
    }

    @Test
    void getPaymentFailureReason() {
        ReadPaymentFailureReasonDTO readPaymentFailureReasonDTO = mock(ReadPaymentFailureReasonDTO.class);
        PaymentFailureReason paymentFailureReason = mock(PaymentFailureReason.class);

        when(paymentFailureReasonDAO.getPaymentFailureReason(TEST_UUID)).thenReturn(paymentFailureReason);
        when(readPaymentFailureReasonMapper.convertToDto(paymentFailureReason)).thenReturn(readPaymentFailureReasonDTO);

        ReadPaymentFailureReasonDTO expected = paymentFailureReasonService.getPaymentFailureReason(TEST_UUID, TEST_UUID);

        assertThat(expected).isNotNull();
        assertThat(expected).isSameAs(readPaymentFailureReasonDTO);
    }

    @Test
    void addFailureReasonToGenericGroup() {
        ReadPaymentFailureReasonDTO readPaymentFailureReasonDTO = mock(ReadPaymentFailureReasonDTO.class);
        PaymentFailureReason paymentFailureReason = mock(PaymentFailureReason.class);
        RefPaymentFailureReasonDTO refPaymentFailureReasonDTO = mock(RefPaymentFailureReasonDTO.class);

        when(paymentFailureReasonDAO.getPaymentFailureReason(null)).thenReturn(paymentFailureReason);
        when(readPaymentFailureReasonMapper.convertToDto(paymentFailureReason)).thenReturn(readPaymentFailureReasonDTO);

        ReadPaymentFailureReasonDTO expected = paymentFailureReasonService.addFailureReasonToGenericGroup(null, refPaymentFailureReasonDTO);
        assertThat(expected).isNotNull();
        assertThat(expected).isSameAs(readPaymentFailureReasonDTO);
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
        RefPaymentFailureReasonDTO refPaymentFailureReasonDTO = mock(RefPaymentFailureReasonDTO.class);

        when(paymentFailureReasonDAO.isMappingExists(null, null)).thenReturn(true);

        assertThrows(AlreadyExistException.class, () -> paymentFailureReasonService.addFailureReasonToGenericGroup(null, refPaymentFailureReasonDTO));
    }

    @Test
    void deleteNonExistingMapping() {
        when(paymentFailureReasonDAO.isPaymentFailureReasonNonExists(TEST_UUID)).thenReturn(true);

        assertThrows(NotFoundException.class, () -> paymentFailureReasonService.deleteMapping(TEST_UUID, TEST_UUID));
    }

    @Test
    void createWithExistingCode() {
        CreateUpdatePaymentFailureReasonDTO createUpdatePaymentFailureReasonDTO = mock(CreateUpdatePaymentFailureReasonDTO.class);
        PaymentFailureReason paymentFailureReason = mock(PaymentFailureReason.class);

        when(createPaymentFailureReasonMapper.convertToEntity(createUpdatePaymentFailureReasonDTO)).thenReturn(paymentFailureReason);
        when(paymentFailureReasonDAO.isPaymentFailureReasonWithCodeExists(null,TEST_UUID)).thenReturn(true);

        assertThrows(AlreadyExistException.class, () -> paymentFailureReasonService.createPaymentFailureReason(TEST_UUID,createUpdatePaymentFailureReasonDTO));
    }
}
