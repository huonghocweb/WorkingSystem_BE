package com.huong.workingsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class WorkingsystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorkingsystemApplication.class, args);
	}

}
