package com.curso.springboot.app.fabricantes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class SpringbootServicioFabricantesApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootServicioFabricantesApplication.class, args);
	}

}
