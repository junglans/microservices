package com.curso.springboot.app.productos.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.curso.springboot.app.productos.model.entity.Producto;
import com.curso.springboot.app.productos.service.IProductoService;

@RestController
public class ProductoController {
	
	//@Autowired
	//private Environment env;
	
	@Value("${producto.server.instance}")
	private Integer instancia;
	
	@Autowired
	private IProductoService service;
	
	@RequestMapping(method = RequestMethod.GET, value="listar" )
	public @ResponseBody List<Producto> findAll() {
		return service.findAll().stream().map( (producto) -> {
			//producto.setPort(Integer.parseInt(env.getProperty("local.server.port")));
			producto.setInstancia(instancia);
			return producto;
		}).collect(Collectors.toList());
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "detalle/{id}",  produces = { "application/json", "application/xml" })
	public  @ResponseBody Producto findById(@PathVariable Long id) {
		  
		if (instancia.equals(1000)) {
			throw new RuntimeException("Error en instancia: " + this.instancia);
		}
		Producto producto = service.findById(id);
		//producto.setPort(Integer.parseInt(env.getProperty("local.server.port")));
		producto.setInstancia(instancia);
		
//		try {
//			Thread.sleep(5000L);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		return producto;
	}

	@RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value="*", produces = "application/xml")
	public Producto fallBack() {
		return new Producto();
	}
}
