package com.youzhi.testthread;

import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.redis.Redis;
import com.youzhi.constant.TimeUtil;
import com.youzhi.model.Cart;
import com.youzhi.model.Goods;
import com.youzhi.model.User;
import com.youzhi.model._MappingKit;
import redis.clients.jedis.Jedis;

import javax.sql.DataSource;
import java.util.UUID;

public class Test01 {
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

    public static void main(String[] args) {
        TestThread tt[] = new TestThread[34];
        for (int i = 0; i < 34; i++) {
            tt[i] = new TestThread("188460801" + (i + 17));
        }
//        Thread[] threads = new Thread[34];
//        for (int i = 0; i < 34; i++) {
//            threads[i] = new Thread(tt[i]);
//        }
//        for (int i = 0; i < 34; i++) {
//        for (int i = 0; ; i++) {
        new Thread(tt[0]).start();
        new Thread(tt[1]).start();
        new Thread(tt[2]).start();
        new Thread(tt[3]).start();
        new Thread(tt[4]).start();
        new Thread(tt[5]).start();
        new Thread(tt[6]).start();
        new Thread(tt[7]).start();
        new Thread(tt[8]).start();
        new Thread(tt[9]).start();
        new Thread(tt[10]).start();
        new Thread(tt[11]).start();
        new Thread(tt[12]).start();
        new Thread(tt[13]).start();
        new Thread(tt[14]).start();

    }

    Integer id = 1;

    public synchronized void test(String phone) {
        UUID uu_id = UUID.randomUUID();
        for (int i = 0; ; i++) {
            String sql_select_user_id = new User().dao().getSql("select_user_id_byphone");
            User user = new User().dao().findFirst(sql_select_user_id, phone);
            long u_id = user.getUserID();
            String sql = new Goods().dao().getSql("selectgoods_byid");
            Goods goods = new Goods().dao().findFirst(sql, id);
            Integer number = goods.getNumber();
            Jedis userCache = Redis.use("onlineShopPlatform").getJedis();
            userCache.set(id.toString(), number.toString());
            boolean b = RedisTool.tryGetDistributedLock(userCache, id.toString(), uu_id.toString(), 5);
            userCache.connect();
            if (b) {
                if (Integer.parseInt(userCache.get(id.toString())) >= 0) {
                    System.out.println(number);
                    userCache.decr(id.toString());
                }
                if (Integer.parseInt(userCache.get(id.toString())) <= -1) {
                    System.out.println(number);
                    System.out.println("商品数量已经为0了");
                    return;
                }
            } else {
                return;
            }


            String sql1 = new Goods().dao().getSql("update_goods_nummber_byid");
            Db.update(sql1, userCache.get(id.toString()), id);
            System.out.println("更新商品表完毕");


            String sql__select_cart_number = new Cart().dao().getSql("selectcartgoods_nummber");
            Cart cart = new Cart().dao().findFirst(sql__select_cart_number, id, u_id);
            System.out.println("查看购物车完毕");
            if (cart == null) {
                Integer cart_number = 1;
                Record cart1 = new Record().set("u_id", u_id).set("goods_id", id).set("number", 1).set("time", TimeUtil.timeUtil());
                Db.save("cart", cart1);
                System.out.println("加入购物车完毕");
            } else {
                Integer cart_number = cart.getNumber() + 1;
                String sql_cart_id = new Cart().dao().getSql("select_cartid_byu_idandgoods_id");
                Cart cart2 = new Cart().dao().findFirst(sql_cart_id, u_id, id);
                Integer cart_id = cart2.getId();
                Record cart1 = Db.findById("cart", cart_id).set("u_id", u_id).set("goods_id", id).set("number", cart_number).set("time", TimeUtil.timeUtil());
                Db.update("cart", cart1);
                System.out.println("添加已经有的商品完毕");
            }
            RedisTool.releaseDistributedLock(userCache, id.toString(), uu_id.toString());
        }
    }
}

class TestThread1 implements Runnable {
    String phone;

    public TestThread1(String phone) {
        this.phone = phone;
    }

    @Override
    public void run() {
        new Test01().test(phone);

    }

}
