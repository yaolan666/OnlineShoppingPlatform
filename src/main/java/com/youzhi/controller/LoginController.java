package com.youzhi.controller;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.youzhi.constant.CheckPhoneUtil;
import com.youzhi.constant.Md5Util;
import com.youzhi.model.User;

import java.util.List;

public class LoginController extends Controller {
    private User dao = new User().dao();

    //登录时检查手机号是否存在
    public void checkLoginPhone() {
        String phone = getPara("phone");
        String sql = dao.getSql("select_login_phone");
        User user = dao.findFirst(sql, phone);
        if (user == null) {
            renderJson("news", "noexist");
        } else {
            renderJson("news", "success");
        }
    }

    // 登录时检查密码是否输入正确
    public void checkLoginPassword() {
        String phone = getPara("phone");
        String password = getPara("pass");
        String sql = dao.getSql("select_password_byTelePhone");
        User u = dao.findFirst(sql, phone);
        //得到查询的手机号
        String u_password = u.getUserPassword();
        //前端输入的手机号加密
        password = Md5Util.encode(password);
        System.out.println(u_password);
        System.out.println(password);
        String sql_selectUsername = dao.getSql("select_username_byphone");
        User u_selectUsername = dao.findFirst(sql_selectUsername, phone);
        String username = u_selectUsername.getUsername();
        //比较输出的手机号是否正确
        if (!(u.getUserPassword().equals(password))) {
            renderJson("news", "fault");
        } else {
            //把登录的用户的手机号放入session中
            setSessionAttr("loginPhone", phone);
            //把登录的查询到的用户名放入session中
            setSessionAttr("loginUserName", username);
            renderJson("news", "success");
        }
    }

    //检查修改密码时原密码是否输入正确
    public void checkOriginalPassword(){
        String phone = getPara("phone");
        String pass = getPara("pass");
        pass = Md5Util.encode(pass);
        String sql = dao.getSql("select_original_password");
        User u = dao.findFirst(sql,phone);
        String password = u.getUserPassword();
        if(!pass.equals(password)){
            renderJson("s","fault");
        }else{
            renderJson("s","success");
        }
    }
    //修改密码
    public void editPassword() {
        String phone = getSessionAttr("loginPhone");

        System.out.println("前台获取的电话号码" + phone);
        setAttr("phone", phone);
        render("/login/editPassword.html");
    }
    //检查注册的手机号是否已经注册
    public void checkRegisterPhone(){
        String phone = getPara("phone");
        if(CheckPhoneUtil.phoneValidate(phone)){
            String sql = dao.getSql("checkphoneexists");
            List<User> list = dao.find(sql,phone);
            if(list.size()!=0){
                renderJson("news","fault");
            }else{
                renderJson("news","noexists");
            }
        }else{
            renderJson("news","format");
        }
    }

    //修改密码的方法
    public  void updatePassword(){
        String phone =  getSessionAttr("loginPhone");
        String new_password = getPara("password2");
        new_password = Md5Util.encode(new_password);

        String sql = dao.getSql("update_password_byphone");
        Db.update(sql,new_password,phone);
        renderJson("s", "success");
    }
}
