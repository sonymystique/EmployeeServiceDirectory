package com.example.EmployeeDirectoryService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
@SpringBootApplication
@EnableJpaAuditing
public class EmployeeDirectoryServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(EmployeeDirectoryServiceApplication.class, args);
	}
}
