package com.novisign.demo.model.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Slideshow {

    private Long id;

    @Builder.Default
    private List<Image> images = new ArrayList<>();

}
