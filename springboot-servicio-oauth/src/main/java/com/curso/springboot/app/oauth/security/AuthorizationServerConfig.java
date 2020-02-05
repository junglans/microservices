package com.curso.springboot.app.oauth.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@RefreshScope
/**
 * This is to set up your token generation end point i.e. 
 * if you provide the properties security.oauth2.client.client-id and 
 * security.oauth2.client.client-secret, Spring will give you an authentication server, 
 * providing standard Oauth2 tokens at the endpoint /oauth/token
 */
@EnableAuthorizationServer
/**
 * 
 *  Their purpose is to provide your custom settings for Authorization Server endpoints,
 *  clients & security. So its up to you as how many you wish to override as there are 
 *  some predefined default settings.
 *
 */
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private Environment env;
	// Estos beans se configuraron en la clase de configuración SpringSecurityConfig
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private AdditionalInfoToken additionalInfo;
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		// tokenKeyAccess es el endpoint para autenticarnos con la ruta oauth/token
		// se encarga de validar las credenciales del cliente y del usuario, con permitAll 
		// indicamos que cualquier cliente puede acceder.
		// checkTokenAccess es la ruta para validar los tokens
		security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()"); 
		
				
	}

	@Override
	/**
	 * Registramos los clientes que van a accder los recursos que están protegidos.
	 */
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		// Lo hacemos en memoria pero podria ser también jdbc u otro tipo de almacenamiento.
		clients.inMemory().withClient(env.getProperty("config.security.oauth.client.registration.FrontEndApp1.client_id")) // client-id
						  .secret(passwordEncoder.encode(env.getProperty("config.security.oauth.client.registration.FrontEndApp1.client_secret"))) // client-secret
						  .scopes("read", "write")
						  // el grant type se refiere a cómo vamos a obtener el token.
						   // aquí con "password". 
						  // Los usuarios existen en nuestro sistema de backend. 
						  // Necesitamos el username y la password del usuario.
						  // hay entonces dos conjuntos de credenciales: las del cliente que quiere 
						  // acceder a los recursos y las del usuario propietario de los recursos
						  .authorizedGrantTypes("password", "refresh_token") 
						  .accessTokenValiditySeconds(3600)
						  .refreshTokenValiditySeconds(3600)
						  .and()
						  .withClient(env.getProperty("config.security.oauth.client.registration.FrontEndApp2.client_id"))
						  .secret(passwordEncoder.encode(env.getProperty("config.security.oauth.client.registration.FrontEndApp2.client_secret")))
						  .scopes("read", "write")
						  .authorizedGrantTypes("password", "refresh_token")
						  .accessTokenValiditySeconds(3600)
						  .refreshTokenValiditySeconds(3600);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(additionalInfo, accessTokenConverter()));
		// endpoint que se encarga de generar autenticar y gnenrar el token.
		endpoints.authenticationManager(authenticationManager)
				 .tokenStore(tokenStore())
				 .accessTokenConverter(accessTokenConverter())
				 .tokenEnhancer(tokenEnhancerChain);
				 
	}
	
	@Bean
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
