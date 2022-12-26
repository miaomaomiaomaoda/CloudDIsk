package com.example.clouddisk.operation.download.domain;

import lombok.Data;

/**
 * @author R.Q.
 * brief:本地下载文件实体类
 */
@Data
public class DownloadFile {
    private String fileUrl;
    private String timeStampName;
}
