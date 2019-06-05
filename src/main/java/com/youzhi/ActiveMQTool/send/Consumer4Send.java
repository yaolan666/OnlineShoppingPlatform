package com.youzhi.ActiveMQTool.send;


import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Consumer4Send {
    public void consumerMessage() {
        ConnectionFactory factory = null;
        Connection connection = null;
        Session session = null;
        Destination destination = null;
        MessageConsumer consumer = null;
        try {
            factory = new ActiveMQConnectionFactory("admin", "admin", "tcp://127.0.0.1:61616");
            connection = factory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue("destination");
            consumer = session.createConsumer(destination);
            consumer.setMessageListener(new MessageListener() {
                /*
                 *监听器一旦注册，永久有效。
                 * 永久 - consumer线程不关闭。
                 * 处理消息的方式：只要有消息未处理，自动调用onMessage方法，处理消息。
                 * 监听器可以注册若干。注册多个监听器，相当于集群。
                 * ActiveMQ自动的循环调用多个监听器，处理队列中的消息，实现并行处理。
                 * 处理消息的方法，就是监听方法。
                 * 监听的事件是：消息，消息未处理。
                 * @param message - 未处理的消息。
                 */
                @Override
                public void onMessage(Message message) {
                    try {
                        ObjectMessage om = (ObjectMessage) message;
                        Object data = om.getObject();
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            });
            System.in.read();

        } catch (
                Exception e) {
            e.printStackTrace();
        } finally {
            if (consumer != null) {
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
    }

    public static void main(String[] args) {
        Consumer4Send listener = new Consumer4Send();
        listener.consumerMessage();
    }
}