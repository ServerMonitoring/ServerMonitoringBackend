package com.example.back.dto.request;

import com.example.back.model.DiskIO;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class DiskIODTORequest {
    private String driveName;
    private Long readCount;
    private Long writeCount;
    private Double read;
    private Double write;

    public static DiskIO toModel(DiskIODTORequest diskIODTORequest){
        DiskIO diskIO = new DiskIO();
        diskIO.setDriveName(diskIODTORequest.getDriveName());
        diskIO.setReadCount(diskIODTORequest.getReadCount());
        diskIO.setWriteCount(diskIODTORequest.getWriteCount());
        diskIO.setRead(diskIODTORequest.getRead());
        diskIO.setWrite(diskIODTORequest.getWrite());
        return diskIO;
    }

}
