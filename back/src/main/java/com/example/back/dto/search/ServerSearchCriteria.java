package com.example.back.dto.search;


import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ServerSearchCriteria extends BaseSearchCriteria {

    private Long userId;
    private String hostname;
    private String osInfo;
    private String address;
    private String addInfo;
    private boolean online;
    private String cpuModel;
    private Integer cpuCountCores;
    private Integer cpuCountCoresPhysical;
    private Double minFreq;
    private Double maxFreq;
}
