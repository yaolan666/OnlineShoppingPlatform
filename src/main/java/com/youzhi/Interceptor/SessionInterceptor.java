package com.youzhi.Interceptor;


import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

public class SessionInterceptor implements Interceptor {
    @Override
    public void intercept(Invocation inv) {
        System.out.println("Before method invoking");
        if (inv.getController().getSessionAttr("loginPhone")!=null){
            inv.invoke();
        }
        else {
            inv.getController().redirect("/index/home.html");
        }
        System.out.println("After method invoking");
    }
}
