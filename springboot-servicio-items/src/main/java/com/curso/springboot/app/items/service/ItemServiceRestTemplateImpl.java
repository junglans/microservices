package com.curso.springboot.app.items.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.curso.springboot.app.items.model.Item;
import com.curso.springboot.app.items.model.Producto;

@Service(value = "ProductRestTemplateClient")
public class ItemServiceRestTemplateImpl implements IItemService {
	
	@Autowired
	private RestTemplate clienteRest;

	@Override
	public List<Item> findAll() {
		
		List<Producto> productos = Arrays.asList(clienteRest.getForObject("http://servicio-productos/listar", Producto[].class));
		return productos.stream().map (	
				(prod) -> {  
							return new Item(prod, 1);
					}
		).collect(Collectors.toList());
		
	}

	@Override
	public Item findByIdAndCantidad(Long id, Integer cantidad) {
		
		Map<String,String> uriParams = new HashMap<String, String>();
		uriParams.put("id", id.toString());
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.ACCEPT, "application/json");
		
		HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
		ResponseEntity<Producto> response = clienteRest.exchange("http://servicio-productos/detalle/{id}", HttpMethod.GET, requestEntity, Producto.class, uriParams);
		return new Item(response.getBody(), cantidad);
	}

}
