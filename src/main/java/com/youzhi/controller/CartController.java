package com.youzhi.controller;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.youzhi.constant.TimeUtil;
import com.youzhi.model.Cart;
import com.youzhi.model.Goods;
import com.youzhi.model.User;

import java.util.List;

public class CartController extends Controller {
    private Goods dao = new Goods().dao();

    public void addGoodsToCart() {
        String phone = getSessionAttr("loginPhone");
        Integer id = getParaToInt();
        //根据id查询到商品
        String sql = dao.getSql("selectgoods_byid");
        Goods goods = dao.findFirst(sql, id);
        //得到商品数量
        Integer number = goods.getNumber();
//        商品数量减一
        number = number - 1;
//        根据商品表中的商品数量
        String sql1 = dao.getSql("update_goods_nummber_byid");
        Db.update(sql1, number, id);

        //根据用户的手机号获取用户的id
        String sql_select_user_id = new User().dao().getSql("select_user_id_byphone");
        User user = new User().dao().findFirst(sql_select_user_id, phone);
        long u_id = user.getUserID();
//        查询购物车中此用户拥有该商品的数量

        String sql__select_cart_number = new Cart().dao().getSql("selectcartgoods_nummber");
        Cart cart = new Cart().dao().findFirst(sql__select_cart_number, id, u_id);

        if (cart == null) {
            Integer cart_number = 1;
            Record cart1 = new Record().set("u_id", u_id).set("goods_id", id).set("number", 1).set("time", TimeUtil.timeUtil());
            Db.save("cart", cart1);
        } else {
            Integer cart_number = cart.getNumber() + 1;
            String sql_cart_id = new Cart().dao().getSql("select_cartid_byu_idandgoods_id");
            Cart cart2 = new Cart().dao().findFirst(sql_cart_id, u_id, id);
            Integer cart_id = cart2.getId();
//        购物车表中插入商品信息和用户信息
            Record cart1 = Db.findById("cart", cart_id).set("u_id", u_id).set("goods_id", id).set("number", cart_number).set("time", TimeUtil.timeUtil());
            Db.update("cart", cart1);
//            String sql2 = new Cart().dao().getSql("insert_cart_bygoodsid");
//            Db.save("cart",phone,id,cart_number);
        }

        redirect("/goods");

    }

    public void queryUserCart() {
        String phone = getSessionAttr("loginPhone");
        String sql = new User().dao().getSql("select_user_id_byphone");
        User u = new User().dao().findFirst(sql, phone);
        long u_id = u.getUserID();
        //购物车表和商品表建立内连接通过用户id查询所需要的记录
        String sql_select_inner_join_byuid = Db.getSql("sql_select_inner_join_byuid");
        List<Record> list = Db.find(sql_select_inner_join_byuid, u_id);
        setAttr("cartList", list);
        render("userCart.html");
        System.out.println(list);
    }

    public void buyGoods() {
        Integer id = getParaToInt();
        System.out.println(id);
    }

    public void deleteGoods() {
        Integer id = getParaToInt();
        //根据商品的id得到商品的数量然后购物车中全部删除
        //商品表中数量增加回去
        String phone = getSessionAttr("loginPhone");
        String sql = new User().dao().getSql("select_user_id_byphone");
        User u = new User().dao().findFirst(sql, phone);
        long u_id = u.getUserID();
        String select_number_id_bygoodsidanduid = Db.getSql("select_number_id_bygoodsidanduid");
//        List<Record> records = Db.find(select_number_id_bygoodsidanduid, u_id, id);
//        long number = records.get(0).get("number");
        Record record = Db.findFirst(select_number_id_bygoodsidanduid, u_id, id);
        System.out.println(record);
        long number = record.getLong("number");
        String sqldelete = dao.getSql("delete_cart_bygoodsidanduid");
        Db.delete(sqldelete, id, u_id);

        String sqladdnumber = dao.getSql("selectgoods_byid");
        Goods goods = dao.findFirst(sqladdnumber, id);
        //得到商品数量
        long numberadd = goods.getNumber();
        //商品数量加
        numberadd = numberadd + number;
        //更新商品表中的商品数量
        String sql1 = dao.getSql("update_goods_nummber_byid");
        Db.update(sql1, numberadd, id);
        redirect("/cart/queryUserCart");
    }
}
