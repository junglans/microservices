package com.curso.springboot.app.fabricantes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.curso.springboot.app.fabricantes.dao.FabricanteDao;
import com.curso.springboot.app.fabricantes.model.entity.Fabricante;

@Service
public class FabricanteServiceImpl implements FabricanteService{
	
	@Autowired
	private FabricanteDao dao;

	@Override
	@Transactional(readOnly = true)
	public List<Fabricante> findAll() {
		return dao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Fabricante findById(Long id) {
		return dao.findById(id).orElse(null);
	}

}
