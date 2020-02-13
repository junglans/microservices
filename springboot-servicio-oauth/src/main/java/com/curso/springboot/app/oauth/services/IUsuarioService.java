package com.curso.springboot.app.oauth.services;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.curso.springboot.app.commons.model.entity.usuarios.Usuario;

public interface IUsuarioService {
	Usuario findUserByUsername(String username) throws UsernameNotFoundException;
	Usuario update(Usuario usuario, Long id);
}
