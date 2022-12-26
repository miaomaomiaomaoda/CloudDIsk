package com.example.clouddisk.operation.delete.product;


import com.example.clouddisk.operation.delete.Deleter;
import com.example.clouddisk.operation.delete.domain.DeleteFile;
import com.example.clouddisk.util.FileUtil;
import com.example.clouddisk.util.PathUtil;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @author R.Q.
 */
@Component
public class LocalStorageDeleter extends Deleter {
    @Override
    public void delete(DeleteFile deleteFile) {
        File file = new File(PathUtil.getStaticPath() + deleteFile.getFileUrl());
        if(file.exists()){
            file.delete();
        }

        if(FileUtil.isImageFile(FileUtil.getFileExtendName(deleteFile.getFileUrl()))){
            File minFile = new File(PathUtil.getStaticPath()+deleteFile.getFileUrl().replace(deleteFile.getTimeStampName(),deleteFile.getTimeStampName()+"_min"));
            if(minFile.exists()){
                minFile.delete();
            }
        }
    }
}
