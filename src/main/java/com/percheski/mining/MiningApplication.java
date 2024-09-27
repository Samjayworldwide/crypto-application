package com.percheski.mining;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MiningApplication {

	public static void main(String[] args) {
		SpringApplication.run(MiningApplication.class, args);
	}

}
