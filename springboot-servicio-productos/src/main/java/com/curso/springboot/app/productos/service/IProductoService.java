package com.curso.springboot.app.productos.service;

import java.util.List;

import com.curso.springboot.app.commons.model.entity.productos.Producto;


/**
 * 
 * @author jfjim
 *
 */
public interface IProductoService {

	List<Producto> findAll();
	Producto findById(Long id);
	Producto save(Producto producto);
	void deleteById(Long id);
}
