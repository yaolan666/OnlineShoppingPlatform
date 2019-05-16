package com.youzhi.controller;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.youzhi.constant.Md5Util;
import com.youzhi.constant.TimeUtil;
import com.youzhi.model.User;

import java.util.List;

public class UserController extends Controller {
    private User dao = new User().dao();

    public void index() {
        String sql = dao.getSql("userList");
        List<User> list = dao.find(sql);
        setAttr("userList",list);
        render("user.html");
//        renderText(sql);
//        renderJson(dao.find(sql));
    }
    //用户注册存入数据库
    public void addUser(){
        String username = getPara("username");
        String realname = getPara("realname");
        String sex = getPara("sex");
        String userPassword = getPara("userPassword");
        userPassword = Md5Util.encode(userPassword);
        String email = getPara("email");
        String telephone = getPara("telephone");
        String ID = getPara("ID");
        String address = getPara("address");
        String sql = dao.getSql("insertUser");
        Db.update(sql, username,realname,sex,userPassword,email,telephone,ID,TimeUtil.timeUtil(),address);
        renderJson("news", "success");
    }


}
