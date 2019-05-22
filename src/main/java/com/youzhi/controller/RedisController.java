package com.youzhi.controller;

import com.jfinal.core.Controller;
import com.jfinal.plugin.redis.Redis;
import redis.clients.jedis.Jedis;

public class RedisController extends Controller {
    public void test() {
        Jedis userCache = Redis.use("onlineShopPlatform").getJedis();
        userCache.set("qwery", "qwery");
        renderText(userCache.get("qwery"));
    }

}
