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
     * @param beginCount 起始文件数
     * @param pageCount 一页显示数量
     * @return UserfileListVO 查询的用户文件列表(VO)
     */
    List<UserfileListVO> userfileList(UserFile userfile,Long beginCount,Long pageCount);

    /**
     * function:搜索扩展名在扩展名列表中的文件
     * @param fileNameList 文件扩展名列表
     * @param beginCount 起始文件数
     * @param pageCount 一页显示数量
     * @param userId 用户id
     * @return UserfileListVO 查询的用户文件列表(VO)
     */
    List<UserfileListVO> selectFileByExtendName(List<String> fileNameList, Long beginCount, Long pageCount, long userId);

    /**
     * function:搜索扩展名在扩展名列表中的文件数量
     * @param fileNameList 文件扩展名列表
     * @param beginCount 起始文件数
     * @param pageCount 一页显示数量
     * @param userId 用户id
     * @return count 查询的文件数
     */
    Long selectCountByExtendName(List<String> fileNameList, Long beginCount, Long pageCount, long userId);

    /**
     * function:搜索扩展名不在扩展名列表中的文件
     * @param fileNameList 文件扩展名列表
     * @param beginCount 起始文件数
     * @param pageCount 一页显示数量
     * @param userId 用户id
     * @return UserfileListVO 查询的用户文件列表(VO)
     */
    List<UserfileListVO> selectFileNotInExtendNames(List<String> fileNameList, Long beginCount, Long pageCount, long userId);

    /**
     * function:搜索扩展名不在扩展名列表中的文件数量
     * @param fileNameList 文件扩展名列表
     * @param beginCount 起始文件数
     * @param pageCount 一页显示数量
     * @param userId 用户id
     * @return count 查询的文件数
     */
    Long selectCountNotInExtendNames(List<String> fileNameList, Long beginCount, Long pageCount, long userId);
}
