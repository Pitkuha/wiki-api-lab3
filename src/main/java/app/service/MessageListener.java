package app.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
public class MessageListener {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @JmsListener(destination = "demoqueue")
    public void getMessage(String content){
        logger.info("Received queue message. Content is " + content);
        System.out.println();
    }
}
