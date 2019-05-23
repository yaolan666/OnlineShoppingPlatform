package com.youzhi.controller;

import com.beust.jcommander.Strings;
import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.aop.Inject;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;
import com.youzhi.Interceptor.SessionInterceptor;
import com.youzhi.constant.DateUtil;
import com.youzhi.constant.TimeUtil;
import com.youzhi.model.Cart;
import com.youzhi.model.Goods;
import com.youzhi.model.User;
import com.youzhi.service.GoodService;
import com.jfinal.kit.PropKit;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
//import com.youzhi.Interceptor.SessionInViewInterceptor;

//@Before(SessionInViewInterceptor.class)
public class GoodsController extends Controller {
    private Goods dao = new Goods().dao();
    @Inject
    GoodService service;
   //查询商品
    public void index() {
//        setAttr("goodsPage", service.paginate(getParaToInt(0, 1), 3));
//        render("goodsList.html");
        String search = getPara("search");
        setAttr("goodsPage", service.paginate(getParaToInt(0, 1), 3, search));
        setAttr("username", getSessionAttr("loginUserName"));
        if (!Strings.isStringEmpty(search)) {
            setAttr("search", search);
        } else {
            setAttr("search", "");
        }
        render("goodsList.html");
    }

    //修改商品
    @Clear
    public void editGoods() {
        setAttr("goods", service.findById(getParaToInt()));
        setAttr("imgUrl", PropKit.get("update.src"));
        render("editGoods.html");
    }

    //修改商品修改后的数据提交到数据库
    @Clear
    public void updateGoods() throws ParseException {
        UploadFile file = getFile();
        if (file != null) {
            //获取原始名字
            String fileName = file.getOriginalFileName();
            //获取文件后缀
            String type = fileName.substring(fileName.lastIndexOf("."));
            System.out.println();
            //将文件名字改成时间戳
            fileName = DateUtil.dateUtil();
            //得到文件上传的路径
            String path = file.getUploadPath();
            //覆盖原来的文件
            boolean flag = file.getFile().renameTo(new File(path + "\\" + fileName + type));
            System.out.println(fileName);
            fileName = "image/" + fileName + type;
//            String id = getPara("goods.goodsID");
            Record goods = Db.findById("goods", getPara("goods.id")).set("goodsImage", fileName).set("goodsName", getPara("goods.goodsName")).set("goodsPrice", getPara("goods.goodsPrice")).set("goodsCategory", getPara("goods.goodsCategory")).set("Manufacturename", getPara("goods.Manufacturename")).set("goodsDescription", getPara("goods.goodsDescription")).set("number", getPara("goods.number"));
            Db.update("goods", goods);
            redirect("/goods");
        } else {
//            String id = getPara("goods.goodsID");
            Record goods = Db.findById("goods", getPara("goods.id")).set("goodsImage", getPara("goods.goodsImage")).set("goodsName", getPara("goods.goodsName")).set("goodsPrice", getPara("goods.goodsPrice")).set("goodsCategory", getPara("goods.goodsCategory")).set("Manufacturename", getPara("goods.Manufacturename")).set("goodsDescription", getPara("goods.goodsDescription")).set("number", getPara("goods.number"));
            Db.update("goods", goods);
            redirect("/goods");

        }
    }

    @Clear
    public void addGoods() throws ParseException {
        RedisController.getMySQlDataToRedis();
        UploadFile file = getFile();
//        String goodsName = getPara("goodsName");
//        String goodsPrice = getPara("goodsPrice");
//        String goodsCategory = getPara("goodsCategory");
//        String Manufacturename = getPara("Manufacturename");
//        String goodsDescription = getPara("goodsDescription");
//        String number = getPara("number");
        if (file != null) {
            //获取原始名字
            String fileName = file.getOriginalFileName();
            //获取文件后缀
            String type = fileName.substring(fileName.lastIndexOf("."));
            //将文件名字改成时间戳
            fileName = DateUtil.dateUtil();
            //得到文件上传的路径
            System.out.println();
            String path = file.getUploadPath();
            //图片上传到本地
            boolean flag = file.getFile().renameTo(new File(path + "\\" + fileName + type));
            // 把文件路径存到数据库
            fileName = "image/" + fileName + type;
            Record goods = new Record().set("goodsImage", fileName).set("goodsName", getPara("goodsName")).set("goodsPrice", getPara("goodsPrice")).set("goodsCategory", getPara("goodsCategory")).set("Manufacturename", getPara("Manufacturename")).set("goodsDescription", getPara("goodsDescription")).set("number", getPara("number"));
            Db.save("goods", goods);
            redirect("/goods");
        } else {
            Record goods = new Record().set("goodsName", getPara("goodsName")).set("goodsPrice", getPara("goodsPrice")).set("goodsCategory", getPara("goodsCategory")).set("Manufacturename", getPara("Manufacturename")).set("goodsDescription", getPara("goodsDescription")).set("number", getPara("number"));
            Db.save("goods", goods);
            redirect("/goods");
        }
    }

    //删除商品
    @Clear
    public void deleteGoods() {
        Integer id = getParaToInt();
        service.deleteById(getParaToInt());
        redirect("/goods");
    }

    //图片上传方法
    @Clear
    public void upload() throws ParseException {
        UploadFile file = getFile();
        //获取原始名字
        String fileName = file.getOriginalFileName();
        //获取文件后缀
        String type = fileName.substring(fileName.lastIndexOf("."));
        //将文件名字改成时间戳
        fileName = DateUtil.dateUtil();
        //得到文件上传的路径
        String path = file.getUploadPath();
        //图片上传到本地
        boolean flag = file.getFile().renameTo(new File(path + "\\" + fileName + type));
        // 把文件路径存到数据库
        fileName = "image/" + fileName + type;
    }


}
