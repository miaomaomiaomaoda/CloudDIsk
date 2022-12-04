package com.example.clouddisk.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.clouddisk.constant.FileConstant;
import com.example.clouddisk.mapper.UserfileMapper;
import com.example.clouddisk.model.UserFile;
import com.example.clouddisk.service.UserfileService;
import com.example.clouddisk.vo.UserfileListVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author R.Q.
 */
@Service
public class UserfileServiceImpl extends ServiceImpl<UserfileMapper, UserFile> implements UserfileService {
    @Resource
    UserfileMapper userfileMapper;

    @Override
    public List<UserfileListVO> getUserFileByFilePath(String filePath, Long userId, Long currentPage, Long pageCount) {
        Long beginCount = (currentPage-1)*pageCount;
        UserFile userFile = new UserFile();
        userFile.setUserId(userId);
        userFile.setFilePath(filePath);
        return userfileMapper.userfileList(userFile, beginCount, pageCount);
    }

    /**
     * function:用于文件分类展示时获取文件
     * @param fileType 文件类型(见常量类)
     * @param currentPage 当前页
     * @param pageCount 一页显示数量
     * @param userId 用户id
     * @return 查询到的文件
     */
    @Override
    public Map<String, Object> getUserFileByType(int fileType, Long currentPage, Long pageCount, Long userId) {
        Long beginCount = (currentPage-1)*pageCount;
        List<UserfileListVO> fileList;
        Long total;
        if(fileType== FileConstant.OTHER_TYPE){
            ArrayList<String> arrList = new ArrayList<>();
            arrList.addAll(Arrays.asList(FileConstant.DOC_FILE));
            arrList.addAll(Arrays.asList(FileConstant.IMG_FILE));
            arrList.addAll(Arrays.asList(FileConstant.VIDEO_FILE));
            arrList.addAll(Arrays.asList(FileConstant.MUSIC_FILE));
            fileList = userfileMapper.selectFileNotInExtendNames(arrList,beginCount,pageCount,userId);
            total = userfileMapper.selectCountNotInExtendNames(arrList,beginCount,pageCount,userId);
        }else{
            List<String> fileExtends = null;
            if(fileType == FileConstant.IMAGE_TYPE){
                fileExtends = Arrays.asList(FileConstant.IMG_FILE);
            }else if(fileType == FileConstant.DOC_TYPE){
                fileExtends = Arrays.asList(FileConstant.DOC_FILE);
            }else if (fileType == FileConstant.VIDEO_TYPE) {
                fileExtends = Arrays.asList(FileConstant.VIDEO_FILE);
            } else if (fileType == FileConstant.MUSIC_TYPE) {
                fileExtends = Arrays.asList(FileConstant.MUSIC_FILE);
            }
            fileList = userfileMapper.selectFileByExtendName(fileExtends,beginCount,pageCount,userId);
            total = userfileMapper.selectCountByExtendName(fileExtends,beginCount,pageCount,userId);
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("list",fileList);
        map.put("total",total);
        return map;
    }
}
