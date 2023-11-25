package com.parsla.PeopleHub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class PeopleHubApplication {

	public static void main(String[] args) {
		SpringApplication.run(PeopleHubApplication.class, args);
	}

}
