package com.web.thuvien.util;

public class NumberUtil {
    public static boolean isNumber(String str) {
        try{
            Long.parseLong(str);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
