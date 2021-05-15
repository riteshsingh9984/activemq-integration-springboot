package com.example.pubsub.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pubsub.integration.Order;
import com.example.pubsub.integration.OrderSender;

@Service
public class IndexService {

	private static Logger log = LoggerFactory.getLogger(IndexService.class);
	
	@Autowired
    private OrderSender orderSender;
	
	@Autowired
	private ParserSample parserSample;
	
	public void test() throws InterruptedException {
		log.info("Spring Boot Embedded ActiveMQ Configuration Example");

        for (int i = 0; i < 5; i++){
            Order myMessage = new Order(i + " - Sending JMS Message using Embedded activeMQ",
            		new Date());
            orderSender.send(myMessage);
        }

        log.info("Waiting for all ActiveMQ JMS Messages to be consumed");
        TimeUnit.SECONDS.sleep(3);
	}
	
	public void orderPublish(Order order) {
		
		Order orderXml = parserSample.getParser();
		
		List<Order> list = parserSample.getExcel();
		
		for(Order selectedOrder: list) {
			
			if(order.getAddress().equals(selectedOrder.getAddress())) {
				Order orderXmlRequest = new Order(orderXml.getContent(),new Date());
				orderXmlRequest.setAmount(selectedOrder.getAmount());
				orderXmlRequest.setAddress(selectedOrder.getAddress());
				orderXmlRequest.setOrderId(UUID.randomUUID().toString());
				
				orderSender.send(orderXmlRequest);
			}
		}
		
	}
}
