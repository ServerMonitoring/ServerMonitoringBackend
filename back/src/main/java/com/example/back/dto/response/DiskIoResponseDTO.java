package com.example.back.dto.response;

import com.example.back.model.DiskIO;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class DiskIoResponseDTO {
    private Long id;
    private String driveName;
    private Long readCount;
    private Long writeCount;
    private Double read;
    private Double write;

    public static DiskIoResponseDTO toDTO(DiskIO diskIO){
        return DiskIoResponseDTO.builder()
                .id(diskIO.getDiskIoId())
                .driveName(diskIO.getDriveName())
                .readCount(diskIO.getReadCount())
                .writeCount(diskIO.getWriteCount())
                .read(diskIO.getRead())
                .write(diskIO.getWrite())
                .build();
    }
}
