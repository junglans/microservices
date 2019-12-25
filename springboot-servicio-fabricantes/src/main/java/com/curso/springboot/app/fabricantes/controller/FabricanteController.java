package com.curso.springboot.app.fabricantes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.curso.springboot.app.fabricantes.model.entity.Fabricante;
import com.curso.springboot.app.fabricantes.service.FabricanteService;

@RestController
public class FabricanteController {
	@Autowired
	private FabricanteService service;
	
	@RequestMapping(method = RequestMethod.GET, value = "listar")
	public @ResponseBody List<Fabricante> findAll() {
		return service.findAll();
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "detalle/{id}")
	public @ResponseBody Fabricante findByid(@PathVariable Long id) {
		return service.findById(id);
	}

}
