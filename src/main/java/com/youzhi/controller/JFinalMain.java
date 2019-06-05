package com.youzhi.controller;

import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.druid.DruidPlugin;
import com.youzhi.constant.TimeUtil;
import com.youzhi.model.Goods;
import com.youzhi.model.RedisGenerator;
import com.youzhi.model.User;
import com.youzhi.model._MappingKit;
import com.youzhi.service.GoodService;
import redis.clients.jedis.Jedis;

import javax.sql.DataSource;
import java.text.SimpleDateFormat;
import java.util.*;

public class JFinalMain {
    private static User dao = new User().dao();

    static {
        PropKit.use("properties/develop.properties");
        // 配置 druid 数据库连接池插件
        DruidPlugin druidPlugin = new DruidPlugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password").trim());
        druidPlugin.start();
        DataSource dataSource = druidPlugin.getDataSource();
        // 配置ActiveRecord插件
        ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
        // 所有映射在 MappingKit 中自动化搞定
        _MappingKit.mapping(arp);
        arp.addSqlTemplate("/sql/user.sql");
        arp.start();
    }

    //查询用户列表
    public static void seletUserList() {
        String sql = dao.getSql("userList");
        List<User> user = dao.find(sql);
        for (User u : user) {
            System.out.println(u);
        }
    }

    //查询用户密码
    public static void selectUserPassword() {
        String sql_select_password = dao.getSql("select_password_byTelePhone");
        List<User> list = dao.find(sql_select_password, "18846080352");
        User user = dao.findFirst(sql_select_password, "18846080352");
        System.out.println(user);
//        for (User u:password){
//            System.out.println(u);
//        }
//        String password = list.get(0).getUserPassword();
        String password = list.get(0).get("userPassword");
        String password1 = list.get(0).getStr("userPassword");
        System.out.println(password1);

    }

    //增加用户
    public static void insertUser() {
        TimeUtil.timeUtil();
        String sql = dao.getSql("insertUser");
        Db.update(sql, "lucy", "王芳", "女", "456789", "18714456963@163.com", "18714456963", "230521199605648716", TimeUtil.timeUtil(), "吉林");
    }

    //删除用户
    public static void deleteUser() {
        String sql = dao.getSql("deleteUser");
        Db.delete(sql, 4);
    }

    //修改用户
    public static void updateUser() {
        String sql = dao.getSql("updateUser");
        Db.update(sql, "1", "1", "1", "1", "1", "1", "1", "5");
    }

    //    public static List<Record> getGoodsIdAndNumber() {
//        List<Record> IdAndNumberList = Db.find("select id,number from goods");
//        return IdAndNumberList;
////        List<Record> IdAndNumberList = Db.find("select id,number from goods");
////        return IdAndNumberList;
//
//    }
    public static void main(String[] args) {
//        insertUser();
//        deleteUser();
//       seletUserList();
//        selectUserPassword();
//        updateUser();
//        List<String> list = new ArrayList<String>();
//        list.add("11");
//        System.out.println(list);
//        for (String u : list) {
//            System.out.println(u);
//        }
//        for (Record s:getGoodsIdAndNumber()){
//            System.out.println(s.get("id"));
//            System.out.println(s.get("number"));
//        }
//        System.out.println(getGoodsIdAndNumber());
        /*GoodService.getGoodsIdAndNumber();
        System.out.println(GoodService.getGoodsIdAndNumber());
        for (Record s : GoodService.getGoodsIdAndNumber()) {
            System.out.println(s.get("id"));
            System.out.println(s.get("number"));
        }*/

//        getMySQlDataToRedis();
    }

    //得到Redis数据库中所有key和value
   /* public static void getRedisKeys(){
        Jedis jedis = RedisGenerator.getJedis();
        Set s = jedis.keys("*");
        Iterator it = s.iterator();
        while (it.hasNext()){
            String key = (String)it.next();
            String value = jedis.get(key);
            System.out.println("键："+key+"值："+value);
        }
    }*/
    /*public static void getMySQlDataToRedis() {
        GoodService goodService = new GoodService();
        Jedis jedis = RedisGenerator.getJedis();
        for (Record s : goodService.getGoodsIdAndNumber()) {
            jedis.set(s.get("id").toString(), s.get("number").toString());
        }
        System.out.println("存入完毕");
        RedisGenerator.returnResource(jedis);
    }*/

}
