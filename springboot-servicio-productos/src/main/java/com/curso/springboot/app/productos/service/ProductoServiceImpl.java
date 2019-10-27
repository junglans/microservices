package com.curso.springboot.app.productos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.curso.springboot.app.productos.dao.ProductoDao;
import com.curso.springboot.app.productos.model.entity.Producto;

@Service
public class ProductoServiceImpl implements IProductoService {

	@Autowired
	private ProductoDao dao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Producto> findAll() {
		return dao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Producto findById(Long id) {
		return dao.findById(id).orElse(null);
	}

}
