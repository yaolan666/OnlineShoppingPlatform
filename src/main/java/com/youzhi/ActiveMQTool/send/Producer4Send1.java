package com.youzhi.ActiveMQTool.send;/*
package com.sxt.send;


import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.Serializable;

public class Producer4Send1 {
    private ConnectionFactory factory = null;
    private Connection connection = null;
    private Session session = null;
    private Destination destination = null;
    private MessageProducer producer = null;
    private Message message = null;

    */
/**
     * 直接发送消息到默认目的地，默认目的地是test-send
     *
     * @param obj
     *//*

    public void sendMessage(Serializable obj) {
        try {
            this.init("test-send");
            message = session.createObjectMessage(obj);
            producer.send(message);
            System.out.println("sendMessage method run");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    */
/**
     * 指定目的地发送消息
     *
     * @param obj             消息内容
     * @param destinationName 目的地
     *//*

    public void sendMessage4Destination(Serializable obj, String destinationName) {
        try {
            this.init();
            message = session.createObjectMessage(obj);
            //创建临时目的地
            Destination destination = session.createQueue(destinationName);
            producer.send(destination, message);
            System.out.println("sendMessage4Destination method run");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    */
/**
     * 多参数发送消息
     * void send(Message message，int deliveryMode,int priority,long timeToLive)
     * deliveryMode - 持久化模式。
     * DeliveryMode.PERSISTENT - 持久化 消息会持久化到数据库中（kahadb ，JDBC等）
     * DeliveryMode.NON_PERSISTENT - 不持久化 消息支保存到内存中。
     * priority - 优先级，0-9取值范围，取值越大优先级越高，不能保证绝对顺序。
     * 必须在activemq.xml配置文件的broker标签中增加配置
     * timeToLive - 消息有效期。单位毫秒。有效期限超时，消息自动放弃。
     *
     * @throws InitJMSException
     *//*

    public void sendMessageWithParameters(Serializable obj, int deliveryMode, int priority, long timeToLive) {
        try {
            this.init("test-send-params");
            message = session.createObjectMessage(obj);
            producer.send(message, deliveryMode, priority, timeToLive);
            System.out.println("sendMessageWithParameters method run");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Producer4Send1() throws InitJMSException {

    }

    private void init() throws InitJMSException {
        this.init(null);
    }

    private void init(String destinationName) throws InitJMSException {
        this.init("admin", "admin", "tcp://127.0.0.1:8161", destinationName);
    }

    private void init(String userName, String password, String brokerURL, String destinationName) throws InitJMSExecption {
        try {
            factory = new ActiveMQConnectionFactory(userName, password, brokerURL);
            connection = factory.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            if (null != destinationName) {
                destination = session.createQueue(destinationName);
                producer = session.createProducer(destination);
            } else {
                producer = session.createProducer(null);
            }
            connection.start();
        } catch (JMSException e) {
            e.printStackTrace();
//            throw new InitJMSException();
            throw new InitJMSException(e);
        }
    }

    public void destory() {
        this.release();
    }

    private void release() {
        if (producer != null) {
            try {
                producer.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
        if (session != null) {
            try {
                session.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args) {
        Producer4Send1 producer = null;
        try {
            producer = new Producer4Send1();
            producer.sendMessage("send Message");
            producer.sendMessage4Destination("send message for destination", "test-send-des");
//            producer.sendMessageWithParameters("send message for parameters",DeliveryMode.PERSISTENT,0,1000*10);
            */
/*
             producer.sendMessageWithParameters("2","DeliveryMode.PERSISTENT,2,0);
             producer.sendMessageWithParameters("9","DeliveryMode.PERSISTENT,9,0);
             producer.sendMessageWithParameters("5","DeliveryMode.PERSISTENT,5,0);
             producer.sendMessageWithParameters("7","DeliveryMode.PERSISTENT,7,0);

             *//*

            producer.destory();
        } catch (InitJMSException e) {
            e.printStackTrace();
        }
    }
}*/
