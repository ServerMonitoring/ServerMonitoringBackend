package com.example.back.dto.search;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BaseSearchCriteria {
    private Long id;
    private Boolean distinct = false;
    private String sortBy;
}
