package com.curso.springboot.app.oauth.security.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.curso.springboot.app.commons.model.entity.usuarios.Usuario;
import com.curso.springboot.app.oauth.services.IUsuarioService;

import brave.Tracer;
import feign.FeignException;

@Component
public class AuthenticationHandler implements AuthenticationEventPublisher {

	private static Logger LOGGER = LoggerFactory.getLogger(AuthenticationHandler.class);

	@Autowired
	private IUsuarioService service;

	@Autowired
	private Tracer tracer;
	
	@Override
	public void publishAuthenticationSuccess(Authentication authentication) {
		UserDetails user = (UserDetails) authentication.getPrincipal();
		LOGGER.info("Successful Authentication for user :" + user.getUsername());
		// Actualizamos el número de intentos a 0.
		Usuario usuario = service.findUserByUsername(user.getUsername());
		usuario.setIntentos(0);
		service.update(usuario, usuario.getId());
		tracer.currentSpan().tag("login.successfull.mensaje", "OK");

	}

	@Override
	public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
		String username = (String) authentication.getPrincipal();
		StringBuilder errors = new StringBuilder();
		errors.append(exception.getMessage() + "\n");
		try {
			
			Usuario usuario = service.findUserByUsername(username);
			if ( usuario != null ) {
				if (usuario.getIntentos() == null) {
					usuario.setIntentos(0);
				}
				
				usuario.setIntentos(usuario.getIntentos() + 1);
				errors.append("Número de intentos de login: " + usuario.getIntentos());
				if (usuario.getIntentos() == 3) {
					String err = String.format("El usuario %s ha sido deshabilitado por exceso de intentos.", username);
					errors.append(err);
					LOGGER.warn(err);
					usuario.setEnabled(Boolean.FALSE);
				}

				service.update(usuario, usuario.getId());
			}

		} catch (FeignException e) {
			e.printStackTrace();
			String err = String.format("El usuario % no existe en el sistema.", username);
			errors.append(err);
			LOGGER.error(err);

		}

		LOGGER.error("Error Authentication for user :" + username);
		LOGGER.error("Error: " + exception.getLocalizedMessage());
		tracer.currentSpan().tag("error.mensaje", errors.toString());
	}

}
