package com.youzhi.ActiveMQTool.send;


import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Producer4Send {

    public void sendMessage(String datas, int priority) {
        ConnectionFactory factory = null;
        Connection conn = null;
        Destination destination = null;
        MessageProducer producer = null;
        Session session = null;
        Message message = null;
        try {
            factory = new ActiveMQConnectionFactory("admin", "admin", "tcp://127.0.0.1:61616");
            conn = factory.createConnection();
            conn.start();
            session = conn.createSession(true, Session.CLIENT_ACKNOWLEDGE);
            producer = session.createProducer(null);
            destination = session.createQueue("destination");
            message = session.createTextMessage(datas);
//			producer.send(destination, message, DeliveryMode.PERSISTENT, priority, 60*60*1000);
            producer.send(destination, session.createTextMessage("this is the message to the specified destination with level 2"),
                    DeliveryMode.PERSISTENT, 2, 60 * 60 * 1000);

            producer.send(destination, session.createTextMessage("this is the message to the specified destination with level 9"),
                    DeliveryMode.PERSISTENT, 9, 60 * 60 * 1000);

            producer.send(destination, session.createTextMessage("this is the message to the specified destination with level 5"),
                    DeliveryMode.PERSISTENT, 5, 60 * 60 * 1000);

            producer.send(destination, session.createTextMessage("this is the message to the specified destination with level 7"),
                    DeliveryMode.PERSISTENT, 7, 60 * 60 * 1000);

            session.commit();
            System.out.println("we send the data to " + destination.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (JMSException ex) {
                    ex.printStackTrace();
                }
            }
            if (producer != null) {
                try {
                    producer.close();
                } catch (JMSException ex) {
                    ex.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (JMSException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        Producer4Send producer = new Producer4Send();
        producer.sendMessage("this is the message to the specified destination with level 2", 2);
//		producer.sendMessage("this is the message to the specified destination with level 9", 9);
//		producer.sendMessage("this is the message to the specified destination with level 5", 5);
//		producer.sendMessage("this is the message to the specified destination with level 7", 7);
    }

}


