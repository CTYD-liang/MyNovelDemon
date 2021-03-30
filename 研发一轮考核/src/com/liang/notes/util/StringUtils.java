package com.liang.notes.util;

/**
 * 字符串工具类
 */
public class StringUtils {

    /**
     * 判断是否为空
     * @param str
     * @return
     */
    public static boolean isEmpty(String str){
        //如果字符串前面有空格，去掉再比较
        return str == null || "".equals(str.trim());
    }

    public static boolean isNotEmpty(String str){
        //字符串前面有空格，去掉再比较
        return str != null && "".equals(str.trim());
    }

}
