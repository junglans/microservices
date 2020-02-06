package com.curso.springboot.app.oauth.security.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.curso.springboot.app.commons.model.entity.Usuario;
import com.curso.springboot.app.oauth.services.IUsuarioService;

import feign.FeignException;

@Component
public class AuthenticationHandler implements AuthenticationEventPublisher {

	private static Logger LOGGER = LoggerFactory.getLogger(AuthenticationHandler.class);
	
	@Autowired
	private IUsuarioService service;
	
	@Override
	public void publishAuthenticationSuccess(Authentication authentication) {
		UserDetails user = (UserDetails) authentication.getPrincipal();
		LOGGER.info("Successful Authentication for user :" + user.getUsername());
		
		try {
			Usuario usuario = service.getUserByUsername(user.getUsername());
			usuario.setIntentos(0);
			service.update(usuario, usuario.getId());
		} catch (FeignException e) {
			e.printStackTrace();
			LOGGER.error(String.format("El usuario % no existe en el sistema.", user.getUsername()));
		}
	}

	@Override
	public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
		String username = (String) authentication.getPrincipal();
		try {
			Usuario usuario = service.getUserByUsername(username);
			if (usuario.getIntentos() == null) {
				usuario.setIntentos(0);
			}
			
			usuario.setIntentos(usuario.getIntentos() + 1);
			if (usuario.getIntentos() == 3) {
				LOGGER.warn(String.format("El usuario %s ha sido deshabilitado por exceso de intentos.", username));
				usuario.setEnabled(Boolean.FALSE);
			}
			
			service.update(usuario, usuario.getId());
			
		} catch (FeignException e) {
			e.printStackTrace();
			LOGGER.error(String.format("El usuario % no existe en el sistema.", username));
		}
		
		
		LOGGER.error("Error Authentication for user :" + username);
		LOGGER.error("Error: " + exception.getLocalizedMessage());

	}

}
