package com.curso.springboot.app.items.service;

import java.util.List;

import com.curso.springboot.app.commons.model.entity.productos.Producto;
import com.curso.springboot.app.items.model.Item;
 

public interface IItemService {

	List<Item> findAll();
	
	Item findByIdAndCantidad(Long id, Integer cantidad);
	
	Producto save(Producto producto);
	
	Producto update(Producto producto, Long id);
	
	void delete(Long id);
}
