package com.curso.springboot.app.productos.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.curso.springboot.app.commons.model.entity.Producto;
import com.curso.springboot.app.productos.service.IProductoService;

@RestController
public class ProductoController {
	
	//@Autowired
	//private Environment env;
	
	@Value("${producto.server.instance}")
	private Integer instancia;
	
	@Autowired
	private IProductoService service;
	
	@RequestMapping(method = RequestMethod.GET, value="/" )
	public @ResponseBody List<Producto> findAll() {
		return service.findAll().stream().map( (producto) -> {
			//producto.setPort(Integer.parseInt(env.getProperty("local.server.port")));
			producto.setInstancia(instancia);
			return producto;
		}).collect(Collectors.toList());
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}",  produces = { "application/json", "application/xml" })
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

	@RequestMapping(value = "/", method = RequestMethod.POST, produces = "application/json;charsert=utf8")
	public ResponseEntity<Producto> save(@RequestBody Producto producto) {
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json;charset=utf8");
		Producto p = service.save(producto);
		return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(p);
		
	}
	
	@RequestMapping(value="/{id}",  method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable Long id) {
		this.service.deleteById(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json;charset=utf8")
	public ResponseEntity<Producto> update(@PathVariable Long id, @RequestBody Producto producto) {
		
		Producto p = service.findById(id);
		if ( p != null) {
			p.setNombre(producto.getNombre());
			p.setPrecio(producto.getPrecio());
			service.save(p);
			return ResponseEntity.status(HttpStatus.OK).body(p);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		
	}
	
	@RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value="*", produces = "application/xml")
	public Producto fallBack() {
		return new Producto();
	}
}
