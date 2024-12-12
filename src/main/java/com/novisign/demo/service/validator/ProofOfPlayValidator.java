package com.novisign.demo.service.validator;

import java.util.List;
import java.util.Objects;

import com.novisign.demo.exception.ExceptionDetail;
import com.novisign.demo.exception.ValidationException;
import com.novisign.demo.repository.ImageRepository;
import com.novisign.demo.repository.SlideshowRepository;

public class ProofOfPlayValidator extends AbstractValidator<String> {

    private Long imageId;
    private Long slideshowId;

    private ImageRepository imageRepository;
    private SlideshowRepository slideshowRepository;

    public ProofOfPlayValidator withImageId(Long imageId) {
        this.imageId = imageId;
        return this;
    }

    public ProofOfPlayValidator withSlideshowId(Long slideshowId) {
        this.slideshowId = slideshowId;
        return this;
    }

    public ProofOfPlayValidator withImageRepository(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
        return this;
    }

    public ProofOfPlayValidator withSlideshowRepository(SlideshowRepository slideshowRepository) {
        this.slideshowRepository = slideshowRepository;
        return this;
    }

    public ProofOfPlayValidator() {
        super.addRules(List.of(
            (e) -> {
                if (Objects.isNull(imageId)) {
                    throw new ValidationException(ExceptionDetail.IMAGE_ID_IS_NULL);
                }
            },
            (e) -> {
                if (Objects.isNull(slideshowId)) {
                    throw new ValidationException(ExceptionDetail.SLIDESHOW_ID_IS_NULL);
                }
            },
            (e) -> {
                if (!slideshowRepository.isExistsById(slideshowId)) {
                    throw new ValidationException(ExceptionDetail.SLIDESHOW_NOT_FOUND, slideshowId);
                }
            },
            (e) -> {
                if (!imageRepository.isExists(imageId)) {
                    throw new ValidationException(ExceptionDetail.IMAGE_NOT_FOUND, imageId);
                }
            }
        ));
    }

}
