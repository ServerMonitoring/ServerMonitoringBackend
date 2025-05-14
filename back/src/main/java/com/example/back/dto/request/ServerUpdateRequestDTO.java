package com.example.back.dto.request;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ServerUpdateRequestDTO {
    private Long id;
    private String address;
    private String serverName;
    private String addInfo;
}
