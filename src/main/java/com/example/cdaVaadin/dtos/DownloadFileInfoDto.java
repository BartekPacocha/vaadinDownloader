package com.example.cdaVaadin.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DownloadFileInfoDto {

    private String fileNumber;
    private String fileName;
    private Long fileSize;
    private Long totalBytesRead;
    private Double downloadSpeed;
    private Double downloadProgress;

}
