package com.infosys.appointment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AppointmentMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppointmentMsApplication.class, args);
	}

}
