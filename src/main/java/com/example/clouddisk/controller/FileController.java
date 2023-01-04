package com.example.clouddisk.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.clouddisk.common.RestResult;
import com.example.clouddisk.dto.CreateFileDTO;
import com.example.clouddisk.dto.UserfileListDTO;
import com.example.clouddisk.model.User;
import com.example.clouddisk.model.UserFile;
import com.example.clouddisk.service.UserService;
import com.example.clouddisk.service.UserfileService;
import com.example.clouddisk.util.DateUtil;
import com.example.clouddisk.vo.UserfileListVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author R.Q.
 */

@Tag(name="file",description = "该接口为文件接口，主要用来完成文件的基本操作，eg.创建目录，删除，移动，复制")
@RestController
@Slf4j
@RequestMapping("/file")
public class FileController {
    @Resource
    UserService userService;
    @Resource
    UserfileService userfileService;

    @Operation(summary = "创建文件",description = "目录(文件夹)的创建",tags = {"file"})
    @PostMapping(value = "/createfile")
    @ResponseBody
    public RestResult<String> createFile(@RequestBody CreateFileDTO createFileDTO, @RequestHeader("token") String token){
        User sessionUser = userService.getUserByToken(token);
        if(sessionUser==null){
            return RestResult.fail().message("token认证失败");
        }
        LambdaQueryWrapper<UserFile> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserFile::getFileName,"").eq(UserFile::getFilePath,"").eq(UserFile::getUserId,0);
        List<UserFile> userfiles = userfileService.list(lambdaQueryWrapper);
        if(!userfiles.isEmpty()){
            return RestResult.fail().message("同目录下文件名重复");
        }

        UserFile userFile = new UserFile();
        userFile.setUserId(sessionUser.getUserId());
        userFile.setFileName(createFileDTO.getFileName());
        userFile.setFilePath(createFileDTO.getFilePath());
        userFile.setIsDir(1);
        userFile.setUploadTime(DateUtil.getCurrentTime());
        userFile.setDeleteFlag(0);
        userfileService.save(userFile);
        return RestResult.success();
    }

    @Operation(summary = "获取文件列表",description = "用来前台文件列表展示",tags = {"file"})
    @GetMapping(value = "/getfilelist")
    @ResponseBody
    public RestResult<UserfileListVO> getUserfileList(UserfileListDTO userfileListDTO,@RequestHeader("token")String token){
        User sessionUser = userService.getUserByToken(token);
        if(sessionUser==null){
            return RestResult.fail().message("token验证失败");
        }
        List<UserfileListVO> fileList = userfileService.getUserFileByFilePath(userfileListDTO.getFilePath(), sessionUser.getUserId(), userfileListDTO.getCurrentPage(), userfileListDTO.getPageCount());
        LambdaQueryWrapper<UserFile> userFileLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userFileLambdaQueryWrapper.eq(UserFile::getUserId,sessionUser.getUserId()).eq(UserFile::getFilePath,userfileListDTO.getFilePath());
        int total = userfileService.count(userFileLambdaQueryWrapper);
        HashMap<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("list",fileList);
        return RestResult.success().data(map);
    }

    @Operation(summary = "通过文件类型搜索文件",description = "该接口可以实现文件格式分类查看",tags = {"file"})
    @GetMapping(value = "/selectfilebyfiletype")
    @ResponseBody
    public RestResult<List<Map<String,Object>>> selectFileByFileType(int fileType,Long currentPage,Long pageCount,@RequestHeader("token")String token){
        User sessionUser = userService.getUserByToken(token);
        if(sessionUser==null){
            return RestResult.fail().message("token验证失败");
        }
        Long userId = sessionUser.getUserId();

        Map<String, Object> map = userfileService.getUserFileByType(fileType, currentPage, pageCount, userId);
        return RestResult.success().data(map);
    }

}
