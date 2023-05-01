package com.thang.employeeserivce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class EmployeeserivceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeserivceApplication.class, args);
	}

}
