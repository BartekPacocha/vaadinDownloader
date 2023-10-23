package com.example.cdaVaadin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CdaVaadinApplication {

	public static void main(String[] args) {
		SpringApplication.run(CdaVaadinApplication.class, args);
	}

}
