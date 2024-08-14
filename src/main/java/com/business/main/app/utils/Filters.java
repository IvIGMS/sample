package com.business.main.app.utils;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Filters {
    BigDecimal priceMin;
    BigDecimal priceMax;
    String id;
    DatesBundle datesBundle;
    String name;
}
