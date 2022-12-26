package com.example.clouddisk.operation;

import com.example.clouddisk.operation.delete.Deleter;
import com.example.clouddisk.operation.delete.product.LocalStorageDeleter;
import com.example.clouddisk.operation.download.Downloader;
import com.example.clouddisk.operation.download.product.LocalStorageDownloader;
import com.example.clouddisk.operation.upload.Uploader;
import com.example.clouddisk.operation.upload.product.LocalStorageUploader;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author R.Q.
 * brief:具体工厂
 */
@Component
public class LocalStorageOperationFactory implements FileOperationFactory{
    @Resource
    LocalStorageUploader localStorageUploader;
    @Resource
    LocalStorageDownloader localStorageDownloader;
    @Resource
    LocalStorageDeleter localStorageDeleter;

    @Override
    public Uploader getUploader() {
        return localStorageUploader;
    }

    @Override
    public Downloader getDownloader() {
        return localStorageDownloader;
    }

    @Override
    public Deleter getDeleter() {
        return localStorageDeleter;
    }
}
