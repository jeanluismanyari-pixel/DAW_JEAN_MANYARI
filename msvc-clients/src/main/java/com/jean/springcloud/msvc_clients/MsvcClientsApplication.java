package com.jean.springcloud.msvc_clients;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsvcClientsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcClientsApplication.class, args);
	}

}
