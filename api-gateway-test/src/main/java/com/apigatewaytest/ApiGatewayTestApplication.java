package com.apigatewaytest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayTestApplication.class, args);
	}

}
