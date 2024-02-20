package com.eurekaservice.eruekaservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EruekaServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EruekaServiceApplication.class, args);
	}

}
