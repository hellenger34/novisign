package com.novisign.demo.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import com.novisign.demo.model.dto.Slideshow;
import com.novisign.demo.model.entity.SlideshowDb;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface SlideshowMapper {

    Slideshow toSlideshow(SlideshowDb slideshowDb);

    SlideshowDb toSlideshowDb(Slideshow slideshow);

}
