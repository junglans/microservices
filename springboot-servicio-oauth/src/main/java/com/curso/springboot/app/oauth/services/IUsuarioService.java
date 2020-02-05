package com.curso.springboot.app.oauth.services;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.curso.springboot.app.commons.model.entity.Usuario;

public interface IUsuarioService {
	Usuario getUserByUsername(String username) throws UsernameNotFoundException;
}
