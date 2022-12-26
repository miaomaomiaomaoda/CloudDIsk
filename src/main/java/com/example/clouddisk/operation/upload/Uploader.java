package com.example.clouddisk.operation.upload;

import com.example.clouddisk.operation.upload.domain.UploadFile;
import com.example.clouddisk.util.FileUtil;
import com.example.clouddisk.util.PathUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
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

    public synchronized boolean checkUploadStatus(UploadFile param,File confFile) throws IOException{
        RandomAccessFile confAccessFile = new RandomAccessFile(confFile,"rw");
        //设置文件长度
        confAccessFile.setLength(param.getTotalChunks());
        //设置初始偏移量
        confAccessFile.seek(param.getChunkNumber()-1);
        //将指定的一个字节写入文件中127，
        confAccessFile.write(Byte.MAX_VALUE);
        byte[] completeStatusList = FileUtils.readFileToByteArray(confFile);
        confAccessFile.close();
        //创建conf文件，文件长度为总分片数，每上传一个分块就向conf文件中写入一个127，那么没上传的位置就是默认的0，已上传的就是127
        for (int i = 0; i < completeStatusList.length; i++) {
            if(completeStatusList[i]!=Byte.MAX_VALUE){
                return false;
            }
        }
        confFile.delete();
        return true;
    }

    /**
     * function:根据原始文件名生成新文件名
     * @return 新文件名
     */
    protected String getTimeStampName() {
        SecureRandom number = null;
        try {
            number = SecureRandom.getInstance("SHA1PRNG");
            return "" + number.nextInt(10000) + System.currentTimeMillis();
        } catch (NoSuchAlgorithmException e) {
            log.error("生成安全随机数失败");
        }
        return "" + System.currentTimeMillis();
    }

    protected String getFileName(String fileName){
        if(!fileName.contains(".")){
            return fileName;
        }
        return fileName.substring(0,fileName.lastIndexOf("."));
    }
}
