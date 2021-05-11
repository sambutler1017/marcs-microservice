package com.marcs;

import com.marcs.service.activeProfile.ActiveProfile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InsiteMicroserviceApplication {

	public static void main(String[] args) {
		ActiveProfile activeProfile = new ActiveProfile();
        activeProfile.setPropertyFile();
		
		SpringApplication.run(InsiteMicroserviceApplication.class, args);
	}
}
