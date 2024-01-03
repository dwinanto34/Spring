package com.app.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// By default, Spring Boot scans the package of the main application class
// (the class annotated with @SpringBootApplication) and its sub-packages for components.

// Since the previous code has been moved to the com.app.springcore package,
// we need to explicitly specify additional packages to be scanned using scanBasePackages.
@SpringBootApplication(
	scanBasePackages = {
		"com.app.spring",
		"com.app.springcore",
		"com.app.beanvalidation"
	}
)

public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
