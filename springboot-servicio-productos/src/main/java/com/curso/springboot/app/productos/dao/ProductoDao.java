package com.curso.springboot.app.productos.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.curso.springboot.app.commons.model.entity.productos.Producto;

public interface ProductoDao extends JpaRepository<Producto, Long> {}
