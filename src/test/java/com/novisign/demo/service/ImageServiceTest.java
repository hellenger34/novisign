package com.novisign.demo.service;

import static com.novisign.demo.TestData.TEST_DURATION_10;
import static com.novisign.demo.TestData.TEST_DURATION_5;
import static com.novisign.demo.TestData.TEST_ID_1;
import static com.novisign.demo.TestData.TEST_ID_2;
import static com.novisign.demo.TestData.TEST_URL_1;
import static com.novisign.demo.TestData.TEST_URL_2;
import static com.novisign.demo.exception.ExceptionDetail.IMAGE_DURATION_IS_INVALID;
import static com.novisign.demo.exception.ExceptionDetail.IMAGE_IS_NULL;
import static com.novisign.demo.exception.ExceptionDetail.IMAGE_URL_IS_EMPTY;
import static com.novisign.demo.exception.ExceptionDetail.IMAGE_URL_IS_INVALID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.novisign.demo.event.producer.ImageKafkaEventProducer;
import com.novisign.demo.exception.ValidationException;
import com.novisign.demo.model.dto.Image;
import com.novisign.demo.repository.ImageRepository;

@ExtendWith(MockitoExtension.class)
public class ImageServiceTest {

    @Mock
    private ImageRepository mockImageRepository;

    @Mock
    private ImageKafkaEventProducer mockImageKafkaEventProducer;

    @InjectMocks
    private ImageService unit;

    @Test
    public void addImageTest() {
        //GIVEN
        Image image = Image.builder()
            .url(TEST_URL_1)
            .durationSeconds(TEST_DURATION_5)
            .build();
        Image createdImage = Image.builder()
            .id(TEST_ID_1)
            .url(TEST_URL_1)
            .durationSeconds(TEST_DURATION_5)
            .build();

        when(mockImageRepository.createImage(image)).thenReturn(createdImage);

        //WHEN
        Image actualResult = unit.addImage(image);

        //THEN
        assertEquals(createdImage, actualResult);
        verify(mockImageRepository).createImage(image);
        verify(mockImageKafkaEventProducer).sendImageCreatedEvent(createdImage);
        verifyNoMoreInteractions(mockImageRepository, mockImageKafkaEventProducer);
    }

    @Test
    public void addImageTest_ImageIsNull() {
        //GIVEN
        //WHEN
        assertThrows(ValidationException.class,
            () -> unit.addImage(null),
            IMAGE_IS_NULL.getMessage());

        //THEN
        verifyNoInteractions(mockImageRepository, mockImageKafkaEventProducer);
    }

    @Test
    public void addImageTest_EmptyUrl() {
        //GIVEN
        Image invalidImage = Image.builder()
            .url("")
            .durationSeconds(TEST_DURATION_5)
            .build();

        //WHEN
        assertThrows(ValidationException.class,
            () -> unit.addImage(invalidImage),
            IMAGE_URL_IS_EMPTY.getMessage());

        //THEN
        verifyNoInteractions(mockImageRepository, mockImageKafkaEventProducer);
    }

    @Test
    public void addImageTest_InvalidDuration() {
        //GIVEN
        Image invalidImage = Image.builder()
            .url(TEST_URL_1)
            .durationSeconds(0)
            .build();

        //WHEN
        assertThrows(ValidationException.class,
            () -> unit.addImage(invalidImage),
            IMAGE_DURATION_IS_INVALID.getMessage());

        //THEN
        verifyNoInteractions(mockImageRepository, mockImageKafkaEventProducer);
    }

    @Test
    public void addImageTest_InvalidUrl() {
        //GIVEN
        Image invalidImage = Image.builder()
            .url("qwe")
            .durationSeconds(TEST_DURATION_5)
            .build();

        //WHEN
        assertThrows(ValidationException.class,
            () -> unit.addImage(invalidImage),
            IMAGE_URL_IS_INVALID.getMessage());

        //THEN
        verifyNoInteractions(mockImageRepository, mockImageKafkaEventProducer);
    }

    @Test
    public void deleteImageTest_Success() {
        //GIVEN
        Long imageId = 1L;
        when(mockImageRepository.isExists(imageId)).thenReturn(true);

        //WHEN
        boolean actualResult = unit.deleteImage(imageId);

        //THEN
        assertTrue(actualResult);
        verify(mockImageRepository).isExists(imageId);
        verify(mockImageRepository).deleteImage(imageId);
        verify(mockImageKafkaEventProducer).sendImageDeletedEvent(imageId);
        verifyNoMoreInteractions(mockImageRepository, mockImageKafkaEventProducer);
    }

    @Test
    public void deleteImageTest_Failure() {
        //GIVEN
        when(mockImageRepository.isExists(TEST_ID_1)).thenReturn(false);

        //WHEN
        boolean actualResult = unit.deleteImage(TEST_ID_1);

        //THEN
        assertFalse(actualResult);
        verify(mockImageRepository).isExists(TEST_ID_1);
        verifyNoMoreInteractions(mockImageRepository, mockImageKafkaEventProducer);
    }

    @Test
    public void searchImagesTest() {
        //GIVEN
        String keyword = "url";
        Image image1 = Image.builder()
            .id(TEST_ID_1)
            .url(TEST_URL_1)
            .durationSeconds(TEST_DURATION_10)
            .build();
        Image image2 = Image.builder()
            .id(TEST_ID_2)
            .url(TEST_URL_2)
            .durationSeconds(TEST_DURATION_10)
            .build();
        List<Image> expectedImages = List.of(image1, image2);

        when(mockImageRepository.searchImages(keyword, TEST_DURATION_10)).thenReturn(expectedImages);

        //WHEN
        List<Image> actualResult = unit.searchImages(keyword, TEST_DURATION_10);

        //THEN
        assertEquals(expectedImages, actualResult);
        verify(mockImageRepository).searchImages(keyword, TEST_DURATION_10);
        verifyNoMoreInteractions(mockImageRepository);
    }
}
