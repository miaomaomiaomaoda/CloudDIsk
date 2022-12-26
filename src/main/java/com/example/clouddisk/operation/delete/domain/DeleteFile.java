package com.example.clouddisk.operation.delete.domain;

import lombok.Data;

/**
 * @author R.Q.
 * brief:删除文件实体类
 */
@Data
public class DeleteFile {
    private String fileUrl;
    private String timeStampName;
}
