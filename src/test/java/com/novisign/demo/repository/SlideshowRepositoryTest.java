package com.novisign.demo.repository;

import static com.novisign.demo.TestData.TEST_DURATION_10;
import static com.novisign.demo.TestData.TEST_DURATION_5;
import static com.novisign.demo.TestData.TEST_ID_1;
import static com.novisign.demo.TestData.TEST_ID_2;
import static com.novisign.demo.TestData.TEST_ID_3;
import static com.novisign.demo.TestData.TEST_URL_1;
import static com.novisign.demo.TestData.TEST_URL_2;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import com.novisign.demo.model.dto.Slideshow;
import com.novisign.demo.model.entity.ImageDb;
import com.novisign.demo.model.entity.SlideshowDb;
import com.novisign.demo.model.mapper.ImageMapper;
import com.novisign.demo.model.mapper.SlideshowMapper;
import com.novisign.demo.repository.jpa.JpaSlideshowRepository;

@ExtendWith(MockitoExtension.class)
public class SlideshowRepositoryTest {

    @Mock
    private JpaSlideshowRepository mockJpaSlideshowRepository;

    @Mock
    private SlideshowMapper mockSlideshowMapper;

    @Mock
    private ImageMapper mockImageMapper;

    @InjectMocks
    private SlideshowRepository unit;

    @Test
    public void createSlideshowTest() {
        //GIVEN
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
        List<Image> images = List.of(image1, image2);

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
        SlideshowDb slideshowDb = SlideshowDb.builder()
            .images(List.of(imageDb1, imageDb2))
            .build();
        SlideshowDb savedSlideshowDb = SlideshowDb.builder()
            .id(TEST_ID_3)
            .images(List.of(imageDb1, imageDb2))
            .build();
        Slideshow expectedSlideshow = Slideshow.builder()
            .id(TEST_ID_3)
            .images(List.of(image1, image2))
            .build();

        when(mockImageMapper.toImageDb(image1)).thenReturn(imageDb1);
        when(mockImageMapper.toImageDb(image2)).thenReturn(imageDb2);
        when(mockSlideshowMapper.toSlideshow(savedSlideshowDb)).thenReturn(expectedSlideshow);
        when(mockJpaSlideshowRepository.save(slideshowDb)).thenReturn(savedSlideshowDb);

        //WHEN
        Slideshow actualResult = unit.createSlideshow(images);

        //THEN
        assertEquals(expectedSlideshow, actualResult);
        verify(mockImageMapper).toImageDb(image1);
        verify(mockImageMapper).toImageDb(image2);
        verify(mockJpaSlideshowRepository).save(slideshowDb);
        verify(mockSlideshowMapper).toSlideshow(savedSlideshowDb);
        verifyNoMoreInteractions(mockImageMapper, mockJpaSlideshowRepository, mockSlideshowMapper);
    }

    @Test
    public void isExistsByIdTest() {
        //GIVEN
        when(mockJpaSlideshowRepository.existsById(TEST_ID_1)).thenReturn(true);

        //WHEN
        boolean actualResult = unit.isExistsById(TEST_ID_1);

        //THEN
        assertTrue(actualResult);
        verify(mockJpaSlideshowRepository).existsById(TEST_ID_1);
        verifyNoMoreInteractions(mockJpaSlideshowRepository);
    }

    @Test
    public void deleteSlideshowTest() {
        //GIVEN

        //WHEN
        unit.deleteSlideshow(TEST_ID_1);

        //THEN
        verify(mockJpaSlideshowRepository).deleteById(TEST_ID_1);
        verifyNoMoreInteractions(mockJpaSlideshowRepository);
    }

    @Test
    public void getSlideshowByIdTest() {
        //GIVEN
        Image image = Image.builder()
            .id(TEST_ID_1)
            .url(TEST_URL_1)
            .durationSeconds(TEST_DURATION_5)
            .build();
        ImageDb imageDb = ImageDb.builder()
            .id(TEST_ID_1)
            .url(TEST_URL_1)
            .durationSeconds(TEST_DURATION_5)
            .build();
        SlideshowDb slideshowDb = SlideshowDb.builder()
            .id(TEST_ID_2)
            .images(List.of(imageDb))
            .build();
        Slideshow expectedSlideshow = Slideshow.builder()
            .id(TEST_ID_2)
            .images(List.of(image))
            .build();

        when(mockJpaSlideshowRepository.findById(TEST_ID_2)).thenReturn(Optional.of(slideshowDb));
        when(mockSlideshowMapper.toSlideshow(slideshowDb)).thenReturn(expectedSlideshow);

        //WHEN
        Optional<Slideshow> actualResult = unit.getSlideshowById(TEST_ID_2);

        //THEN
        assertTrue(actualResult.isPresent());
        assertEquals(expectedSlideshow, actualResult.get());
        verify(mockJpaSlideshowRepository).findById(TEST_ID_2);
        verify(mockSlideshowMapper).toSlideshow(slideshowDb);
        verifyNoMoreInteractions(mockJpaSlideshowRepository, mockSlideshowMapper);
    }

    @Test
    public void getSlideshowByIdTest_NotFound() {
        //GIVEN
        Long slideshowId = 1L;

        when(mockJpaSlideshowRepository.findById(slideshowId)).thenReturn(Optional.empty());

        //WHEN
        Optional<Slideshow> actualResult = unit.getSlideshowById(slideshowId);

        //THEN
        assertFalse(actualResult.isPresent());
        verify(mockJpaSlideshowRepository).findById(slideshowId);
        verifyNoMoreInteractions(mockJpaSlideshowRepository, mockSlideshowMapper);
    }
}
