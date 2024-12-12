package com.novisign.demo.controller;

import static com.novisign.demo.TestData.TEST_DURATION_5;
import static com.novisign.demo.TestData.TEST_DURATION_7;
import static com.novisign.demo.TestData.TEST_ID_1;
import static com.novisign.demo.TestData.TEST_ID_2;
import static com.novisign.demo.TestData.TEST_URL_1;
import static com.novisign.demo.TestData.TEST_URL_2;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.novisign.demo.model.dto.Image;
import com.novisign.demo.model.dto.Slideshow;
import com.novisign.demo.service.SlideshowService;

@ExtendWith(MockitoExtension.class)
public class SlideshowControllerTest {

    @Mock
    private SlideshowService mockSlideshowService;

    @InjectMocks
    private SlideshowController unit;

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
            .durationSeconds(TEST_DURATION_7)
            .build();
        List<Image> images = List.of(image1, image2);

        Slideshow createdSlideshow = Slideshow.builder()
            .images(List.of(image1, image2))
            .build();

        when(mockSlideshowService.createSlideshow(images)).thenReturn(createdSlideshow);

        //WHEN
        ResponseEntity<Slideshow> actualResult = unit.createSlideshow(images);

        //THEN
        assertEquals(HttpStatus.CREATED, actualResult.getStatusCode());
        assertEquals(createdSlideshow, actualResult.getBody());

        verify(mockSlideshowService).createSlideshow(images);
        verifyNoMoreInteractions(mockSlideshowService);
    }

    @Test
    public void deleteSlideshowTest_Success() {
        //GIVEN
        when(mockSlideshowService.deleteSlideshow(TEST_ID_1)).thenReturn(true);

        //WHEN
        ResponseEntity<Void> actualResult = unit.deleteSlideshow(TEST_ID_1);

        //THEN
        assertEquals(HttpStatus.OK, actualResult.getStatusCode());

        verify(mockSlideshowService).deleteSlideshow(TEST_ID_1);
        verifyNoMoreInteractions(mockSlideshowService);
    }

    @Test
    public void deleteSlideshowTest_NotFound() {
        //GIVEN
        Long slideshowId = 2L;
        when(mockSlideshowService.deleteSlideshow(slideshowId)).thenReturn(false);

        //WHEN
        ResponseEntity<Void> actualResult = unit.deleteSlideshow(slideshowId);

        //THEN
        assertEquals(HttpStatus.NO_CONTENT, actualResult.getStatusCode());

        verify(mockSlideshowService).deleteSlideshow(slideshowId);
        verifyNoMoreInteractions(mockSlideshowService);
    }

    @Test
    public void slideshowOrderTest() {
        //GIVEN
        Long slideshowId = 1L;
        Image image1 = Image.builder()
            .id(TEST_ID_1)
            .url(TEST_URL_1)
            .durationSeconds(TEST_DURATION_5)
            .build();
        Image image2 = Image.builder()
            .id(TEST_ID_2)
            .url(TEST_URL_2)
            .durationSeconds(TEST_DURATION_7)
            .build();
        List<Image> sortedImages = List.of(image1, image2);

        when(mockSlideshowService.getSlideshowOrder(slideshowId)).thenReturn(sortedImages);

        //WHEN
        ResponseEntity<List<Image>> actualResult = unit.slideshowOrder(slideshowId);

        //THEN
        assertEquals(HttpStatus.OK, actualResult.getStatusCode());
        assertEquals(sortedImages, actualResult.getBody());

        verify(mockSlideshowService).getSlideshowOrder(slideshowId);
        verifyNoMoreInteractions(mockSlideshowService);
    }

}
