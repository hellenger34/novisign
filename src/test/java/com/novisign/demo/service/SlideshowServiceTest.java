package com.novisign.demo.service;

import static com.novisign.demo.TestData.TEST_DURATION_10;
import static com.novisign.demo.TestData.TEST_DURATION_5;
import static com.novisign.demo.TestData.TEST_ID_1;
import static com.novisign.demo.TestData.TEST_ID_2;
import static com.novisign.demo.TestData.TEST_ID_3;
import static com.novisign.demo.TestData.TEST_URL_1;
import static com.novisign.demo.TestData.TEST_URL_2;
import static com.novisign.demo.exception.ExceptionDetail.SLIDESHOW_NOT_FOUND;
import static com.novisign.demo.exception.ExceptionDetail.THERE_ID_NOT_IMAGES_RELATED_TO_SLIDESHOW;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.novisign.demo.event.producer.SlideshowKafkaEventProducer;
import com.novisign.demo.exception.ValidationException;
import com.novisign.demo.model.dto.Image;
import com.novisign.demo.model.dto.Slideshow;
import com.novisign.demo.repository.SlideshowRepository;
import com.novisign.demo.service.validator.ImageValidator;

@ExtendWith(MockitoExtension.class)
public class SlideshowServiceTest {

    @Mock
    private SlideshowRepository mockSlideshowRepository;

    @Mock
    private SlideshowKafkaEventProducer mockSlideshowKafkaEventProducer;

    @Mock
    private ImageValidator mockImageValidator;

    @InjectMocks
    private SlideshowService unit;

    @Test
    public void createSlideshowTest() {
        //GIVEN
        Image image1 = Image.builder()
            .id(TEST_ID_1)
            .url(TEST_URL_1)
            .durationSeconds(TEST_DURATION_5)
            .build();
        Image image2 = Image.builder()
            .id(TEST_ID_2)
            .url(TEST_URL_2)
            .durationSeconds(TEST_DURATION_10)
            .build();
        List<Image> images = List.of(image1, image2);

        Slideshow createdSlideshow = Slideshow.builder()
            .images(images)
            .build();

        when(mockSlideshowRepository.createSlideshow(images)).thenReturn(createdSlideshow);

        //WHEN
        Slideshow actualResult = unit.createSlideshow(images);

        //THEN
        assertEquals(createdSlideshow, actualResult);
        verify(mockSlideshowRepository).createSlideshow(images);
        verify(mockSlideshowKafkaEventProducer).sendSlideshowCreatedEvent(createdSlideshow);
        verifyNoMoreInteractions(mockSlideshowRepository, mockSlideshowKafkaEventProducer);
    }

    @Test
    public void createSlideshowTest_InvalidUrl() {
        //GIVEN
        Image invalidImage = Image.builder()
            .id(TEST_ID_1)
            .url("qwe")
            .durationSeconds(TEST_DURATION_5)
            .build();
        List<Image> images = List.of(invalidImage);

        //WHEN & THEN
        assertThrows(ValidationException.class, () -> unit.createSlideshow(images));
        verifyNoInteractions(mockSlideshowRepository, mockSlideshowKafkaEventProducer);
    }

    @Test
    public void deleteSlideshowTest_Success() {
        //GIVEN
        when(mockSlideshowRepository.isExistsById(TEST_ID_3)).thenReturn(true);

        //WHEN
        boolean actualResult = unit.deleteSlideshow(TEST_ID_3);

        //THEN
        assertTrue(actualResult);
        verify(mockSlideshowRepository).isExistsById(TEST_ID_3);
        verify(mockSlideshowRepository).deleteSlideshow(TEST_ID_3);
        verify(mockSlideshowKafkaEventProducer).sendSlideshowDeletedEvent(TEST_ID_3);
        verifyNoMoreInteractions(mockSlideshowRepository, mockSlideshowKafkaEventProducer);
    }

    @Test
    public void deleteSlideshowTest_Failure() {
        //GIVEN
        when(mockSlideshowRepository.isExistsById(TEST_ID_3)).thenReturn(false);

        //WHEN
        boolean actualResult = unit.deleteSlideshow(TEST_ID_3);

        //THEN
        assertFalse(actualResult);
        verify(mockSlideshowRepository).isExistsById(TEST_ID_3);
        verifyNoMoreInteractions(mockSlideshowRepository, mockSlideshowKafkaEventProducer);
    }

    @Test
    public void getSlideshowOrderTest() {
        //GIVEN
        Image image1 = Image.builder()
            .id(TEST_ID_1)
            .url(TEST_URL_1)
            .durationSeconds(TEST_DURATION_5)
            .build();
        Image image2 = Image.builder()
            .id(TEST_ID_2)
            .url(TEST_URL_2)
            .durationSeconds(TEST_DURATION_10)
            .build();
        List<Image> images = new ArrayList<>(List.of(image1, image2));
        Slideshow slideshow = Slideshow.builder()
            .id(TEST_ID_3)
            .images(images)
            .build();

        when(mockSlideshowRepository.getSlideshowById(TEST_ID_3)).thenReturn(Optional.of(slideshow));

        //WHEN
        List<Image> actualResult = unit.getSlideshowOrder(TEST_ID_3);

        //THEN
        assertEquals(images, actualResult);
        verify(mockSlideshowRepository).getSlideshowById(TEST_ID_3);
        verifyNoMoreInteractions(mockSlideshowRepository);
    }

    @Test
    public void getSlideshowOrderTest_SlideshowNotFound() {
        //GIVEN
        when(mockSlideshowRepository.getSlideshowById(TEST_ID_3)).thenReturn(Optional.empty());

        //WHEN
        assertThrows(
            ValidationException.class,
            () -> unit.getSlideshowOrder(TEST_ID_3),
            SLIDESHOW_NOT_FOUND.getFormattedMessage(TEST_ID_3)
        );

        //THEN
        verify(mockSlideshowRepository).getSlideshowById(TEST_ID_3);
        verifyNoMoreInteractions(mockSlideshowRepository);
    }

    @Test
    public void getSlideshowOrderTest_EmptyImages() {
        //GIVEN
        Slideshow slideshow = Slideshow.builder()
            .id(TEST_ID_3)
            .build();

        when(mockSlideshowRepository.getSlideshowById(TEST_ID_3)).thenReturn(Optional.of(slideshow));

        //WHEN
        assertThrows(
            ValidationException.class,
            () -> unit.getSlideshowOrder(TEST_ID_3),
            THERE_ID_NOT_IMAGES_RELATED_TO_SLIDESHOW.getFormattedMessage(TEST_ID_3)
        );

        //THEN
        verify(mockSlideshowRepository).getSlideshowById(TEST_ID_3);
        verifyNoMoreInteractions(mockSlideshowRepository);
    }

    @Test
    public void getSlideshowOrderTest_SortImages() {
        //GIVEN
        Image image1 = Image.builder()
            .id(TEST_ID_1)
            .url(TEST_URL_1)
            .durationSeconds(TEST_DURATION_10)
            .build();
        Image image2 = Image.builder()
            .id(TEST_ID_2)
            .url(TEST_URL_2)
            .durationSeconds(TEST_DURATION_5)
            .build();
        List<Image> images = new ArrayList<>(List.of(image1, image2));
        Slideshow slideshow = Slideshow.builder()
            .id(TEST_ID_3)
            .images(images)
            .build();

        List<Image> sortedImages = new ArrayList<>(images);
        sortedImages.sort(Comparator.comparingInt(Image::getDurationSeconds));

        when(mockSlideshowRepository.getSlideshowById(TEST_ID_3)).thenReturn(Optional.of(slideshow));

        //WHEN
        List<Image> actualResult = unit.getSlideshowOrder(TEST_ID_3);

        //THEN
        assertEquals(sortedImages, actualResult);
        verify(mockSlideshowRepository).getSlideshowById(TEST_ID_3);
        verifyNoMoreInteractions(mockSlideshowRepository);
    }
}
