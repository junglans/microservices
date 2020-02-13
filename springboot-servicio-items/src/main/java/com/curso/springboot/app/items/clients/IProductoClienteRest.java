package com.curso.springboot.app.items.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.curso.springboot.app.commons.model.entity.productos.Producto;



@FeignClient(name = "servicio-productos")
public interface IProductoClienteRest {

	@RequestMapping(method = RequestMethod.GET, value="/")
	List<Producto> findAll();
	
	@RequestMapping(method = RequestMethod.GET, value="/{id}")
	Producto findById(@PathVariable Long id);
	
	@RequestMapping(method = RequestMethod.POST, value="/")
	Producto save(@RequestBody Producto producto);
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	Producto update(@RequestBody Producto producto, @PathVariable Long id);
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	void delete(@PathVariable Long id);
};
