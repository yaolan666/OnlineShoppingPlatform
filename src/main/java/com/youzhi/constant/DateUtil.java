package com.youzhi.constant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static String dateToStamp(String s) throws ParseException {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }
    public static String dateUtil()throws ParseException {
        Date d = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        System.out.println(simpleDateFormat.format(d));
        DateUtil.dateToStamp(simpleDateFormat.format(d));
//        System.out.println(DateUtil.dateToStamp(simpleDateFormat.format(d)));
        return DateUtil.dateToStamp(simpleDateFormat.format(d));
    }
    public static void main(String[] args) throws ParseException{
        Date d = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(simpleDateFormat.format(d));
        DateUtil.dateToStamp(simpleDateFormat.format(d));
        System.out.println(DateUtil.dateToStamp(simpleDateFormat.format(d)));
    }
}
