package com.youzhi.controller;

import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfplugin.mail.MailKit;
import com.youzhi.ActiveMQTool.first.TextConsumer;
import com.youzhi.ActiveMQTool.first.TextProducer;
import com.youzhi.model.Mail;
import org.apache.activemq.Message;
import org.apache.activemq.command.ActiveMQObjectMessage;

import javax.jms.*;

@Clear
public class MailController extends Controller {
    //    public void testMail() throws EmailException {
//        Mailer.sendText("发送邮件", "hello", "1771414740@qq.com");
//
//    }
    public void testSendMail() {
        MailKit.send("1771414740@qq.com", null, "测试", "success!!!");
    }

    public void index() {
        renderText("success");
    }

    /*public void sendMail(){
        String to = getPara("to");
        String cc = getPara("cc");
        String subject = getPara("subject");
        String text = getPara("text");
        MailKit.send(to,null,subject,text);
        renderText("success");
    }*/
    public void sendMail() {
        Mail mail = new Mail();
        mail.setTo(getPara("to"));

        mail.setSubject(getPara("subject"));
        mail.setText(getPara("text"));
        TextProducer textProducer = new TextProducer();
        TextConsumer consumer = new TextConsumer();
//        MailKit.send(mail.getTo(), null, mail.getSubject(), mail.getText());
        MailKit.send(mail.getTo(), null, mail.getSubject(), mail.getText());
        renderText("success");
    }

    public static void main(String[] args) throws JMSException {
        Mail mail = new Mail();
        mail.setTo("1771414740@qq.com");
        mail.setSubject("ceshi");
        mail.setText("45741852963");
//        System.out.println(mail.toString());
        TextProducer textProducer = new TextProducer();

        textProducer.sendTextMessage(mail);
        TextConsumer consumer = new TextConsumer();
        Mail mail1 = consumer.receiveTextMessage();
        System.out.println(mail1);
    }
}
