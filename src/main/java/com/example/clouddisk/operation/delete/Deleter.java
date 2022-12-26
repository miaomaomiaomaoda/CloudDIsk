package com.example.clouddisk.operation.delete;

import com.example.clouddisk.operation.delete.domain.DeleteFile;

/**
 * @author R.Q.
 * 抽象文件删除类
 */
public abstract class Deleter {
    public abstract void delete(DeleteFile deleteFile);
}
