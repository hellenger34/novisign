package com.novisign.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.novisign.demo.model.dto.Image;
import com.novisign.demo.model.entity.ImageDb;
import com.novisign.demo.model.mapper.ImageMapper;
import com.novisign.demo.repository.jpa.JpaImageRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ImageRepository {

    private final JpaImageRepository jpaImageRepository;
    private final ImageMapper imageMapper;

    public Image createImage(final Image image) {
        final ImageDb savedImage = jpaImageRepository.findByUrl(image.getUrl())
            .orElseGet(() -> jpaImageRepository.save(imageMapper.toImageDb(image)));

        return imageMapper.toImage(savedImage);
    }

    public boolean isExists(final Long id) {
        return jpaImageRepository.existsById(id);
    }

    public void deleteImage(final Long id) {
        jpaImageRepository.deleteById(id);
    }

    public List<Image> searchImages(final String keyword, final Integer duration) {
        return jpaImageRepository.findByUrlContainingOrDurationSeconds(keyword, duration)
            .stream()
            .map(imageMapper::toImage)
            .toList();
    }

    public Optional<Image> getImageByUrl(final String url) {
        return jpaImageRepository.findByUrl(url).map(imageMapper::toImage);
    }

}
