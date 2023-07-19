package com.example.employee;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.nio.file.Files;
import java.nio.file.Path;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EnableScheduling
@EnableCaching
@Slf4j
public class EmployeeApplication implements CommandLineRunner {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(EmployeeApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Path directoryPath = Files.createDirectories(Path.of("uploads"));
        log.info("******** Directory Created :: " + directoryPath.toUri());
    }

}
