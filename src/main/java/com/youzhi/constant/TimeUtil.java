package com.youzhi.constant;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
    public static String timeUtil() {
        Date d = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        System.out.println(simpleDateFormat.format(d));
        return simpleDateFormat.format(d);
    }

}
