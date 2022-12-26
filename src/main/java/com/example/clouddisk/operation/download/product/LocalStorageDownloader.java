package com.example.clouddisk.operation.download.product;

import com.example.clouddisk.operation.download.Downloader;
import com.example.clouddisk.operation.download.domain.DownloadFile;
import com.example.clouddisk.util.PathUtil;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @author R.Q.
 * brief:本地文件下载实现类
 */
@Component
public class LocalStorageDownloader extends Downloader {


    @Override
    public void download(HttpServletResponse httpServletResponse, DownloadFile downloadFile) {
        BufferedInputStream bis = null;
        byte[] buffer = new byte[1024];
        //设置文件路径
        File file = new File(PathUtil.getStaticPath() + downloadFile.getFileUrl());
        if(file.exists()) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream os = httpServletResponse.getOutputStream();

                int count = bis.read(buffer);
                while (count != -1) {
                    os.write(buffer, 0, count);
                    count = bis.read(buffer);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally{
                if(bis!=null){
                    try{
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }
}
