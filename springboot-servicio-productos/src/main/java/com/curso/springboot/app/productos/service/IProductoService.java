package com.curso.springboot.app.productos.service;

import java.util.List;

import com.curso.springboot.app.productos.model.entity.Producto;
/**
 * 
 * @author jfjim
 *
 */
public interface IProductoService {

	List<Producto> findAll();
	Producto findById(Long id);
}
