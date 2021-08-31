package com.ilya.service.service;

import com.ilya.db.dao.PaymentProcessorDAOImpl;
import com.ilya.db.domain.PaymentProcessor;
import com.ilya.service.exception.AlreadyExistException;
import com.ilya.service.modelmapper.CreatePaymentProcessorMapper;
import com.ilya.service.modelmapper.ReadPaymentProcessorMapper;
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

import javax.ws.rs.NotFoundException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentProcessorServiceImplTest {

    @Mock
    private PaymentProcessorDAOImpl paymentProcessorDAO;

    @InjectMocks
    private PaymentProcessorServiceImpl paymentProcessorService;

    @Mock
    private CreatePaymentProcessorMapper createPaymentProcessorMapper;

    @Mock
    private ReadPaymentProcessorMapper readPaymentProcessorMapper;

    @Test
    public void getTest() {
        PaymentProcessor paymentProcessor = mock(PaymentProcessor.class);
        ReadPaymentProcessorDTO readPaymentProcessorDTO = new ReadPaymentProcessorDTO();

        when(paymentProcessorDAO.getPaymentProcessor("123e4567-e89b-12d3-a456-426614174000")).thenReturn(paymentProcessor);
        when(readPaymentProcessorMapper.convertToDto(paymentProcessor)).thenReturn(readPaymentProcessorDTO);

        ReadPaymentProcessorDTO exp = paymentProcessorService.getPaymentProcessor("123e4567-e89b-12d3-a456-426614174000");

        assertThat(exp).isSameAs(readPaymentProcessorDTO);
    }

    @Test
    public void createTest() {
        CreateUpdatePaymentProcessorDTO createUpdatePaymentProcessorDTO = new CreateUpdatePaymentProcessorDTO();
        ReadPaymentProcessorDTO readPaymentProcessorDTO = new ReadPaymentProcessorDTO();
        PaymentProcessor paymentProcessor = new PaymentProcessor();

        when(readPaymentProcessorMapper.convertToDto(paymentProcessor)).thenReturn(readPaymentProcessorDTO);
        when(createPaymentProcessorMapper.convertToEntity(createUpdatePaymentProcessorDTO)).thenReturn(paymentProcessor);

        ReadPaymentProcessorDTO expected = paymentProcessorService.createPaymentProcessor(createUpdatePaymentProcessorDTO);

        assertThat(expected).isNotNull();
        assertThat(expected).isSameAs(readPaymentProcessorDTO);
    }

    @Test
    public void getAllTest() {
        PaymentProcessor paymentProcessor = new PaymentProcessor();
        List<PaymentProcessor> personList = new ArrayList<>();
        personList.add(paymentProcessor);

        ReadPaymentProcessorDTO readPaymentProcessorDTO = mock(ReadPaymentProcessorDTO.class);
        List<ReadPaymentProcessorDTO> list = new ArrayList<>();
        list.add(readPaymentProcessorDTO);


        when(paymentProcessorDAO.getAllPaymentProcessors(1L, 3L)).thenReturn(personList);
        when(readPaymentProcessorMapper.convertListToDto(personList)).thenReturn(list);

        List<ReadPaymentProcessorDTO> expected = paymentProcessorService.getAllPaymentProcessors(1L, 3L);
        assertEquals(expected.size(),1);
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
        CreateUpdatePaymentProcessorDTO createUpdatePaymentProcessorDTO = new CreateUpdatePaymentProcessorDTO();
        ReadPaymentProcessorDTO readPaymentProcessorDTO = new ReadPaymentProcessorDTO();
        PaymentProcessor paymentProcessor = new PaymentProcessor();
        String uuid = String.valueOf(UUID.randomUUID());

        when(createPaymentProcessorMapper.convertToEntity(createUpdatePaymentProcessorDTO)).thenReturn(paymentProcessor);
        when(readPaymentProcessorMapper.convertToDto(paymentProcessor)).thenReturn(readPaymentProcessorDTO);

        ReadPaymentProcessorDTO expected = paymentProcessorService.updatePaymentProcessor(uuid, createUpdatePaymentProcessorDTO);

        assertThat(expected).isNotNull();
        verify(paymentProcessorDAO, times(1)).updatePaymentProcessor(uuid, paymentProcessor);
    }

    @Test
    public void createExistingKey(){
        CreateUpdatePaymentProcessorDTO createUpdatePaymentProcessorDTO = new CreateUpdatePaymentProcessorDTO();
        PaymentProcessor paymentProcessor = new PaymentProcessor();

        when(createPaymentProcessorMapper.convertToEntity(createUpdatePaymentProcessorDTO)).thenReturn(paymentProcessor);
        when(paymentProcessorDAO.isPaymentProcessorWithKeyExists(null)).thenReturn(true);

        assertThrows(AlreadyExistException.class, () -> paymentProcessorService.createPaymentProcessor(createUpdatePaymentProcessorDTO));
    }

    @Test
    public void updateExistingKey(){
        CreateUpdatePaymentProcessorDTO createUpdatePaymentProcessorDTO = new CreateUpdatePaymentProcessorDTO();
        String uuid = String.valueOf(UUID.randomUUID());

        when(paymentProcessorDAO.isPaymentProcessorWithKeyExists(null)).thenReturn(true);

        assertThrows(AlreadyExistException.class, () -> paymentProcessorService.updatePaymentProcessor(uuid,createUpdatePaymentProcessorDTO));
    }

    @Test
    public void getNonExisting(){
        when(paymentProcessorDAO.isPaymentProcessorNonExists(null)).thenReturn(true);

        assertThrows(NotFoundException.class, () -> paymentProcessorService.getPaymentProcessor(null));
    }

    @Test
    public void updateNonExisting(){
        when(paymentProcessorDAO.isPaymentProcessorNonExists(null)).thenReturn(true);

        assertThrows(NotFoundException.class, () -> paymentProcessorService.updatePaymentProcessor(null,null));
    }

    @Test
    public void deleteNonExisting(){
        when(paymentProcessorDAO.isPaymentProcessorNonExists(null)).thenReturn(true);

        assertThrows(NotFoundException.class, () -> paymentProcessorService.deletePaymentProcessor(null));
    }

    @Test
    public void isExists(){
        when(paymentProcessorDAO.isPaymentProcessorNonExists(null)).thenReturn(true);

        assertThrows(NotFoundException.class, () -> paymentProcessorService.isPaymentProcessorExists(null));
    }
}
