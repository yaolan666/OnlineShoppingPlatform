package com.youzhi.controller;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.redis.Redis;
import com.youzhi.model.RedisGenerator;
import com.youzhi.service.GoodService;
import redis.clients.jedis.Jedis;

import java.awt.geom.GeneralPath;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class RedisController extends Controller {
   /* public void test() {
        Jedis userCache = Redis.use("onlineShopPlatform").getJedis();
        userCache.set("qwery", "qwery");
        renderText(userCache.get("qwery"));
    }*/

    //从数据库中读取数据放入Redis中
    public static void getMySQlDataToRedis() {
        GoodService goodService = new GoodService();
        Jedis jedis = RedisGenerator.getJedis();
        for (Record s : goodService.getGoodsIdAndNumber()) {
            jedis.set(s.get("id").toString(), s.get("number").toString());
        }
        System.out.println("存入完毕");
        //释放Jedis连接池资源
        RedisGenerator.returnResource(jedis);
    }

    //活动结束后把redis中的数据刷回到数据库中
    public static void putReidsDataToMySQL() {
//        GoodService goodService = new GoodService();
        Jedis jedis = RedisGenerator.getJedis();
        Set s = jedis.keys("*");
        Iterator it = s.iterator();
        while (it.hasNext()){
            String key = (String)it.next();
            String value = jedis.get(key);
            Db.update("update goods set number=? where id=?",value,key);
            System.out.println("键："+key+"值："+value);
        }
        System.out.println("释放连接池资源");
        RedisGenerator.returnResource(jedis);
    }
    //得到Redis数据库中所有key和value
    /*public static void getRedisKeysAndValue(){
        Jedis jedis = RedisGenerator.getJedis();
        Set s = jedis.keys("*");
        Iterator it = s.iterator();
        while (it.hasNext()){
            String key = (String)it.next();
            String value = jedis.get(key);
            System.out.println("键："+key+"值："+value);
        }
    }*/
}
