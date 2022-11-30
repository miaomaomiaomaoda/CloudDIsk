package com.example.clouddisk.service;

import com.example.clouddisk.common.RestResult;
import com.example.clouddisk.model.User;

/**
 * @author R.Q.
 */
public interface UserService {
    RestResult<String> registerUser(User user);

    RestResult<User> login(User user);

}
