package com.example.clouddisk.operation.upload.product;

import com.example.clouddisk.exception.NotSameFileExpection;
import com.example.clouddisk.exception.UploadException;
import com.example.clouddisk.operation.upload.Uploader;
import com.example.clouddisk.operation.upload.domain.UploadFile;
import com.example.clouddisk.util.FileUtil;
import com.example.clouddisk.util.PathUtil;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * @author R.Q.
 * brief:本地上传实现类
 */
@Component
public class LocalStorageUploader extends Uploader {

    public LocalStorageUploader(){

    }

    @Override
    public List<UploadFile> upload(HttpServletRequest request, UploadFile uploadFile) {
        List<UploadFile> saveUploadFileList = new ArrayList<>();
        StandardMultipartHttpServletRequest standardMultipartHttpServletRequest = (StandardMultipartHttpServletRequest)request;
        boolean isMultipart = ServletFileUpload.isMultipartContent(standardMultipartHttpServletRequest);
        if(!isMultipart){
            throw new UploadException("未包含文件上传域");
        }

        String savePath = getSaveFilePath();
        
        try{
            Iterator<String> iter = standardMultipartHttpServletRequest.getFileNames();
            while(iter.hasNext()){
                saveUploadFileList = doUpload(standardMultipartHttpServletRequest,savePath,iter,uploadFile);
            }
        }catch (IOException e){
            throw new UploadException("未包含文件上传域");
        }catch(NotSameFileExpection e){
            e.printStackTrace();
        }

        return saveUploadFileList;
    }

    private List<UploadFile> doUpload(StandardMultipartHttpServletRequest standardMultipartHttpServletRequest, String savePath, Iterator<String> iter, UploadFile uploadFile) throws IOException,NotSameFileExpection{
        List<UploadFile> saveUploadFileList = new ArrayList<>();
        MultipartFile multipartFile = standardMultipartHttpServletRequest.getFile(iter.next());

        String timeStampName = uploadFile.getIdentifier();
        String originalFilename = multipartFile.getOriginalFilename();
        String fileName = getFileName(originalFilename);
        String fileType = FileUtil.getFileExtendName(originalFilename);
        uploadFile.setFileName(fileName);
        uploadFile.setFileType(fileType);
        uploadFile.setTimeStampName(timeStampName);

        String saveFilePath = savePath + FILE_SEPAPATOR + timeStampName + "." + fileType;
        String tempFilePath = savePath + FILE_SEPAPATOR + timeStampName + "." + fileType + "_tmp";
        String minFilePath = savePath + FILE_SEPAPATOR + timeStampName + "_min" + "." + fileType;
        String confFilePath = savePath + FILE_SEPAPATOR + timeStampName + "." + "conf";
        File file = new File(PathUtil.getStaticPath() + FILE_SEPAPATOR + saveFilePath);
        File tempFile = new File(PathUtil.getFilePath() + FILE_SEPAPATOR + tempFilePath);
        File minFile = new File(PathUtil.getFilePath() + FILE_SEPAPATOR + minFilePath);
        File confFile = new File(PathUtil.getFilePath() + FILE_SEPAPATOR + confFilePath);

        // uploadFile.setIsOSS(0);
        // uploadFile.setStorageType(0);
        uploadFile.setUrl(saveFilePath);

        if(StringUtils.isEmpty(uploadFile.getTaskId())){
            uploadFile.setTaskId(UUID.randomUUID().toString());
        }

        //首先，打开要写入的文件
        RandomAccessFile raf = new RandomAccessFile(tempFile, "rw");
        //然后，打开通道
        FileChannel fileChannel = raf.getChannel();
        //计算偏移量
        long position = (uploadFile.getChunkNumber()-1)*uploadFile.getChunkSize();
        //获取分片数据
        byte[] fileBytes = multipartFile.getBytes();
        //写入数据
        fileChannel.position(position);
        fileChannel.write(ByteBuffer.wrap(fileBytes));
        fileChannel.force(true);
        fileChannel.close();
        raf.close();

        //判断是否完成文件传输并进行校验与重命名
        boolean isComplete = checkUploadStatus(uploadFile, confFile);
        if(isComplete){
            FileInputStream fileInputStream = new FileInputStream(tempFile.getPath());
            String md5 = DigestUtils.md5DigestAsHex(fileInputStream);
            fileInputStream.close();
            if(StringUtils.isNotBlank(md5)&&!md5.equals(uploadFile.getIdentifier())){
                throw new NotSameFileExpection();
            }
            tempFile.renameTo(file);
            if(FileUtil.isImageFile(uploadFile.getFileType())){
                Thumbnails.of(file).size(300, 300).toFile(minFile);
            }
            uploadFile.setSuccess(1);
            uploadFile.setMessage("上传成功");
        }else{
            uploadFile.setSuccess(0);
            uploadFile.setMessage("上传未完成");
        }

        uploadFile.setFileSize(uploadFile.getTotalSize());
        saveUploadFileList.add(uploadFile);
        return saveUploadFileList;
    }
}
