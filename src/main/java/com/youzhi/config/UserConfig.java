package com.youzhi.config;

import com.jfinal.config.*;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.cron4j.Cron4jPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;
import com.jfinal.plugin.redis.RedisPlugin;
import com.jfinal.template.Engine;
import com.youzhi.Task.RedisTask;
import com.youzhi.controller.*;
import com.youzhi.model._MappingKit;
//import com.youzhi.Interceptor.SessionInViewInterceptor;

public class UserConfig extends JFinalConfig {
    static Prop p;

    static void loadConfig() {
        if (p == null) {
            //优先加载配置文件
            p = PropKit.useFirstFound("properties/develop.properties");
        }
    }

    @Override
    public void configConstant(Constants me) {
        loadConfig();
        me.setDevMode(p.getBoolean("devMode", false));
        //支持Controller、Interceptor之中使用@Inject注入业务层，并且并且自动实现 AOP
        me.setInjectDependency(true);
        me.setBaseUploadPath(PropKit.get("upload.directory"));
    }

    @Override
    public void configRoute(Routes me) {

        me.add("/index", IndexController.class);
        me.add("/user", UserController.class);
        me.add("/log", LoginController.class);
        me.add("/goods", GoodsController.class);
        me.add("/cart", CartController.class);
        me.add("/test", TestController.class);
        me.add("/re", RedisController.class);
    }

    @Override
    public void configEngine(Engine me) {
        me.addSharedFunction("/common/_paginate.html");
    }

    @Override
    public void configPlugin(Plugins me) {
        // 配置 druid 数据库连接池插件
        DruidPlugin druidPlugin = new DruidPlugin(p.get("jdbcUrl"), p.get("user"), p.get("password").trim());
        me.add(druidPlugin);
        //定时任务把数据库中的数据刷到Redis中
        Cron4jPlugin cp = new Cron4jPlugin();
        cp.addTask("0-59/1 * * * *",new RedisTask());
        me.add(cp);
        // 配置ActiveRecord插件
        ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
        // 所有映射在 MappingKit 中自动化搞定
        _MappingKit.mapping(arp);
        arp.addSqlTemplate("/sql/user.sql");
//        arp.addSqlTemplate("/sql/goods.sql");
        me.add(arp);

    }

    public static DruidPlugin createDruidPlugin() {
        loadConfig();
        return new DruidPlugin(p.get("jdbcUrl"), p.get("user"), p.get("password").trim());
    }

    @Override
    public void configInterceptor(Interceptors me) {

//        me.add(new SessionInViewInterceptor());
    }

    @Override
    public void configHandler(Handlers handlers) {

    }
}
