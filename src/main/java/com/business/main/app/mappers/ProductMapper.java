package com.business.main.app.mappers;

import com.business.main.app.dto.ProductDTO;
import com.business.main.app.entities.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductDTO toDto(ProductEntity product);
    ProductEntity toEntity(ProductDTO productDTO);
}
