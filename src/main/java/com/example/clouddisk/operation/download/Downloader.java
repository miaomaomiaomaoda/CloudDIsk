package com.example.clouddisk.operation.download;

import com.example.clouddisk.operation.download.domain.DownloadFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author R.Q.
 * brief:抽象下载类
 */
public abstract class Downloader {
    public abstract void download(HttpServletResponse httpServletResponse, DownloadFile downloadFile);
}
