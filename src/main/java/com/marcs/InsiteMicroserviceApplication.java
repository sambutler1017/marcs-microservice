package com.marcs;

import java.security.NoSuchAlgorithmException;

import com.marcs.service.activeProfile.ActiveProfile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class InsiteMicroserviceApplication {

	public static void main(String[] args) throws NoSuchAlgorithmException {
		ActiveProfile activeProfile = new ActiveProfile();
		activeProfile.setPropertyFile();

		SpringApplication.run(InsiteMicroserviceApplication.class, args);
	}
}
