package com.example.clouddisk.controller;

import com.example.clouddisk.common.RestResult;
import com.example.clouddisk.dto.DownloadFileDTO;
import com.example.clouddisk.dto.UploadFileDTO;
import com.example.clouddisk.model.File;
import com.example.clouddisk.model.User;
import com.example.clouddisk.model.UserFile;
import com.example.clouddisk.service.FileService;
import com.example.clouddisk.service.FiletransferService;
import com.example.clouddisk.service.UserService;
import com.example.clouddisk.service.UserfileService;
import com.example.clouddisk.util.DateUtil;
import com.example.clouddisk.util.FileUtil;
import com.example.clouddisk.vo.UploadFileVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author R.Q.
 * brief:文件传输接口，用作文件上传、下载和删除
 */
@Tag(name = "filetransfer",description = "文件传输接口，用作文件上传、下载")
@RestController
@RequestMapping("/filetransfer")
public class FiletransferController {
    @Resource
    UserService userService;
    @Resource
    FileService fileService;
    @Resource
    UserfileService userfileService;
    @Resource
    FiletransferService filetransferService;

    @Operation(summary = "极速上传",description = "校验文件md5判断文件是否存在，如果存在直接上传成功并返回skipUpload=true，即跳过上传。如果不存在则返回skipUpload=false需要再次调用该接口的POST方法",tags = {"filetransfer"})
    @GetMapping(value="/uploadfile")
    @ResponseBody
    public RestResult<UploadFileVo> uploadFileSpeed(UploadFileDTO uploadFileDTO, @RequestHeader("token") String token){
        User sessionUser = userService.getUserByToken(token);
        if(sessionUser==null){
            return RestResult.fail().message("未登录");
        }

        UploadFileVo uploadFileVo = new UploadFileVo();
        Map<String, Object> param = new HashMap<>();
        param.put("identifier",uploadFileDTO.getIdentifier());
        synchronized (FiletransferController.class){
            List<File> list = fileService.listByMap(param);
            if(list!=null&&!list.isEmpty()){
                File file = list.get(0);
                UserFile userFile = new UserFile();
                userFile.setFileId(file.getFileId());
                userFile.setUserId(sessionUser.getUserId());
                userFile.setFilePath(uploadFileDTO.getFilePath());

                String filename = uploadFileDTO.getFilename();
                userFile.setFileName(filename.substring(0,filename.lastIndexOf(".")));
                userFile.setExtendName(FileUtil.getFileExtendName(filename));
                userFile.setIsDir(0);
                userFile.setUploadTime(DateUtil.getCurrentTime());
                userFile.setDeleteFlag(0);
                userfileService.save(userFile);
                // fileService.increaseFilePointCount(file.getFileId());
                uploadFileVo.setSkipUpload(true);
            }else{
                uploadFileVo.setSkipUpload(false);
            }
        }
        return RestResult.success().data(uploadFileVo);
    }

    @Operation(summary = "上传文件",description = "上传文件接口",tags = {"filetransfer"})
    @RequestMapping(value = "/uploadfile",method = RequestMethod.POST)
    @ResponseBody
    public RestResult<UploadFileVo> uploadFile(HttpServletRequest request,UploadFileDTO uploadFileDTO,@RequestHeader("token") String token){
        User sessionUser = userService.getUserByToken(token);
        if(sessionUser==null){
            return RestResult.fail().message("未登录");
        }

        filetransferService.uploadFile(request,uploadFileDTO,sessionUser.getUserId());
        UploadFileVo uploadFileVo = new UploadFileVo();
        return RestResult.success().data(uploadFileVo);
    }

    @Operation(summary = "下载文件",description = "下载文件接口",tags = {"filetransfer"})
    @RequestMapping(value = "/downloadfile",method = RequestMethod.GET)
    public void downloadFile(HttpServletResponse response, DownloadFileDTO downloadFileDTO){
        filetransferService.downloadFile(response,downloadFileDTO);
    }
}
