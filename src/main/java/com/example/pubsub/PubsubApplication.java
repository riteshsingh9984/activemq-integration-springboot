package com.example.pubsub;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.pubsub.service.ParserSample;

@SpringBootApplication
public class PubsubApplication {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		SpringApplication.run(PubsubApplication.class, args);
	}

}
