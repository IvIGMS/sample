package com.business.main.app.utils;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class DatesBundle {
    LocalDateTime createdAtMin;
    LocalDateTime createdAtMax;
    LocalDateTime updatedAtMin;
    LocalDateTime updatedAtMax;
}
