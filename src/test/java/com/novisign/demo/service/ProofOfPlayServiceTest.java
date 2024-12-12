package com.novisign.demo.service;

import static com.novisign.demo.TestData.TEST_ID_1;
import static com.novisign.demo.TestData.TEST_ID_2;
import static com.novisign.demo.exception.ExceptionDetail.IMAGE_ID_IS_NULL;
import static com.novisign.demo.exception.ExceptionDetail.IMAGE_NOT_FOUND;
import static com.novisign.demo.exception.ExceptionDetail.SLIDESHOW_ID_IS_NULL;
import static com.novisign.demo.exception.ExceptionDetail.SLIDESHOW_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.novisign.demo.event.producer.ProofOfPlayKafkaEventProducer;
import com.novisign.demo.exception.ValidationException;
import com.novisign.demo.model.dto.Slideshow;
import com.novisign.demo.repository.ImageRepository;
import com.novisign.demo.repository.SlideshowRepository;

@ExtendWith(MockitoExtension.class)
public class ProofOfPlayServiceTest {

    @Mock
    private ImageRepository mockImageRepository;
    @Mock
    private SlideshowRepository mockSlideshowRepository;
    @Mock
    private ProofOfPlayKafkaEventProducer mockProofOfPlayKafkaEventProducer;
    @InjectMocks
    private ProofOfPlayService unit;

    @Test
    public void proofOfPlay() {
        //GIVEN
        when(mockSlideshowRepository.isExistsById(TEST_ID_1)).thenReturn(true);
        when(mockImageRepository.isExists(TEST_ID_2)).thenReturn(true);
        doNothing().when(mockProofOfPlayKafkaEventProducer).sendProofOfPlayEvent(TEST_ID_1, TEST_ID_2);

        //WHEN
        unit.proofOfPlay(TEST_ID_1, TEST_ID_2);

        //THEN
        verify(mockImageRepository).isExists(TEST_ID_2);
        verify(mockSlideshowRepository).isExistsById(TEST_ID_1);
        verify(mockProofOfPlayKafkaEventProducer).sendProofOfPlayEvent(TEST_ID_1, TEST_ID_2);

        verifyNoMoreInteractions(mockImageRepository, mockSlideshowRepository, mockProofOfPlayKafkaEventProducer);
    }

    @Test
    public void proofOfPlay_ImageIdNull() {
        //GIVEN

        //WHEN
        assertThrows(
            ValidationException.class,
            () -> unit.proofOfPlay(TEST_ID_1, null),
            IMAGE_ID_IS_NULL.getMessage()
        );

        //THEN
        verifyNoInteractions(mockSlideshowRepository, mockImageRepository, mockProofOfPlayKafkaEventProducer);
    }

    @Test
    public void proofOfPlay_SlideshowIdNull() {
        //GIVEN

        //WHEN
        assertThrows(
            ValidationException.class,
            () -> unit.proofOfPlay(null, TEST_ID_2),
            SLIDESHOW_ID_IS_NULL.getMessage()
        );

        //THEN
        verifyNoInteractions(mockSlideshowRepository, mockImageRepository, mockProofOfPlayKafkaEventProducer);
    }

    @Test
    public void proofOfPlay_SlideshowNotFound() {
        //GIVEN
        when(mockSlideshowRepository.isExistsById(TEST_ID_1)).thenReturn(false);

        //WHEN
        assertThrows(
            ValidationException.class,
            () -> unit.proofOfPlay(TEST_ID_1, TEST_ID_2),
            SLIDESHOW_NOT_FOUND.getFormattedMessage(TEST_ID_1)
        );

        //THEN
        verify(mockSlideshowRepository).isExistsById(TEST_ID_1);
        verifyNoInteractions(mockImageRepository, mockProofOfPlayKafkaEventProducer);
    }

    @Test
    public void proofOfPlay_ImageNotFound() {
        //GIVEN
        when(mockSlideshowRepository.isExistsById(TEST_ID_1)).thenReturn(true);
        when(mockImageRepository.isExists(TEST_ID_2)).thenReturn(false);

        //WHEN
        assertThrows(
            ValidationException.class,
            () -> unit.proofOfPlay(TEST_ID_1, TEST_ID_2),
            IMAGE_NOT_FOUND.getFormattedMessage(TEST_ID_2)
        );

        //THEN
        verify(mockSlideshowRepository).isExistsById(TEST_ID_1);
        verify(mockImageRepository).isExists(TEST_ID_2);
        verifyNoInteractions(mockProofOfPlayKafkaEventProducer);
    }

}
