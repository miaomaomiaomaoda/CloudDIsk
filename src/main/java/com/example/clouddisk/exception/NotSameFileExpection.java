package com.example.clouddisk.exception;

/**
 * @author R.Q.
 * brief:MD5校验失败异常
 */
public class NotSameFileExpection extends Exception{
    public NotSameFileExpection(){
        super("File MD5 Different");
    }
}
