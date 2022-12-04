package com.example.clouddisk.operation.upload;

import com.example.clouddisk.operation.upload.domain.UploadFile;
import com.example.clouddisk.util.PathUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author R.Q.
 * brief:抽象上传类
 * 待处理:抽象类有什么用?
 */
@Slf4j
public abstract class Uploader {
    public static final String ROOT_PATH = "upload";
    public static final String FILE_SEPAPATOR = "/";
    //文件大小限制,kb
    public final int maxsize = 10000000;

    public abstract List<UploadFile> upload(HttpServletRequest request,UploadFile uploadFile);

    /**
     * function:根据字符串创建本地目录，并按照日期建立子目录返回
     * @return 子目录
     */
    protected String getSaveFilePath(){
        String path = ROOT_PATH;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        path = FILE_SEPAPATOR + path + FILE_SEPAPATOR + dateFormat.format(new Date());

        String staticPath = PathUtil.getStaticPath();
        File dir = new File(staticPath + path);
        if(!dir.exists()){
            try{
                boolean isSuccessMakeDir = dir.mkdirs();
                if(!isSuccessMakeDir){
                    log.error("目录创建失败:" + PathUtil.getStaticPath() + path);
                }
            }catch (Exception e){
                log.error("目录创建失败" + PathUtil.getStaticPath() + path);
                return "";
            }
        }
        return path;
    }

}
