package com.project.compagnoserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
public class CompagnoServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CompagnoServerApplication.class, args);
	}

}
