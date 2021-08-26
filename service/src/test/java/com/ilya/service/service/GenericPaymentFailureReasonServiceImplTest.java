package com.ilya.service.service;

import com.ilya.db.dao.GenericFailureReasonDAO;
import com.ilya.db.domain.GenericPaymentFailureReason;
import com.ilya.db.domain.PaymentProcessor;
import com.ilya.service.exception.AlreadyExistException;
import com.ilya.service.modelmapper.CreateGenericPaymentFailureReasonMapper;
import com.ilya.service.modelmapper.ReadGenericPaymentFailureReasonMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import com.learn.dropwizard.model.CreateUpdateGenericPaymentFailureReasonDTO;
import com.learn.dropwizard.model.ReadGenericPaymentFailureReasonDTO;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GenericPaymentFailureReasonServiceImplTest {

    @Mock
    private GenericFailureReasonDAO genericFailureReasonDAO;

    @Mock
    private CreateGenericPaymentFailureReasonMapper createGenericPaymentFailureReasonMapper;

    @Mock
    private ReadGenericPaymentFailureReasonMapper readGenericPaymentFailureReasonMapper;

    @InjectMocks
    private GenericPaymentFailureReasonServiceImpl genericPaymentFailureReasonService;

    private static final String TEST_UUID = "123e4567-e89b-12d3-a456-426614174000";

    @Test
    void createGenericPaymentFailureReason() {
        CreateUpdateGenericPaymentFailureReasonDTO createUpdateGenericPaymentFailureReasonDTO = mock(CreateUpdateGenericPaymentFailureReasonDTO.class);
        ReadGenericPaymentFailureReasonDTO readGenericPaymentFailureReasonDTO = mock(ReadGenericPaymentFailureReasonDTO.class);
        GenericPaymentFailureReason genericPaymentFailureReason = mock(GenericPaymentFailureReason.class);

        when(createGenericPaymentFailureReasonMapper.convertToEntity(createUpdateGenericPaymentFailureReasonDTO)).thenReturn(genericPaymentFailureReason);
        when(readGenericPaymentFailureReasonMapper.convertToDto(genericPaymentFailureReason)).thenReturn(readGenericPaymentFailureReasonDTO);

        ReadGenericPaymentFailureReasonDTO expected = genericPaymentFailureReasonService.createGenericPaymentFailureReason(createUpdateGenericPaymentFailureReasonDTO);

        assertThat(expected).isNotNull();
        assertThat(expected).isSameAs(readGenericPaymentFailureReasonDTO);
    }

    @Test
    void getAllGenericPaymentFailureReasons() {
        List <GenericPaymentFailureReason> genericPaymentFailureReasons = mock(List.class);
        List<ReadGenericPaymentFailureReasonDTO> genericPaymentFailureReasonDTOS = mock(List.class);

        when(genericFailureReasonDAO.getAllGenericFailureReasons(1L, 3L)).thenReturn(genericPaymentFailureReasons);
        when(readGenericPaymentFailureReasonMapper.convertListToDto(genericPaymentFailureReasons)).thenReturn(genericPaymentFailureReasonDTOS);

        List<ReadGenericPaymentFailureReasonDTO> expected = genericPaymentFailureReasonService.getAllGenericPaymentFailureReasons(1L, 3L);
        assertThat(expected).isNotNull();
        assertThat(expected).isSameAs(genericPaymentFailureReasonDTOS);
    }

    @Test
    void deleteGenericPaymentFailureReason() {
        String uuid = String.valueOf(UUID.randomUUID());

        genericPaymentFailureReasonService.deleteGenericPaymentFailureReason(uuid);

        verify(genericFailureReasonDAO, times(1)).deleteGenericFailureReason(uuid);
    }


    @Test
    void updateGenericPaymentFailureReason() {
        CreateUpdateGenericPaymentFailureReasonDTO createUpdateGenericPaymentFailureReasonDTO = mock(CreateUpdateGenericPaymentFailureReasonDTO.class);
        ReadGenericPaymentFailureReasonDTO readGenericPaymentFailureReasonDTO = mock(ReadGenericPaymentFailureReasonDTO.class);
        GenericPaymentFailureReason genericPaymentFailureReason = mock(GenericPaymentFailureReason.class);
        String uuid = String.valueOf(UUID.randomUUID());

        when(createGenericPaymentFailureReasonMapper.convertToEntity(createUpdateGenericPaymentFailureReasonDTO)).thenReturn(genericPaymentFailureReason);
        when(readGenericPaymentFailureReasonMapper.convertToDto(genericPaymentFailureReason)).thenReturn(readGenericPaymentFailureReasonDTO);

        ReadGenericPaymentFailureReasonDTO expected = genericPaymentFailureReasonService.updateGenericPaymentFailureReason(uuid, createUpdateGenericPaymentFailureReasonDTO);

        assertThat(expected).isNotNull();
        verify(genericFailureReasonDAO, times(1)).updateGenericFailureReason(uuid, genericPaymentFailureReason);
    }

    @Test
    void getGenericPaymentFailureReason() {
        ReadGenericPaymentFailureReasonDTO readGenericPaymentFailureReasonDTO = mock(ReadGenericPaymentFailureReasonDTO.class);
        GenericPaymentFailureReason genericPaymentFailureReason = mock(GenericPaymentFailureReason.class);


        when(genericFailureReasonDAO.getGenericFailureReason(TEST_UUID)).thenReturn(genericPaymentFailureReason);
        when(readGenericPaymentFailureReasonMapper.convertToDto(genericPaymentFailureReason)).thenReturn(readGenericPaymentFailureReasonDTO);

        ReadGenericPaymentFailureReasonDTO expected = genericPaymentFailureReasonService.getGenericPaymentFailureReason(TEST_UUID);

        assertThat(expected).isNotNull();
        assertThat(expected).isSameAs(readGenericPaymentFailureReasonDTO);
    }

    @Test
    void isGenericReasonExists() {
        when(genericFailureReasonDAO.isGenericFailureReasonNonExists(TEST_UUID)).thenReturn(true);

        assertThrows(NotFoundException.class, () -> genericPaymentFailureReasonService.isGenericReasonExists(TEST_UUID));
    }

    @Test
    void getNonExisting(){
        when(genericFailureReasonDAO.isGenericFailureReasonNonExists(TEST_UUID)).thenReturn(true);

        assertThrows(NotFoundException.class, () -> genericPaymentFailureReasonService.getGenericPaymentFailureReason(TEST_UUID));
    }

    @Test
    void deleteNonExisting(){
        when(genericFailureReasonDAO.isGenericFailureReasonNonExists(TEST_UUID)).thenReturn(true);

        assertThrows(NotFoundException.class, () -> genericPaymentFailureReasonService.deleteGenericPaymentFailureReason(TEST_UUID));
    }

    @Test
    void updateNonExisting(){
        when(genericFailureReasonDAO.isGenericFailureReasonNonExists(TEST_UUID)).thenReturn(true);

        assertThrows(NotFoundException.class, () -> genericPaymentFailureReasonService.updateGenericPaymentFailureReason(TEST_UUID,null));
    }

    @Test
    void createExistingCode(){
        CreateUpdateGenericPaymentFailureReasonDTO createUpdateGenericPaymentFailureReasonDTO = mock(CreateUpdateGenericPaymentFailureReasonDTO.class);
        GenericPaymentFailureReason genericPaymentFailureReason = mock(GenericPaymentFailureReason.class);

        when(createGenericPaymentFailureReasonMapper.convertToEntity(createUpdateGenericPaymentFailureReasonDTO)).thenReturn(genericPaymentFailureReason);
        when(genericFailureReasonDAO.isGenericFailureReasonWithCodeExists(null)).thenReturn(true);

        assertThrows(AlreadyExistException.class, () -> genericPaymentFailureReasonService.createGenericPaymentFailureReason(createUpdateGenericPaymentFailureReasonDTO));
    }
}