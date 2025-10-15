package com.artur.mongodb.relacionamentos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
@SpringBootApplication
public class RelacionamentosApplication {

	public static void main(String[] args) {
		SpringApplication.run(RelacionamentosApplication.class, args);
	}

}
