package com.curso.springboot.app.items.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.curso.springboot.app.items.model.Producto;

@FeignClient(name = "servicio-productos")
public interface IProductoClienteRest {

	@RequestMapping(method = RequestMethod.GET, value="/listar")
	List<Producto> findAll();
	
	@RequestMapping(method = RequestMethod.GET, value="/detalle/{id}")
	Producto findById(@PathVariable Long id);
}
