package com.novisign.demo.repository;

import static com.novisign.demo.TestData.TEST_DURATION_10;
import static com.novisign.demo.TestData.TEST_DURATION_5;
import static com.novisign.demo.TestData.TEST_ID_1;
import static com.novisign.demo.TestData.TEST_ID_2;
import static com.novisign.demo.TestData.TEST_URL_1;
import static com.novisign.demo.TestData.TEST_URL_2;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.novisign.demo.model.dto.Image;
import com.novisign.demo.model.entity.ImageDb;
import com.novisign.demo.model.mapper.ImageMapper;
import com.novisign.demo.repository.jpa.JpaImageRepository;

@ExtendWith(MockitoExtension.class)
public class ImageRepositoryTest {

    @Mock
    private JpaImageRepository mockJpaImageRepository;

    @Mock
    private ImageMapper mockImageMapper;

    @InjectMocks
    private ImageRepository unit;

    @Test
    public void createImageTest_ExistingImage() {
        //GIVEN
        Image image = Image.builder()
            .id(TEST_ID_2)
            .url(TEST_URL_2)
            .durationSeconds(TEST_DURATION_5)
            .build();
        ImageDb imageDb = ImageDb.builder()
            .id(TEST_ID_2)
            .url(TEST_URL_2)
            .durationSeconds(TEST_DURATION_5)
            .build();

        when(mockJpaImageRepository.findByUrl(image.getUrl())).thenReturn(Optional.of(imageDb));
        when(mockImageMapper.toImage(imageDb)).thenReturn(image);

        //WHEN
        Image actualResult = unit.createImage(image);

        //THEN
        assertEquals(image, actualResult);
        verify(mockJpaImageRepository).findByUrl(image.getUrl());
        verifyNoMoreInteractions(mockJpaImageRepository, mockImageMapper);
    }

    @Test
    public void createImageTest_NewImage() {
        //GIVEN
        Image image = Image.builder()
            .url(TEST_URL_2)
            .durationSeconds(TEST_DURATION_5)
            .build();
        ImageDb imageDb = ImageDb.builder()
            .url(TEST_URL_2)
            .durationSeconds(TEST_DURATION_5)
            .build();
        ImageDb savedImageDb = ImageDb.builder()
            .id(TEST_ID_2)
            .url(TEST_URL_2)
            .durationSeconds(TEST_DURATION_5)
            .build();

        when(mockJpaImageRepository.findByUrl(image.getUrl())).thenReturn(Optional.empty());
        when(mockImageMapper.toImageDb(image)).thenReturn(imageDb);
        when(mockJpaImageRepository.save(imageDb)).thenReturn(savedImageDb);
        when(mockImageMapper.toImage(savedImageDb)).thenReturn(image);

        //WHEN
        Image actualResult = unit.createImage(image);

        //THEN
        assertEquals(image, actualResult);
        verify(mockJpaImageRepository).findByUrl(image.getUrl());
        verify(mockJpaImageRepository).save(imageDb);
        verifyNoMoreInteractions(mockJpaImageRepository, mockImageMapper);
    }

    @Test
    public void isExistsTest() {
        //GIVEN
        Long imageId = 1L;
        when(mockJpaImageRepository.existsById(imageId)).thenReturn(true);

        //WHEN
        boolean actualResult = unit.isExists(imageId);

        //THEN
        assertTrue(actualResult);
        verify(mockJpaImageRepository).existsById(imageId);
        verifyNoMoreInteractions(mockJpaImageRepository);
    }

    @Test
    public void deleteImageTest() {
        //GIVEN
        Long imageId = 1L;

        //WHEN
        unit.deleteImage(imageId);

        //THEN
        verify(mockJpaImageRepository).deleteById(imageId);
        verifyNoMoreInteractions(mockJpaImageRepository);
    }

    @Test
    public void searchImagesTest() {
        //GIVEN
        String keyword = "url";
        ImageDb imageDb1 = ImageDb.builder()
            .id(TEST_ID_1)
            .url(TEST_URL_1)
            .durationSeconds(TEST_DURATION_10)
            .build();
        ImageDb imageDb2 = ImageDb.builder()
            .id(TEST_ID_2)
            .url(TEST_URL_2)
            .durationSeconds(TEST_DURATION_10)
            .build();
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
        List<ImageDb> imageDbList = List.of(imageDb1, imageDb2);
        List<Image> expectedImages = List.of(image1, image2);

        when(mockJpaImageRepository.findByUrlContainingOrDurationSeconds(keyword, TEST_DURATION_10))
            .thenReturn(imageDbList);
        when(mockImageMapper.toImage(imageDb1)).thenReturn(image1);
        when(mockImageMapper.toImage(imageDb2)).thenReturn(image2);

        //WHEN
        List<Image> actualResult = unit.searchImages(keyword, TEST_DURATION_10);

        //THEN
        assertEquals(expectedImages, actualResult);
        verify(mockJpaImageRepository).findByUrlContainingOrDurationSeconds(keyword, TEST_DURATION_10);
        verify(mockImageMapper, times(2)).toImage(any(ImageDb.class));
        verifyNoMoreInteractions(mockJpaImageRepository, mockImageMapper);
    }

    @Test
    public void getImageByUrlTest_Found() {
        //GIVEN
        String url = "url1";
        ImageDb imageDb = ImageDb.builder()
            .id(TEST_ID_1)
            .url(TEST_URL_1)
            .durationSeconds(TEST_DURATION_10)
            .build();
        Image image = Image.builder()
            .id(TEST_ID_1)
            .url(TEST_URL_1)
            .durationSeconds(TEST_DURATION_10)
            .build();

        when(mockJpaImageRepository.findByUrl(url)).thenReturn(Optional.of(imageDb));
        when(mockImageMapper.toImage(imageDb)).thenReturn(image);

        //WHEN
        Optional<Image> actualResult = unit.getImageByUrl(url);

        //THEN
        assertTrue(actualResult.isPresent());
        assertEquals(image, actualResult.get());
        verify(mockJpaImageRepository).findByUrl(url);
        verify(mockImageMapper).toImage(imageDb);
        verifyNoMoreInteractions(mockJpaImageRepository, mockImageMapper);
    }

    @Test
    public void getImageByUrlTest_NotFound() {
        //GIVEN
        String url = "url1";

        when(mockJpaImageRepository.findByUrl(url)).thenReturn(Optional.empty());

        //WHEN
        Optional<Image> actualResult = unit.getImageByUrl(url);

        //THEN
        assertFalse(actualResult.isPresent());
        verify(mockJpaImageRepository).findByUrl(url);
        verifyNoMoreInteractions(mockJpaImageRepository, mockImageMapper);
    }
}
