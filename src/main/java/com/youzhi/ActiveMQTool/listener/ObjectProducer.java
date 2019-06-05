package com.youzhi.ActiveMQTool.listener;


import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.Random;

public class ObjectProducer {
    public void sendMessage(Object obj) {
        ConnectionFactory factory = null;
        Connection connection = null;
        Session session = null;
        Destination destination = null;
        MessageProducer producer = null;
        Message message = null;
        try {
            factory = new ActiveMQConnectionFactory();
            connection = factory.createConnection();
            session = connection.createSession(true, Session.CLIENT_ACKNOWLEDGE);
            destination = session.createQueue("test-listener");
            producer = session.createProducer(destination);
            connection.start();
            Random r = new Random();
            for (int i = 0; i < 10; i++) {
//                Integer data = r.nextInt(100);
                Integer data = i;
                message = session.createObjectMessage(data);
                producer.send(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
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
    }

    public static void main(String[] args) {
        ObjectProducer producer = new ObjectProducer();
        producer.sendMessage(null);
    }
}
