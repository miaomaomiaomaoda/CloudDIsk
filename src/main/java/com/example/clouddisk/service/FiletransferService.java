package com.example.clouddisk.service;

import com.example.clouddisk.dto.DownloadFileDTO;
import com.example.clouddisk.dto.UploadFileDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author R.Q.
 * brief:文件传输服务
 */
public interface FiletransferService {
    void uploadFile(HttpServletRequest httpServletRequest, UploadFileDTO uploadFileDTO,Long userId);
    void downloadFile(HttpServletResponse httpServletResponse, DownloadFileDTO downloadFileDTO);
}
