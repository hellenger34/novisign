package com.novisign.demo.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class Image {

    private Long id;
    private String url;
    private Integer durationSeconds;
}
