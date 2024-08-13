package com.business.main.app.dto.validationObject;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ValidationProductDTO {
    int isValidate;
    int duplicatedValidation;
    int nameValidation;
    int descriptionValidation;
    int priceValidation;
}
