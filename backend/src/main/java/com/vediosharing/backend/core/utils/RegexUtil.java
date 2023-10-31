package com.vediosharing.backend.core.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName RegexUtil
 * @Description 正则判断
 * @Author Colin
 * @Date 2023/10/31 22:04
 * @Version 1.0
 */
public class RegexUtil {
    public static boolean isValidUsername(String username) {
        // 使用正则表达式判断用户名是否合法
        String regex = "^[a-zA-Z0-9]{6,16}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }

    public static boolean isValidPassword(String password) {
        String regex = "^[a-zA-Z0-9]{6,20}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
