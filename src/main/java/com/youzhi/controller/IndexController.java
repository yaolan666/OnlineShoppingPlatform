package com.youzhi.controller;

import com.jfinal.core.Controller;

public class IndexController extends Controller {
    public void index(){
        render("/test/test.html");
    }
}
