package com.accountmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AccountmanagementsystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountmanagementsystemApplication.class, args);
	}

}
