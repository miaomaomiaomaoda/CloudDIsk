package com.example.clouddisk.vo;

import io.swagger.models.auth.In;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author R.Q.
 * brief:上传文件VO,后端给前端的
 */
@Data
@Schema(name="文件上传Vo",required = true)
public class UploadFileVo {
    @Schema(description = "时间戳",example = "15165156516165")
    private String timeStampName;
    @Schema(description = "跳过上传",example = "true")
    private boolean skipUpload;
    @Schema(description = "是否需要合并分片",example = "true")
    private boolean needMerge;
    @Schema(description = "已上传的分片",example = "[1,2,3,4]")
    private List<Integer> uploaded;
}
