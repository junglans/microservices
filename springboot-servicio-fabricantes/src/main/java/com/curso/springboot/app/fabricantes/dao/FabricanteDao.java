package com.curso.springboot.app.fabricantes.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.curso.springboot.app.fabricantes.model.entity.Fabricante;

public interface FabricanteDao extends JpaRepository<Fabricante, Long> {

}
