package com.curso.springboot.app.sesiones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@EntityScan({"com.curso.springboot.app.commons.model.entity"})
public class SpringbootServicioSesionesApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootServicioSesionesApplication.class, args);
	}

}
