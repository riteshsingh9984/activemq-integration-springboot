package com.example.pubsub.integration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.example.pubsub.service.ParserSample;

import static com.example.pubsub.integration.ActiveMQConfig.ORDER_QUEUE;

@Service
public class OrderSender {

    private static Logger log = LoggerFactory.getLogger(OrderSender.class);

    @Autowired
    private JmsTemplate jmsTemplate;
    
    @Autowired
    private ParserSample parserSample;

    public void send(Order myMessage) {
        log.info("sending with convertAndSend() to queue <" + myMessage + ">");
        
        jmsTemplate.convertAndSend(ORDER_QUEUE, parserSample.jaxbObjectToXML(myMessage));
    }
}