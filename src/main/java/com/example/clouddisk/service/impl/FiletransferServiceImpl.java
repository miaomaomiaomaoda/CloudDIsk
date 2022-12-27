package com.example.clouddisk.service.impl;

import com.example.clouddisk.dto.DownloadFileDTO;
import com.example.clouddisk.dto.UploadFileDTO;
import com.example.clouddisk.mapper.FileMapper;
import com.example.clouddisk.mapper.UserfileMapper;
import com.example.clouddisk.model.UserFile;
import com.example.clouddisk.operation.FileOperationFactory;
import com.example.clouddisk.operation.download.Downloader;
import com.example.clouddisk.operation.download.domain.DownloadFile;
import com.example.clouddisk.operation.upload.Uploader;
import com.example.clouddisk.operation.upload.domain.UploadFile;
import com.example.clouddisk.service.FiletransferService;
import com.example.clouddisk.util.DateUtil;
import com.example.clouddisk.util.PropertiesUtil;
import com.example.clouddisk.model.File;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author R.Q.
 */
@Service
public class FiletransferServiceImpl implements FiletransferService {
    @Resource
    FileMapper fileMapper;
    @Resource
    UserfileMapper userfileMapper;
    @Resource
    FileOperationFactory localStorageOperationFactory;

    @Override
    public void uploadFile(HttpServletRequest httpServletRequest, UploadFileDTO uploadFileDTO, Long userId) {
        Uploader uploader = null;
        UploadFile uploadFile = new UploadFile();
        uploadFile.setChunkNumber(uploadFileDTO.getChunkNumber());
        uploadFile.setChunkSize(uploadFileDTO.getChunkSize());
        uploadFile.setTotalChunks(uploadFileDTO.getTotalChunks());
        uploadFile.setIdentifier(uploadFileDTO.getIdentifier());
        uploadFile.setTotalSize(uploadFileDTO.getTotalSize());
        uploadFile.setCurrentChunkSize(uploadFileDTO.getCurrentChunkSize());

        String storageType = PropertiesUtil.getProperty("file.storage-type");
        synchronized (FiletransferService.class){
            //暂时只设计了本地存储的方案
            if("0".equals(storageType)){
                uploader = localStorageOperationFactory.getUploader();
            }
        }

        List<UploadFile> uploadFileList = uploader.upload(httpServletRequest, uploadFile);
        for (int i = 0; i < uploadFileList.size(); i++) {
            uploadFile = uploadFileList.get(i);
            File file = new File();
            file.setIdentifier(uploadFileDTO.getIdentifier());
            file.setStorageType(Integer.parseInt(storageType));
            file.setTimeStampName(uploadFile.getTimeStampName());
            if(uploadFile.getSuccess()==1){
                file.setFileUrl(uploadFile.getUrl());
                file.setFileSize(uploadFile.getFileSize());
                file.setPointCount(1);
                fileMapper.insert(file);

                UserFile userFile = new UserFile();
                userFile.setUserId(file.getFileId());
                userFile.setExtendName(uploadFile.getFileType());
                userFile.setFileName(uploadFile.getFileName());
                userFile.setFilePath(uploadFileDTO.getFilePath());
                userFile.setDeleteFlag(0);
                userFile.setUserId(userId);
                userFile.setIsDir(0);
                userFile.setUploadTime(DateUtil.getCurrentTime());
                userfileMapper.insert(userFile);
            }
        }
    }

    @Override
    public void downloadFile(HttpServletResponse httpServletResponse, DownloadFileDTO downloadFileDTO) {
        UserFile userFile = userfileMapper.selectById(downloadFileDTO.getUserFileId());
        String fileName = userFile.getFileName() + "." + userFile.getExtendName();
        try{
            fileName = new String(fileName.getBytes("utf-8"), "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //设置强制下载不打开
        httpServletResponse.setContentType("application/force-download");
        //设置文件名
        httpServletResponse.addHeader("Content-Disposition", "attachment;fileName=" + fileName);

        File file = fileMapper.selectById(userFile.getFileId());
        Downloader downloader = null;
        if(file.getStorageType()==0){
            //获取工厂
            downloader = localStorageOperationFactory.getDownloader();
        }
        DownloadFile downloadFile = new DownloadFile();
        downloadFile.setFileUrl(file.getFileUrl());
        downloadFile.setTimeStampName(file.getTimeStampName());
        downloader.download(httpServletResponse,downloadFile);
    }
}
