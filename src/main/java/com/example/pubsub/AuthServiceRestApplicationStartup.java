package com.example.pubsub;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.example.pubsub.service.ParserSample;


/**
 * @author RITESH SINGH
 * @since JDK 1.8
 * @version 1.0
 *
 */
@Component
public class AuthServiceRestApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

  @Override
  public void onApplicationEvent(final ApplicationReadyEvent event) {

    ParserSample sub = new ParserSample();
	
    System.out.println(sub.getParser());
    System.out.println(sub.getExcel());
    return;
  }

}
