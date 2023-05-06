package com.thang.notificationservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
@EnableBinding(Sink.class)
public class NotificationserviceApplication {

	private Logger logger= LoggerFactory.getLogger(NotificationserviceApplication.class);
//
//	@Autowired
//	private WebClient.Builder webClientBuilder;



	@StreamListener(Sink.INPUT)
	public void consumeMessage(Message message) {
		System.out.println("Message: " + message);
		logger.info("Consume message: " + message.getEmployeeId() + " " + message.getMessage());
	}

	public static void main(String[] args) {
		SpringApplication.run(NotificationserviceApplication.class, args);
	}

}
