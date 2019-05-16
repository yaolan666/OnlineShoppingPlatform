package com.youzhi.testthread;

import java.util.ArrayList;
import java.util.List;

public class Test01 {
    public static void test() {
        List<String> a = new ArrayList<String>();
        a.add("1");
        a.add("2");
        for (String temp : a) {
            if ("2".equals(temp)) {
                a.remove(temp);
            }
        }
        System.out.println(a);
    }

    public static void main(String[] args) {
        test();
    }
}
