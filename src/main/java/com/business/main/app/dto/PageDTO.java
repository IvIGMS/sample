package com.business.main.app.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class PageDTO {
    int page;
    int size;
}
