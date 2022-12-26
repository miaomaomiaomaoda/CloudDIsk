package com.example.clouddisk.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author R.Q.
 * brief:下载文件DTO
 */
@Data
@Schema(name = "下载文件DTO",required = true)
public class DownloadFileDTO {
    @Schema(description = "用户文件id")
    private  Long userFileId;
}
