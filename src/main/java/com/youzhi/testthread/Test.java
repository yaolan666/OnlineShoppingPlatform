package com.youzhi.testthread;


import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.druid.DruidPlugin;
import com.youzhi.constant.TimeUtil;
import com.youzhi.model.Cart;
import com.youzhi.model.Goods;
import com.youzhi.model.User;
import com.youzhi.model._MappingKit;

import javax.sql.DataSource;

public class Test {
    public static void main(String[] args) {
        new Test().launch();
    }

    public void launch() {
        TestThread tt[] = new TestThread[34];
        for (int i = 0; i < 34; i++) {
            tt[i] = new TestThread("188460801" + (i + 17));
        }
//        Thread[] threads = new Thread[34];
//        for (int i = 0; i < 34; i++) {
//            threads[i] = new Thread(tt[i]);
//        }
        for (int i = 0; i < 34; i++) {
            new Thread(tt[i]).start();
        }
    }

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


    volatile static Integer number = 0;

    class TestThread implements Runnable {
        String phone;

        public TestThread(String phone) {
            this.phone = phone;
        }

        @Override
        public void run() {
            for (int i = 0; ; i++) {
                String sql_select_user_id = new User().dao().getSql("select_user_id_byphone");
                User user = new User().dao().findFirst(sql_select_user_id, phone);
                long u_id = user.getUserID();
                synchronized (number) {
                    Integer id = 1;
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Integer cart_number = 1;
                    Record cart1 = new Record().set("u_id", u_id).set("goods_id", id).set("number", 1).set("time", TimeUtil.timeUtil());
                    Db.save("cart", cart1);
                    System.out.println("购物车和商品表更改完之后的number");

                    //更新商品表中的商品数量
                    String sql1 = new Goods().dao().getSql("update_goods_number");
                    Db.update(sql1, id);

                    String sql = new Goods().dao().getSql("selectgoods_byid");
                    Goods goods = new Goods().dao().findFirst(sql, id);
                    //得到商品数量
                    number = goods.getNumber();
                    System.out.println(number);
                    if (number < 0) {
//                        System.out.println("商品数量已经为0了");
//                        System.out.println(number);
//                        String sql_cart_end_record = new Cart().dao().getSql("update_cart_end_record");
//                        Db.update(sql_cart_end_record);
//                        String update_goods_end_number = new Goods().dao().getSql("update_goods_end_number");
//                        Db.update(update_goods_end_number,id);
                        return;
                    }
                }
            }
        }
    }
}