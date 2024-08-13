package com.business.main.app.controllers;

import com.business.main.app.dto.ProductDTO;
import com.business.main.app.dto.ProductReturn;
import com.business.main.app.entities.ProductEntity;
import com.business.main.app.services.ProductService;
import jakarta.transaction.Transactional;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Value("${MAGIC_WORD}")
    private String magicWord;

    @GetMapping
    public Page<ProductEntity> getAllProducts(
            @RequestParam(name = "page") int page,
            @RequestParam(name = "size") int size,
            @RequestParam(name = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(name = "direction", defaultValue = "ASC") String direction,
            @RequestParam(name = "priceMin", required = false) BigDecimal priceMin,
            @RequestParam(name = "priceMax", required = false) BigDecimal priceMax,
            @RequestParam(name = "id", required = false) String id)
    {

        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(--page, size, sort);
        return productService.findAll(pageable, priceMin, priceMax, id);
    }

    @GetMapping("/{id}")
    public Optional<ProductEntity> getProductById(@PathVariable UUID id) {
        // Fixme: I'm not working
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

    @GetMapping("/excel")
    public ResponseEntity<byte[]> downloadExcel() throws IOException {
        List<ProductEntity> products = productService.getProductsForExcell();

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Products");
            // Header
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("id");
            headerRow.createCell(1).setCellValue("name");
            headerRow.createCell(2).setCellValue("description");
            headerRow.createCell(3).setCellValue("price");
            headerRow.createCell(4).setCellValue("createdAt");
            headerRow.createCell(5).setCellValue("updatedAt");
            // Data
            int rowIdx = 1;
            for (ProductEntity producto : products) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(producto.getId());
                row.createCell(1).setCellValue(producto.getName());
                row.createCell(2).setCellValue(producto.getDescription());
                row.createCell(3).setCellValue(producto.getPrice());
                row.createCell(4).setCellValue(producto.getCreatedAt().toString());
                row.createCell(5).setCellValue(producto.getUpdatedAt().toString());
            }

            workbook.write(out);
            ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=products.xlsx");

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(out.toByteArray());
        }
    }

    @GetMapping("/secret")
    public String keepTheSecret(){
        return "Congratulations. The magic word is '" + magicWord + "'";
    }
}
