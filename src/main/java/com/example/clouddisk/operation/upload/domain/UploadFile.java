package com.example.clouddisk.operation.upload.domain;

import lombok.Data;

/**
 * @author R.Q.
 * brief:本地上传文件实体类
 */
@Data
public class UploadFile {
    private String fileName;
    private String fileType;
    private long fileSize;
    private String timeStampName;
    private int success;
    private String message;
    private String url;
    //切片上传相关参数
    private String taskId;
    private int chunkNumber;
    private long chunkSize;
    private int totalChunks;
    private String identifier;
    private long totalSize;
    private long currentChunkSize;
}
