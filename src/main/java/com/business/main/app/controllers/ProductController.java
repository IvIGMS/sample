package com.business.main.app.controllers;

import com.business.main.app.dto.ProductDTO;
import com.business.main.app.entities.ProductEntity;
import com.business.main.app.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public List<ProductEntity> getAllProducts() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<ProductEntity> getProductById(@PathVariable UUID id) {
        return productService.findById(id);
    }

    @PostMapping
    public ProductEntity createProduct(@RequestBody ProductDTO product) {
        return productService.save(product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable UUID id) {
        productService.deleteById(id);
    }
}
