package com.curso.springboot.app.oauth.services;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.curso.springboot.app.commons.model.entity.Rol;
import com.curso.springboot.app.commons.model.entity.Usuario;
import com.curso.springboot.app.oauth.clients.UsuarioFeignClient;

import feign.FeignException;

@Service
public class UsuarioService implements IUsuarioService, UserDetailsService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UsuarioService.class);
	@Autowired
	private UsuarioFeignClient client;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = client.findByUsername(username);
		if (usuario == null) {
			LOGGER.error("Error en la autenticación. No existe el usuario " + username + " en el sistema");
			throw new UsernameNotFoundException("Error en la autenticación. No existe el usuario " + username + " en el sistema");
		}
		List<GrantedAuthority> authorities = usuario.getRoles()
				.stream()
				.map( (Rol rol) -> {
						return new SimpleGrantedAuthority(rol.getNombre()); 
					  }
				)
				.peek(authority -> LOGGER.info("Rol: " + authority.getAuthority()))
				.collect(Collectors.toList());
		
		return new User(usuario.getUsername(), usuario.getPassword(), usuario.isEnabled(), true, true , true, authorities);
	}
	@Override
	public Usuario getUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = null;
		try {
			usuario = client.findByUsername(username);
		} catch (FeignException e) {
			LOGGER.error("Error en la autenticación. No existe el usuario " + username + " en el sistema");
			throw new UsernameNotFoundException("Error en la autenticación. No existe el usuario " + username + " en el sistema");
		}

		return usuario;
	}
	@Override
	public Usuario update(Usuario usuario, Long id) {
		return client.update(usuario, id);
	}

}
