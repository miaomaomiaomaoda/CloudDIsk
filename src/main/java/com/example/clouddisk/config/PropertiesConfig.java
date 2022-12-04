package com.example.clouddisk.config;

import com.example.clouddisk.util.PropertiesUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author R.Q.
 * brief:环境配置类，读取环境变量
 */
@Configuration
public class PropertiesConfig {
    @Resource
    private Environment env;

    @PostConstruct
    public void setProperties(){
        PropertiesUtil.setEnvironment(env);
    }
}
