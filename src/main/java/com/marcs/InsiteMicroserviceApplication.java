package com.marcs;

import java.security.NoSuchAlgorithmException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@ServletComponentScan
@EnableScheduling
public class InsiteMicroserviceApplication {

	public static void main(String[] args) throws NoSuchAlgorithmException {
		SpringApplication.run(InsiteMicroserviceApplication.class, args);
	}
}
