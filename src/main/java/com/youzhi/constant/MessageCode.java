package com.youzhi.constant;

public class MessageCode {
    public static String getShotMessageCode(){
        Integer messageCode =(int)((Math.random()*9+1)*1000);
        return messageCode.toString();
    }
    public static void main(String[] args) {
        System.out.println((int)((Math.random()*9+1)*1000));
    }
}
