package com.youzhi.constant;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.alibaba.fastjson.JSONObject;

import java.net.URLEncoder;

public class ApiTest {

    //普通短信
    private void sendsms() throws Exception {
        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod("http://api.1cloudsp.com/api/v2/single_send");
        postMethod.getParams().setContentCharset("UTF-8");
        postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,new DefaultHttpMethodRetryHandler());

        String accesskey = "4kMy43jfl6tkLZmv"; //用户开发key
        String accessSecret = "4bKyeN8walvJCAbEDtr7A6pKipsJxbFB"; //用户开发秘钥

        NameValuePair[] data = {
                new NameValuePair("accesskey", accesskey),
                new NameValuePair("secret", accessSecret),
                new NameValuePair("sign", "27492"),
                new NameValuePair("templateId", "25434"),
                new NameValuePair("mobile", "18846080352"),
                new NameValuePair("content", URLEncoder.encode(MessageCode.getShotMessageCode(), "utf-8"))//（示例模板：{1}您好，您的订单于{2}已通过{3}发货，运单号{4}）
        };
        postMethod.setRequestBody(data);
        postMethod.setRequestHeader("Connection", "close");

        int statusCode = httpClient.executeMethod(postMethod);
        System.out.println("statusCode: " + statusCode + ", body: "
                + postMethod.getResponseBodyAsString());
    }

    public static void main(String[] args) throws Exception {
        ApiTest t = new ApiTest();
        //普通短信
        t.sendsms();
    }
}