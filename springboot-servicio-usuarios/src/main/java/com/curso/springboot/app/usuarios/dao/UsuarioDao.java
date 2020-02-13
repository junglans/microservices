package com.curso.springboot.app.usuarios.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.curso.springboot.app.commons.model.entity.usuarios.Usuario;

@RepositoryRestResource(path = "gestion")
public interface UsuarioDao extends JpaRepository<Usuario , Long> {

	/**
	 * Username es un indice único.
	 * @param username
	 * @return
	 */
	// http://localhost:8090/api/v1.0/usuarios/crud/search/byusername?name=mama
	@RestResource(path = "byusername")
	public Usuario findByUsername(@Param("username") String username);
	@RestResource(path = "getbyusername")
	@Query("SELECT u FROM Usuario u WHERE u.username = ?1")
	public Usuario getUserByUsername(@Param("username")  String username);
	
}
