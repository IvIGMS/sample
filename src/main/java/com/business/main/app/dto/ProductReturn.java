package com.business.main.app.dto;

import com.business.main.app.entities.ProductEntity;
import lombok.*;

import java.util.List;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductReturn {
    List<ProductEntity> productsImported;
    List<ProductEntity> productsError;
}
