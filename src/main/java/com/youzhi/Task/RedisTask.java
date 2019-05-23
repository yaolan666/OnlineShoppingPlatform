package com.youzhi.Task;

import com.jfinal.plugin.cron4j.ITask;
import com.youzhi.controller.RedisController;

public class RedisTask implements ITask {

    @Override
    public void run() {
//        RedisController.getReidsDataToMySQL();
        System.out.println("we invoke the task");
    }

    @Override
    public void stop() {
        System.out.println("结束");
    }
}
