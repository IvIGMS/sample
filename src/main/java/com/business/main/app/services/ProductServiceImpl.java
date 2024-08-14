package com.business.main.app.services;

import com.business.main.app.dto.ProductDTO;
import com.business.main.app.dto.ProductReturn;
import com.business.main.app.dto.validationObject.ValidationProductDTO;
import com.business.main.app.entities.ProductEntity;
import com.business.main.app.mappers.ProductMapper;
import com.business.main.app.repositories.ProductRepository;
import com.business.main.app.utils.Filters;
import com.business.main.app.utils.Validations;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private Validations validations;

    @Autowired
    private EmailService emailService;

    public Page<ProductEntity> findAll(Pageable pageable, Filters filters) {
        return productRepository.findAllProductEntity(pageable,
                filters.getPriceMin(),
                filters.getPriceMax(),
                filters.getId(),
                filters.getName(),
                filters.getDatesBundle().getCreatedAtMin(),
                filters.getDatesBundle().getCreatedAtMax(),
                filters.getDatesBundle().getUpdatedAtMin(),
                filters.getDatesBundle().getUpdatedAtMax());
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

    public ProductReturn createManyProducts(List<ProductDTO> products) {
        // todo: enviar un correo con el resultado de lo importado.
        ProductReturn result = ProductReturn.builder()
                .productsImported(new ArrayList<>())
                .productsError(new ArrayList<>())
                .build();
        products.forEach(p -> {
            ValidationProductDTO validationObject = validations.validateProduct(p);

            // Product ok
            if(validationObject.getIsValidate() == 0){
                ProductEntity pe = productMapper.toEntity(p);
                productRepository.save(pe);
                result.getProductsImported().add(pe);
                log.info("El producto {}se ha creado correctamente", p.getName());
            //Product ko
            } else {
                ProductEntity pe = productMapper.toEntity(p);
                result.getProductsError().add(pe);
                String error = getString(p, validationObject);
                log.error(error);
            }
        });
        result.setProductsImportedCounter(result.getProductsImported().size());
        result.setProductsErrorCounter(result.getProductsError().size());
        // Notification
        emailService.sendEmail("friasgilivan@gmail.com", "Esto es una simple prueba para ver que se puede enviar el correo.");
        return result;
    }

    private String getString(ProductDTO p, ValidationProductDTO validationObject) {
        String error = "El producto " + p.getName() + " no ha podido crearse. Errors: ";
        // todo: meter los errores como constantes
        if(validationObject.getDuplicatedValidation()==1){
            error = error += "Name duplicated. ";
        } if(validationObject.getNameValidation()==1){
            error = error += "Name too sort/long. ";
        } if(validationObject.getDescriptionValidation()==1){
            error = error += "Description to sort/long. ";
        } if(validationObject.getPriceValidation()==1){
            error = error += "Price to small/big. ";
        }
        return error;
    }

    public List<ProductEntity> getProductsForExcell() {
        return productRepository.findAll();
    }
}


