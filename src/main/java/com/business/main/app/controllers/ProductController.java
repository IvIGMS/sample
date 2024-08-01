package com.business.main.app.controllers;

import com.business.main.app.dto.PageDTO;
import com.business.main.app.dto.ProductDTO;
import com.business.main.app.dto.ProductReturn;
import com.business.main.app.entities.ProductEntity;
import com.business.main.app.services.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public Page<ProductEntity> getAllProducts(
            @RequestParam(name = "page") int page,
            @RequestParam(name = "size") int size,
            @RequestParam(name = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(name = "direction", defaultValue = "ASC") String direction,
            @RequestParam(name = "priceMin", required = false) double priceMin,
            @RequestParam(name = "priceMax", required = false) double priceMax,
            @RequestParam(name = "id", required = false) String id)
    {

        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(--page, size, sort);
        return productService.findAll(pageable, priceMin, priceMax, id);
    }

    @GetMapping("/{id}")
    public Optional<ProductEntity> getProductById(@PathVariable UUID id) {
        return productService.findById(id);
    }

    @PostMapping
    public ProductEntity createProduct(@RequestBody ProductDTO product) {
        return productService.save(product);
    }

    @Transactional
    @PostMapping("/list")
    public ProductReturn createManyProducts(@RequestBody List<ProductDTO> products){
        return productService.createManyProducts(products);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable UUID id) {
        productService.deleteById(id);
    }
}
