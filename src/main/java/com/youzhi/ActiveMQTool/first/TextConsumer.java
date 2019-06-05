package com.youzhi.ActiveMQTool.first;


import com.youzhi.model.Mail;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQObjectMessage;

import javax.jms.*;

public class TextConsumer {
    public Mail receiveTextMessage() {
        String resultCode = "";
        ConnectionFactory factory = null;
        Connection connection = null;
        Session session = null;
        Destination destination = null;
        //消息的消费者，用于接收消息的对象。
        MessageConsumer consumer = null;
        Message message = null;
        ActiveMQObjectMessage objectMail = null;
        Mail mail = null;
        try {
            factory = new ActiveMQConnectionFactory();
            connection = factory.createConnection();
            //消息的消费者必须启动连续，否则无法处理消息
            connection.start();
            session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
            destination = session.createQueue("first-mq");
            //创建消费者对象，在指定的目的地获取消息
            consumer = session.createConsumer(destination);
            //获取队列中的消息,receive方法是一个主动获取消息的方法。执行一次，拉取一个消息，开发少用。
            message = consumer.receive();
            objectMail = (ActiveMQObjectMessage) message;
            mail = (Mail) objectMail.getObject();
            //确认消息，发送处理消息确认消息，通知MQ删除对应的消息。
            message.acknowledge();



            //处理文本消息
//            resultCode = ((TextMessage) message).getText();

            //System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (consumer != null) {//回收消息发送者
                try {
                    consumer.close();
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
        return mail;
    }

    public static void main(String[] args) {
        TextConsumer consumer = new TextConsumer();
//        String messageString = consumer.receiveTextMessage();
//        System.out.println("消息内容是：" + messageString);
    }
}
