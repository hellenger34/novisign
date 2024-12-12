package com.novisign.demo.exception;

import lombok.Getter;

@Getter
public enum ExceptionDetail {
    IMAGE_IS_NULL(
        "Image should not be null",
        1001
    ),
    IMAGE_URL_IS_EMPTY(
        "Image url should not be empty",
        1002
    ),
    IMAGE_DURATION_IS_INVALID(
        "Image duration can't be null or less then 0",
        1003
    ),
    IMAGE_URL_IS_INVALID(
        "Image url is not valid. Example of valid url: https://example.com/image8.jpg",
        1004
    ),
    IMAGE_ID_IS_NULL(
        "Image ID should not be null",
        1005
    ),
    SLIDESHOW_ID_IS_NULL(
        "Slideshow ID should not be null",
        1006
    ),
    IMAGE_NOT_FOUND(
        "Image with id: %s was not found",
        1007
    ),
    SLIDESHOW_NOT_FOUND(
        "Slideshow with id: %s was not found",
        1008
    ),
    THERE_ID_NOT_IMAGES_RELATED_TO_SLIDESHOW(
        "There is no images related to slideshow with id: %s",
        1009
    );


    private String message;
    private int code;

    ExceptionDetail(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public String getFormattedMessage(Object... params) {
        return String.format(message, params);
    }
}
