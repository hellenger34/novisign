package com.novisign.demo.controller;

import static com.novisign.demo.TestData.TEST_DURATION_10;
import static com.novisign.demo.TestData.TEST_DURATION_5;
import static com.novisign.demo.TestData.TEST_DURATION_7;
import static com.novisign.demo.TestData.TEST_ID_1;
import static com.novisign.demo.TestData.TEST_ID_2;
import static com.novisign.demo.TestData.TEST_ID_3;
import static com.novisign.demo.TestData.TEST_URL_1;
import static com.novisign.demo.TestData.TEST_URL_2;
import static com.novisign.demo.TestData.TEST_URL_3;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.novisign.demo.model.dto.Image;
import com.novisign.demo.service.ImageService;

/**
 * Dev notes: there is only 1 class covered because of lack of time, but tests for others classes will look the same as this one (of course except specific cases)
 */
@ExtendWith(MockitoExtension.class)
public class ImageControllerTest {

    @Mock
    private ImageService mockImageService;
    @InjectMocks
    private ImageController unit;

    @Test
    public void addImageTest() {
        //GIVEN
        final Image image = Image.builder()
            .url(TEST_URL_1)
            .durationSeconds(TEST_DURATION_5)
            .build();

        final Image createdImage = Image.builder()
            .url(TEST_URL_1)
            .durationSeconds(TEST_DURATION_5)
            .build();

        when(mockImageService.addImage(image)).thenReturn(createdImage);

        //WHEN
        final ResponseEntity<Image> actualResult = unit.addImage(image);

        //THEN
        assertEquals(actualResult.getBody(), createdImage);
        assertEquals(actualResult.getStatusCode(), HttpStatus.CREATED);

        verify(mockImageService).addImage(image);
        verifyNoMoreInteractions(mockImageService);
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    public void deleteImageByIdTest(boolean isDeleted) {
        //GIVEN
        when(mockImageService.deleteImage(TEST_ID_1)).thenReturn(isDeleted);

        //WHEN
        ResponseEntity<Void> actualResponse = unit.deleteImageById(TEST_ID_1);

        //THEN
        if (isDeleted){
            assertEquals(actualResponse.getStatusCode(), HttpStatus.OK);
        } else {
            assertEquals(actualResponse.getStatusCode(), HttpStatus.NO_CONTENT);
        }

        verify(mockImageService).deleteImage(TEST_ID_1);
    }

    @Test
    public void searchImagesTest() {
        //GIVEN
        final Image image1 = Image.builder()
            .id(TEST_ID_1)
            .url(TEST_URL_1)
            .durationSeconds(TEST_DURATION_5)
            .build();
        final Image image2 = Image.builder()
            .id(TEST_ID_2)
            .url(TEST_URL_2)
            .durationSeconds(TEST_DURATION_7)
            .build();
        final Image image3 = Image.builder()
            .id(TEST_ID_3)
            .url(TEST_URL_3)
            .durationSeconds(TEST_DURATION_10)
            .build();
        final List<Image> images = List.of(image1, image2, image3);

        when(mockImageService.searchImages(anyString(), anyInt())).thenReturn(images);

        //WHEN
        ResponseEntity<List<Image>> listResponseEntity = unit.searchImages(anyString(), anyInt());

        //THEN
        assertEquals(listResponseEntity.getBody(), images);
        assertEquals(listResponseEntity.getStatusCode(), HttpStatus.OK);

        verify(mockImageService).searchImages(anyString(), anyInt());
        verifyNoMoreInteractions(mockImageService);
    }

}
