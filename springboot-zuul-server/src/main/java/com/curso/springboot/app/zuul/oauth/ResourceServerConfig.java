package com.curso.springboot.app.zuul.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@RefreshScope
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Autowired
	private Environment env;
	
	@Override
	// Para el token
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.tokenStore(tokenStore());
	}

	@Override
	// Para rutas
	public void configure(HttpSecurity http) throws Exception {
		 
		http.authorizeRequests().antMatchers("/api/security/oauth/**").permitAll()
								.antMatchers(HttpMethod.GET, "/api/usuarios/gestion", "/api/usuarios/gestion/{id}", "/api/productos", 
										                     "/api/productos/{id}", "/api/items", "/api/items/{id}/cantidad/{cantidad}", 
										                     "/api/fabricantes", "/api/fabricantes/{id}").permitAll()
								.antMatchers( "/api/productos/**",  "/api/items/**", "/api/usuarios/gestion/**").hasRole("ADMIN")
								.anyRequest().authenticated();
								
								
	}
	/**
	 * genera los tokens con los datos de JwtAccessTokenConverter
	 */
	public JwtTokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}
	
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
		tokenConverter.setSigningKey(env.getProperty("config.security.oauth.jwt.key"));
		return tokenConverter;
	}
}