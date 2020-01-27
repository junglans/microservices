package com.curso.springboot.app.oauth.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.curso.springboot.app.commons.model.entity.Usuario;

@FeignClient(name = "servicio-usuarios")
public interface UsuarioFeignClient {

	@RequestMapping( method = RequestMethod.GET , value = "usuario/search/byusername")
	public Usuario findByUsername( @RequestParam String username);
}
