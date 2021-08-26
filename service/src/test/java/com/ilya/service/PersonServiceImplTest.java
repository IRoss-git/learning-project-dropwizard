package com.ilya.service;

import com.ilya.db.dao.PaymentProcessorDAO;
import com.ilya.db.domain.PaymentProcessor;
import com.ilya.service.modelmapper.CreatePaymentProcessorMapper;
import com.ilya.service.modelmapper.ReadPaymentProcessorMapper;
import com.ilya.service.service.PaymentProcessorServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.learn.dropwizard.model.ReadPaymentProcessorDTO;
import com.learn.dropwizard.model.CreateUpdatePaymentProcessorDTO;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonServiceImplTest {
    @Mock
    private PaymentProcessorDAO paymentProcessorDAO;

    @InjectMocks
    private PaymentProcessorServiceImpl paymentProcessorService;

    @Mock
    private CreatePaymentProcessorMapper createPaymentProcessorMapper;

    @Mock
    private ReadPaymentProcessorMapper readPaymentProcessorMapper;

    @Test
    public void getTest() {
        PaymentProcessor paymentProcessor = mock(PaymentProcessor.class);
        ReadPaymentProcessorDTO mockDto = mock(ReadPaymentProcessorDTO.class);

        when(paymentProcessorDAO.getPaymentProcessor("123e4567-e89b-12d3-a456-426614174000")).thenReturn(paymentProcessor);
        when(readPaymentProcessorMapper.convertToDto(paymentProcessor)).thenReturn(mockDto);

        ReadPaymentProcessorDTO exp = paymentProcessorService.getPaymentProcessor("123e4567-e89b-12d3-a456-426614174000");

        assertThat(exp).isSameAs(mockDto);
    }

    @Test
    public void createTest() {
        CreateUpdatePaymentProcessorDTO createUpdatePaymentProcessorDTO = mock(CreateUpdatePaymentProcessorDTO.class);
        ReadPaymentProcessorDTO readPaymentProcessorDTO = mock(ReadPaymentProcessorDTO.class);
        PaymentProcessor paymentProcessor = mock(PaymentProcessor.class);

        when(readPaymentProcessorMapper.convertToDto(paymentProcessor)).thenReturn(readPaymentProcessorDTO);
        when(createPaymentProcessorMapper.convertToEntity(createUpdatePaymentProcessorDTO)).thenReturn(paymentProcessor);
        when(paymentProcessorDAO.isPaymentProcessorWithKeyExists(null)).thenReturn(false);

        ReadPaymentProcessorDTO expected = paymentProcessorService.createPaymentProcessor(createUpdatePaymentProcessorDTO);

        assertThat(expected).isNotNull();
        assertThat(expected).isSameAs(readPaymentProcessorDTO);
    }

    @Test
    public void getAllTest() {
        PaymentProcessor paymentProcessor = mock(PaymentProcessor.class);
        List<PaymentProcessor> personList = new ArrayList<>();
        personList.add(paymentProcessor);

        ReadPaymentProcessorDTO readPaymentProcessorDTO = mock(ReadPaymentProcessorDTO.class);
        List<ReadPaymentProcessorDTO> list = new ArrayList<>();
        list.add(readPaymentProcessorDTO);


        when(paymentProcessorDAO.getAllPaymentProcessors(1L, 3L)).thenReturn(personList);
        when(readPaymentProcessorMapper.convertListToDto(personList)).thenReturn(list);

        List<ReadPaymentProcessorDTO> expected = paymentProcessorService.getAllPaymentProcessors(1L, 3L);
        assertThat(expected).isNotNull();
        assertThat(expected).isSameAs(list);
    }

    @Test
    public void deleteTest() {
        String uuid = String.valueOf(UUID.randomUUID());

        paymentProcessorService.deletePaymentProcessor(uuid);

        verify(paymentProcessorDAO, times(1)).deletePaymentProcessor(uuid);
    }

    @Test
    public void updateTest() {
        CreateUpdatePaymentProcessorDTO createUpdatePaymentProcessorDTO = mock(CreateUpdatePaymentProcessorDTO.class);
        ReadPaymentProcessorDTO readPaymentProcessorDTO = mock(ReadPaymentProcessorDTO.class);
        PaymentProcessor paymentProcessor = mock(PaymentProcessor.class);
        String uuid = String.valueOf(UUID.randomUUID());

        when(createPaymentProcessorMapper.convertToEntity(createUpdatePaymentProcessorDTO)).thenReturn(paymentProcessor);
        when(paymentProcessorDAO.isPaymentProcessorWithKeyExists(null)).thenReturn(false);
        when(readPaymentProcessorMapper.convertToDto(paymentProcessor)).thenReturn(readPaymentProcessorDTO);

        ReadPaymentProcessorDTO expected = paymentProcessorService.updatePaymentProcessor(uuid, createUpdatePaymentProcessorDTO);

        assertThat(expected).isNotNull();
        verify(paymentProcessorDAO, times(1)).updatePaymentProcessor(uuid, paymentProcessor);
    }
}
