package com.curso.springboot.app.items.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.curso.springboot.app.items.model.Item;
import com.curso.springboot.app.items.model.Producto;
import com.curso.springboot.app.items.service.IItemService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.ribbon.proxy.annotation.Hystrix;

@RestController
public class ItemController {

	@Autowired
	@Qualifier("ProductFeignClient")
	//@Qualifier("ProductRestTemplateClient")
	private IItemService service;
	
	@RequestMapping(method = RequestMethod.GET, value = "listar")
	public List<Item> findAll() {
		return service.findAll();
	}
    @HystrixCommand(fallbackMethod = "findByIdAndCantidadCB")
	@RequestMapping(method = RequestMethod.GET, value="detalle/{id}/cantidad/{cantidad}",headers = {"Accept=application/xml"})
	public Item findByIdAndCantidad(@PathVariable Long id, @PathVariable Integer cantidad ) {
		return service.findByIdAndCantidad(id, cantidad);
	}
 
    public Item findByIdAndCantidadCB(  Long id,  Integer cantidad ) {
    	Producto producto = new Producto();
    	producto.setId(id);
    	producto.setNombre("Invalido");
    	
    	Item item = new Item(producto, cantidad);
		return item;
    	
    }
}
