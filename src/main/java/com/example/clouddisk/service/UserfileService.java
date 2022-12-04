package com.example.clouddisk.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.clouddisk.model.UserFile;
import com.example.clouddisk.vo.UserfileListVO;

import java.util.List;
import java.util.Map;

/**
 * @author R.Q.
 */
public interface UserfileService extends IService<UserFile> {
    /**
     * function:获取文件列表接口
     * @param filePath 文件路径
     * @param userId 用户id
     * @param currentPage 当前页
     * @param pageCount 一页显示数量
     * @return UserfileListVO 获取的用户文件列表
     */
    List<UserfileListVO> getUserFileByFilePath(String filePath,Long userId,Long currentPage,Long pageCount);

    /**
     * function:通过类型获取用户文件列表接口
     * @param fileType 文件类型(见常量类)
     * @param currentPage 当前页
     * @param pageCount 一页显示数量
     * @param userId 用户id
     * @return 用户文件列表(MAP)
     */
    Map<String, Object> getUserFileByType(int fileType, Long currentPage, Long pageCount, Long userId);
}
