package com.business.main.app.services;

import com.business.main.app.dto.ProductDTO;
import com.business.main.app.dto.ProductReturn;
import com.business.main.app.dto.validationObject.ValidationProductDTO;
import com.business.main.app.entities.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public interface ProductService {

    public Page<ProductEntity> findAll(Pageable pageable, BigDecimal priceMin, BigDecimal priceMax, String id);

    public Optional<ProductEntity> findById(UUID id);

    public ProductEntity save(ProductDTO product);

    public void deleteById(UUID id);

    public ProductReturn createManyProducts(List<ProductDTO> products);

    public List<ProductEntity> getProductsForExcell();
}
