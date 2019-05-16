package com.youzhi.constant;
/*
    密码验证的正则验证类
  */
public class CheckPasswordUtil {

    public static boolean passValidate(String password) {
//        String regex = "^[a-zA-Z](?![0-9]+$)(?![a-zA-Z]+$)([a-zA-Z0-9]|[._#@]){5,17}$";
        String regex = "^[^\\\\s]{6,16}$";
        return password.matches(regex);
    }

    public static void main(String[] args) {
       Boolean b = passValidate("");
        System.out.println(b);
    }
}
