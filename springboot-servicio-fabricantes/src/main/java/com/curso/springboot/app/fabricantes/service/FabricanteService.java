package com.curso.springboot.app.fabricantes.service;

import java.util.List;

import com.curso.springboot.app.fabricantes.model.entity.Fabricante;

public interface FabricanteService {

	List<Fabricante> findAll();
	Fabricante findById(Long id);
}
