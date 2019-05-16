package com.youzhi.controller;

import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.druid.DruidPlugin;
import com.youzhi.constant.TimeUtil;
import com.youzhi.model.Goods;
import com.youzhi.model.User;
import com.youzhi.model._MappingKit;

import javax.sql.DataSource;

public class TestController extends Controller {
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

    private Goods dao = new Goods().dao();

    public  void addGoodsToCart() {
        String phone = "18846080352";
        //根据用户的手机号获取用户的id
        System.out.println("执行了controller");
        String sql_select_user_id = new User().dao().getSql("select_user_id_byphone");
        User user = new User().dao().findFirst(sql_select_user_id, phone);
        System.out.println("controller执行结束了");
        long u_id = user.getUserID();
        try {
            Integer id = 1;
            //根据id查询到商品
            String sql = dao.getSql("selectgoods_byid");
            Goods goods = dao.findFirst(sql, id);
            //得到商品数量
            Integer number = goods.getNumber();
            synchronized (number) {
                if (number >= 0) {
                    //商品数量减一
                    number = number - 1;
                }
                if (number == -1) {
                    System.out.println("商品数量已经为0了");
                    return;
                }
                //更新商品表中的商品数量
                String sql1 = dao.getSql("update_goods_nummber_byid");
                Db.update(sql1, number, id);
                if (number >= 0) {
                    Integer cart_number = 1;
                    Record cart1 = new Record().set("u_id", u_id).set("goods_id", id).set("number", 1).set("time", TimeUtil.timeUtil());
                    Db.save("cart", cart1);
                }
                if (number == -1) {
                    return;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        TestController controller = new TestController();
        System.out.println("执行controller");
        controller.addGoodsToCart();
    }


}
