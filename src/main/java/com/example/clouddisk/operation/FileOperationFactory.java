package com.example.clouddisk.operation;

import com.example.clouddisk.operation.delete.Deleter;
import com.example.clouddisk.operation.download.Downloader;
import com.example.clouddisk.operation.upload.Uploader;

/**
 * @author R.Q.
 * brief:抽象工厂
 */
public interface FileOperationFactory {
    Uploader getUploader();
    Downloader getDownloader();
    Deleter getDeleter();
}
