package com.example.back.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class DiskIODTO {
    private String driveName;
    private Long readCount;
    private Long writeCount;
    private Double read;
    private Double write;

}
