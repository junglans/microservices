package com.curso.springboot.app.items;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.client.RestTemplate;

@Configuration
//@PropertySources({
//		@PropertySource(value = "application.properties", encoding="UTF-8"),
//		@PropertySource(value = "servicio-items-${env}.properties", encoding="UTF-8")
//})
public class AppItemsConfig {

	@Bean(name = "clienteRest")
	@LoadBalanced
	public RestTemplate registrarRestTemplate() {
		return new RestTemplate();
	}
//	@Bean
//	public PropertyPlaceholderConfigurer applicationProperties() {
//		PropertyPlaceholderConfigurer res = new PropertyPlaceholderConfigurer();
//	    res.setFileEncoding("UTF-8");
//	    res.setLocation(new ClassPathResource("application.properties"));
//	    return res;
//	}
}
