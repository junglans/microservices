package com.curso.springboot.app.items.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.curso.springboot.app.commons.model.entity.productos.Producto;
import com.curso.springboot.app.items.model.Item;

import com.curso.springboot.app.items.service.IItemService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

// Esta anotación permite refrescar el componente con los cambios de la configuración en tiempo real. 
// Se necesita la dependencia Actuator de SpringBoot
@RefreshScope
@RestController
public class ItemController {

	@Autowired
	private Environment env;

	@Autowired
	@Qualifier("ProductFeignClient")
	//@Qualifier("ProductRestTemplateClient")
	private IItemService service;

	@Value("${configuracion.descripcion}")
	private String texto;

	@RequestMapping(method = RequestMethod.GET, value = "/")

	/*
	 * ResponseEntity represents an HTTP response, including headers, body, and
	 * status. While @ResponseBody puts the return value into the body of the
	 * response, ResponseEntity also allows us to add headers and status code.
	 */
	public ResponseEntity<List<Item>> findAll() {
		return new ResponseEntity<List<Item>>(service.findAll(), HttpStatus.OK);
	}

	@HystrixCommand(fallbackMethod = "findByIdAndCantidadCB")
	@RequestMapping(method = RequestMethod.GET, value = "/{id}/cantidad/{cantidad}", produces = "application/json;charsert=utf8")
	public ResponseEntity<Item> findByIdAndCantidad(@PathVariable Long id, @PathVariable Integer cantidad) {
		return new ResponseEntity<Item>(service.findByIdAndCantidad(id, cantidad), HttpStatus.OK);
	}

	public ResponseEntity<Item> findByIdAndCantidadCB(Long id, Integer cantidad) {
		Producto producto = new Producto();
		producto.setId(id);
		producto.setNombre("Invalido");

		Item item = new Item(producto, cantidad);
		return new ResponseEntity<Item>(item, HttpStatus.OK);

	}

	@RequestMapping(method = RequestMethod.POST, value = "productos", produces = "application/json;charsert=utf8")
	public ResponseEntity<Producto> save(@RequestBody Producto producto) {
		Producto prod = service.save(producto);
		return new ResponseEntity<Producto>(prod, HttpStatus.CREATED);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "productos/{id}", produces = "application/json;charset=utf8")
	public ResponseEntity<Producto> update(@RequestBody Producto producto, @PathVariable Long id) {
		Producto prod = service.update(producto, id);
		return new ResponseEntity<Producto>(prod, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "productos/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
	}
	
	@RequestMapping(value = "configuracion", method = RequestMethod.GET, produces = "application/json;charsert=utf8")
	public ResponseEntity<Map<String, String>> getConfiguracion(@Value("${server.port}") Integer puerto) {
		Map<String, String> json = new HashMap<String, String>();
		json.put("texto", texto);
		json.put("puerto", puerto.toString());

		json.put("autor.nombre", env.getProperty("configuracion.autor.nombre"));
		json.put("autor.email", env.getProperty("configuracion.autor.email"));

		return new ResponseEntity<>(json, HttpStatus.OK);
	}
}
