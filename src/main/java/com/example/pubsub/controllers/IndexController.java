package com.example.pubsub.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.pubsub.integration.Order;
import com.example.pubsub.service.IndexService;

@RestController
public class IndexController {

	private static Logger log = LoggerFactory.getLogger(IndexController.class);
	
	@Autowired
    private IndexService indexService;

	
	@GetMapping("/index")
	public String test() throws InterruptedException {
		
		indexService.test();
		
		return "It's working..!";
	}
	
	@PostMapping("/index/publish")
	public String publishTopic(@RequestBody Order order) {
		
		indexService.orderPublish(order);
		return "published";
	}
}
