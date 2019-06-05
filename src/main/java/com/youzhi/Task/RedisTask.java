/*
package com.youzhi.Task;

import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.cron4j.ITask;
import com.jfinal.plugin.druid.DruidPlugin;
import com.youzhi.controller.RedisController;
import com.youzhi.model.RedisGenerator;
import com.youzhi.model._MappingKit;
import com.youzhi.service.GoodService;
import redis.clients.jedis.Jedis;

import javax.sql.DataSource;
import java.util.Iterator;
import java.util.Set;

public class RedisTask implements ITask {

    @Override
    public void run() {
        RedisController.putReidsDataToMySQL();
        System.out.println("we invoke the task");
    }

    @Override
    public void stop() {
        */
/*PropKit.use("properties/develop.properties");
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
        //  RedisController.putReidsDataToMySQL();*//*

        Jedis jedis = RedisGenerator.getJedis();
        GoodService goodService = new GoodService();
        Set s = jedis.keys("*");
        Iterator it = s.iterator();
        while (it.hasNext()){
            String key = (String)it.next();
            String value = jedis.get(key);
            goodService.updateNumberDatafromRedisToMySQL(key,value);
            System.out.println("键："+key+"值："+value);
        }
        System.out.println("释放连接池资源");
        RedisGenerator.returnResource(jedis);
        System.out.println("结束");
    }
}
*/
