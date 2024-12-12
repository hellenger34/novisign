package com.novisign.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.novisign.demo.model.dto.Image;
import com.novisign.demo.model.dto.Slideshow;
import com.novisign.demo.model.entity.ImageDb;
import com.novisign.demo.model.entity.SlideshowDb;
import com.novisign.demo.model.mapper.ImageMapper;
import com.novisign.demo.model.mapper.SlideshowMapper;
import com.novisign.demo.repository.jpa.JpaSlideshowRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SlideshowRepository {

    private final JpaSlideshowRepository jpaSlideshowRepository;
    private final SlideshowMapper slideshowMapper;
    private final ImageMapper imageMapper;

    public Slideshow createSlideshow(final List<Image> images) {
        List<ImageDb> imagesRelatedToSlideshow = images.stream()
            .map(imageMapper::toImageDb)
            .toList();

        final SlideshowDb slideshowDb = SlideshowDb.builder()
            .images(imagesRelatedToSlideshow)
            .build();

        return slideshowMapper.toSlideshow(jpaSlideshowRepository.save(slideshowDb));
    }

    public boolean isExistsById(final Long id) {
        return jpaSlideshowRepository.existsById(id);
    }

    public void deleteSlideshow(final Long id) {
        jpaSlideshowRepository.deleteById(id);
    }

    public Optional<Slideshow> getSlideshowById(final Long id) {
        return jpaSlideshowRepository.findById(id).map(slideshowMapper::toSlideshow);
    }
}
