package com.sujit.reconciliationwebapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.sujit.reconciliationwebapp.repository"})
public class ReconciliationWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReconciliationWebApplication.class, args);
	}

}
