package com.business.main.app.services;

import com.business.main.app.dto.ProductDTO;
import com.business.main.app.entities.ProductEntity;
import com.business.main.app.mappers.ProductMapper;
import com.business.main.app.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    public List<ProductEntity> findAll() {
        return productRepository.findAll();
    }

    public Optional<ProductEntity> findById(UUID id) {
        return productRepository.findById(id);
    }

    public ProductEntity save(ProductDTO product) {
        ProductEntity productEntity = productMapper.toEntity(product);
        return productRepository.save(productEntity);
    }

    public void deleteById(UUID id) {
        productRepository.deleteById(id);
    }
}
