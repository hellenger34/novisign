package com.novisign.demo.service;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.novisign.demo.model.dto.Image;
import com.novisign.demo.model.dto.Slideshow;
import com.novisign.demo.repository.SlideshowRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SlideshowService {

    private final SlideshowRepository slideshowRepository;

    public Slideshow createSlideshow(final List<Image> images) {
        return slideshowRepository.createSlideshow(images);
    }

    public boolean deleteSlideshow(final Long id) {
        if (slideshowRepository.isExistsById(id)) {
            slideshowRepository.deleteSlideshow(id);
            return true;
        }
        return false;
    }

    public List<Image> getSlideshowOrder(final Long id) throws Exception {
        final Slideshow slideshow = slideshowRepository.getSlideshowById(id)
            .orElseThrow(() -> new Exception(String.format("Slideshow not found by id: %s", id)));

        List<Image> images = slideshow.getImages();

        if (images.isEmpty()) {
            throw new Exception(String.format("There is no images related to slideshow with id: %s", id));
        }

        images.sort(Comparator.comparingInt(Image::getDurationSeconds));
        return images;
    }

}
