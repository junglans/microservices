package com.curso.springboot.app.items.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.curso.springboot.app.commons.model.entity.Producto;
import com.curso.springboot.app.items.clients.IProductoClienteRest;
import com.curso.springboot.app.items.model.Item;
 

@Service(value = "ProductFeignClient")
public class ItemServiceFeignImpl implements IItemService {

	@Autowired
	private IProductoClienteRest clienteRest;
	
	@Override
	public List<Item> findAll() {
	  
		return clienteRest.findAll().stream().map( 
				(Producto p) -> {  
									return new Item(p, 1); 
				   				} ).collect(Collectors.toList());
	}

	@Override
	public Item findByIdAndCantidad(Long id, Integer cantidad) {
		return new Item( clienteRest.findById(id), cantidad );
	}

	@Override
	public Producto save(Producto producto) {
		 
		return clienteRest.save(producto);
	}

	@Override
	public Producto update(Producto producto, Long id) {
		 return clienteRest.update(producto, id);
	}

	@Override
	public void delete(Long id) {
		  clienteRest.delete(id);
	}

}
