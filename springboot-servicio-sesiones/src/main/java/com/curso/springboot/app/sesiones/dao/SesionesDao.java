package com.curso.springboot.app.sesiones.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.PathVariable;

import com.curso.springboot.app.commons.model.entity.SesionLogin;
import com.curso.springboot.app.commons.model.entity.Usuario;


@RepositoryRestResource(path = "sesion")
public interface SesionesDao extends JpaRepository<SesionLogin, Long> {

	
	@RestResource()
	/**
	 * Lista todos los registros de sesión de un usuario
	 * @param username
	 * @return
	 */
	List<SesionLogin> findByUsuarioUsername(@Param("username") String username);
	
	

}
