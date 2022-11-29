package com.example.clouddisk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.clouddisk.model.User;
import java.util.List;

/**
 * @author R.Q.
 */
public interface UserMapper extends BaseMapper<User> {
    void insertUser(User user);
    List<User> selectUser();
}
