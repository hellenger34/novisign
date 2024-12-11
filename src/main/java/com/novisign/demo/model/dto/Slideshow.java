package com.novisign.demo.model.dto;

import java.util.List;

import lombok.Data;

@Data
public class Slideshow {

    private Long id;
    private List<Image> images;

}
