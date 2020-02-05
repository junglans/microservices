package com.curso.springboot.app.usuarios;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

import com.curso.springboot.app.commons.model.entity.Rol;
import com.curso.springboot.app.commons.model.entity.Usuario;

@Configuration
public class RepositoryConfig implements RepositoryRestConfigurer {
/**
 * Con esto conseguimos que los identificadores se envíen en la respuesta.
 */
	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
		config.exposeIdsFor(Rol.class, Usuario.class);
	}

}
