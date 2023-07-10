package com.example.employee;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootApplication
@EnableScheduling
@EnableCaching
public class EmployeeApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(EmployeeApplication.class);
		application.setApplicationStartup(new BufferingApplicationStartup(2048));
		application.run(args);
	}

	@Override
	public void run(String... args) throws Exception {
		Path directoryPath = Files.createDirectories(Path.of("uploads"));
		System.out.println("******** Directory Created :: "+directoryPath.toUri());
	}

}
