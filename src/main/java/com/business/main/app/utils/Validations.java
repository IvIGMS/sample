package com.business.main.app.utils;

import com.business.main.app.dto.ProductDTO;
import com.business.main.app.dto.validationObject.ValidationProductDTO;
import com.business.main.app.entities.ProductEntity;
import com.business.main.app.mappers.ProductMapper;
import com.business.main.app.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Validations {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductMapper productMapper;

    // todo: dar funcionalidad a este metodo.
    // fixme: meter las constantes en una tabla y consultarlas

    public ValidationProductDTO validateProduct(ProductDTO productDTO){
        ValidationProductDTO validationProductDTO = new ValidationProductDTO(0, 0, 0, 0, 0);
        List<ProductEntity> pe = productRepository.findByName(productMapper.toEntity(productDTO).getName());
        if(!pe.isEmpty()){
            if(productDTO.getName().equals(pe.get(0).getName())){
                validationProductDTO.setDuplicatedValidation(1);
                validationProductDTO.setIsValidate(1);
            }
        } else {
            if(productDTO.getName().length() > 100 || productDTO.getName().length() < 5){
                validationProductDTO.setNameValidation(1);
                validationProductDTO.setIsValidate(1);
            } if(productDTO.getDescription().length() < 50 || productDTO.getDescription().length() > 200){
                validationProductDTO.setDescriptionValidation(1);
                validationProductDTO.setIsValidate(1);
            } if(productDTO.getPrice() < 0.10 || productDTO.getPrice() > 10000){
                validationProductDTO.setPriceValidation(1);
                validationProductDTO.setIsValidate(1);
            }
        }
        return validationProductDTO;
    }
}

// isValidate - duplicated - name - desc - price
