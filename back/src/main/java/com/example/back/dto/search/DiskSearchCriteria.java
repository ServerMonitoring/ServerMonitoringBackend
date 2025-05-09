package com.example.back.dto.search;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DiskSearchCriteria {
    private String device;
    private String mountpoint;
}
