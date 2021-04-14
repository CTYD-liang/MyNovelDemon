package com.liang.notes.util;

/**
 * 字符串工具类
 * @author 86178
 */
public class StringUtils {

    /**
     * 判断是否为空
     * @param str
     * @return
     */
    public static boolean isEmpty(String str){
        //如果字符串前面有空格，去掉再比较
        if(str == null || "".equals(str.trim())){
            return true;
        }else{
            return false;
        }

    }

    /**
     * 判断不是空
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str){
        //字符串前面有空格，去掉再比较
        if(str != null && "".equals(str.trim())){
            return true;
        }else{
            return false;
        }
    }
}
