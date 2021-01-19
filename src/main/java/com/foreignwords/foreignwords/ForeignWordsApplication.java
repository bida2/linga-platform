package com.foreignwords.foreignwords;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.foreignwords.foreignwords.repositories")
public class ForeignWordsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ForeignWordsApplication.class, args);
	}

}
