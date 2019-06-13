package com.tvs.iot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@SpringBootApplication
public class IotApplication {

	public static void main(String[] args) {
		SpringApplication.run(IotApplication.class, args);
		System.out.println("Application started...");
	}

}


