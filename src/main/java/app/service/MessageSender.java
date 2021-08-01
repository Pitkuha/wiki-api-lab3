package app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.TextMessage;
import java.util.UUID;

@Service
public class MessageSender {
    @Autowired
    ConnectionFactory connectionFactory;

    @Scheduled(fixedRate = 1000)
    public void rpcWithSpringJmsTemplate() throws JMSException {
        Connection clientConnection = connectionFactory.createConnection();
        clientConnection.start();
        String messageContent = UUID.randomUUID().toString();

        JmsTemplate tpl = new JmsTemplate(connectionFactory);
        tpl.setReceiveTimeout(2000);
        tpl.send("demoqueue", session -> {
            TextMessage message = session.createTextMessage(messageContent);
            message.setJMSCorrelationID(messageContent);
            return message;
        });
    }

    /*
    TODO пример JMS producer описан и подключен к RabbitMQ
    TODO Придумать какие сообщения и на какие узлы буду отправляться, реализовать
    TODO Реализовать Listener на базе Spring Boot (@JMSListener)
     */
}
