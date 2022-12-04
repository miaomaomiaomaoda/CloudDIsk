package com.example.clouddisk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.clouddisk.dto.UserfileListDTO;
import com.example.clouddisk.model.UserFile;
import com.example.clouddisk.vo.UserfileListVO;

import java.util.List;

/**
 * @author R.Q.
 */
public interface UserfileMapper extends BaseMapper<UserFile> {
    /**
     * functon:文件查询接口
     * @param userfile 用户文件
     * @param beginCount 开始
     * @param pageCount 页数
     * @return 查询的用户文件列表
     */
    List<UserfileListVO> userfileList(UserFile userfile,Long beginCount,Long pageCount);
    List<UserfileListVO> selectFileByExtendName(List<String> fileNameList, Long beginCount, Long pageCount, long userId);
    Long selectCountByExtendName(List<String> fileNameList, Long beginCount, Long pageCount, long userId);
    List<UserfileListVO> selectFileNotInExtendNames(List<String> fileNameList, Long beginCount, Long pageCount, long userId);
    Long selectCountNotInExtendNames(List<String> fileNameList, Long beginCount, Long pageCount, long userId);
}
