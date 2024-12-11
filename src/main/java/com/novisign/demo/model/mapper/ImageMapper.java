package com.novisign.demo.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import com.novisign.demo.model.dto.Image;
import com.novisign.demo.model.entity.ImageDb;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ImageMapper {

    ImageDb toImageDb(Image image);

    Image toImage(ImageDb imageDb);
}
