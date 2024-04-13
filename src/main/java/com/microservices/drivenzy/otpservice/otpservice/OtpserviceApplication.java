package com.microservices.drivenzy.otpservice.otpservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class OtpserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OtpserviceApplication.class, args);
	}

}
