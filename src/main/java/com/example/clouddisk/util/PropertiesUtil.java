package com.example.clouddisk.util;

import org.springframework.core.env.Environment;

/**
 * @author R.Q.
 * brief:配置工具类
 */
public class PropertiesUtil {
    private static Environment env = null;

    public static void setEnvironment(Environment env){
        PropertiesUtil.env = env;
    }

    public static String getProperty(String key){
        return PropertiesUtil.env.getProperty(key);
    }
}
