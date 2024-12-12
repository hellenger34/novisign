package com.novisign.demo.service.validator;

import java.net.URL;
import java.util.List;
import java.util.Objects;

import com.novisign.demo.exception.ExceptionDetail;
import com.novisign.demo.exception.ValidationException;
import com.novisign.demo.model.dto.Image;

public class ImageValidator extends AbstractValidator<Image> {

    public ImageValidator() {
        super.addRules(List.of(
            (entity) -> {
                if (Objects.isNull(entity)) {
                    throw new ValidationException(ExceptionDetail.IMAGE_IS_NULL);
                }
            },
            (entity) -> {
                if (entity.getUrl().isBlank()) {
                    throw new ValidationException(ExceptionDetail.IMAGE_URL_IS_EMPTY);
                }
            },

            (entity) -> {
                if (Objects.isNull(entity.getDurationSeconds()) || entity.getDurationSeconds() <= 0) {
                    throw new ValidationException(ExceptionDetail.IMAGE_DURATION_IS_INVALID);
                }
            },
            (entity) -> {
                try {
                    new URL(entity.getUrl()).toURI();
                } catch (Exception e) {
                    throw new ValidationException(ExceptionDetail.IMAGE_URL_IS_INVALID);
                }
            }));
    }

    @Override
    public AbstractValidator<Image> withEntity(Image entity) {
        return super.withEntity(entity);
    }
}
