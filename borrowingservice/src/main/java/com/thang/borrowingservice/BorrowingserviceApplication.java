package com.thang.borrowingservice;

import com.thang.borrowingservice.config.AxonConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.messaging.MessageChannel;

@SpringBootApplication
@EnableDiscoveryClient
@Import({AxonConfig.class})
public class BorrowingserviceApplication {
	@Bean
	public MessageChannel borrowOutputChannel() {
		return MessageChannels.direct().get();
	}

	public static void main(String[] args) {
		SpringApplication.run(BorrowingserviceApplication.class, args);
	}

}
