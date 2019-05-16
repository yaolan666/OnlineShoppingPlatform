package com.youzhi.constant;
/*
   手机号正则验证工具类
*/
public class CheckPhoneUtil {

    public static boolean phoneValidate(String phone) {
        String regex = "(\\(\\d{3,4}\\)|\\d{3,4}-|\\s)?\\d{7,14}";
        return  phone.matches(regex);
    }


    public static void main(String[] args) {

        System.out.println(CheckPhoneUtil.phoneValidate("5123456abc"));
    }
}