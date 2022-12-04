package com.example.clouddisk.util;

/**
 * @author R.Q.
 * brief:文件工具类
 * function:定义文件的一些常用操作方法
 */
public class FileUtil {
    public static final String[] IMG_FILE = {"bmp", "jpg", "png", "tif", "gif", "jpeg"};
    public static final String[] DOC_FILE = {"doc", "docx", "ppt", "pptx", "xls", "xlsx", "txt", "hlp", "wps", "rtf", "html", "pdf"};
    public static final String[] VIDEO_FILE = {"avi", "mp4", "mpg", "mov", "swf"};
    public static final String[] MUSIC_FILE = {"wav", "aif", "au", "mp3", "ram", "wma", "mmf", "amr", "aac", "flac"};
    public static final int IMAGE_TYPE = 1;
    public static final int DOC_TYPE = 2;
    public static final int VIDEO_TYPE = 3;
    public static final int MUSIC_TYPE = 4;
    public static final int OTHER_TYPE = 5;
    public static final int SHARE_FILE = 6;
    public static final int RECYCLE_FILE = 7;

    /**
     * function:判断是否是图片文件
     * 待修改:后面考虑将本类的IMG_FILE数组删除，使用常量类中的数组
     * @param extendName 文件扩展名
     * @return true-图片文件
     */
    public static boolean isImageFile(String extendName){
        for (int i = 0; i < IMG_FILE.length; i++) {
            if(extendName.equalsIgnoreCase(IMG_FILE[i])){
                return true;
            }
        }
        return false;
    }

    /**
     * function:获取文件扩展名
     * @param fileName 文件名
     * @return 文件扩展名(无则返回空串)
     */
    public static String getFileExtendName(String fileName){
        if(fileName.lastIndexOf(".")==-1){
            return "";
        }
        return fileName.substring(fileName.lastIndexOf(".")+1);
    }

    /**
     * function:获取不含扩展名的文件名
     * @param fileName 文件名
     * @return 文件名(不含扩展名)
     */
    public static String getFileNameNotExtend(String fileName){
        String fileType = getFileExtendName(fileName);
        return fileName.replace("."+fileType,"");
    }
}
