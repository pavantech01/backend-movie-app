package com.stackroute.emailapp1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
public class Emailapp1Application {

	public static void main(String[] args) {
		SpringApplication.run(Emailapp1Application.class, args);
	}

}
