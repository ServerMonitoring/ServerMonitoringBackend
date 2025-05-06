package com.example.back.dto.request;

import com.example.back.model.DiskIO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class DiskIODTORequest {
    @JsonProperty("read_count")
    private Long readCount;
    @JsonProperty("write_count")
    private Long writeCount;
    @JsonProperty()
    private Double read;
    private Double write;

    public static DiskIO toModel(String driveName, DiskIODTORequest dto) {
        DiskIO diskIO = new DiskIO();
        diskIO.setDriveName(driveName);
        diskIO.setReadCount(dto.getReadCount());
        diskIO.setWriteCount(dto.getWriteCount());
        diskIO.setRead(dto.getRead());
        diskIO.setWrite(dto.getWrite());
        return diskIO;
    }
}
