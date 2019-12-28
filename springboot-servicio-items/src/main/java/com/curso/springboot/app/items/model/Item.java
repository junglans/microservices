package com.curso.springboot.app.items.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name = "Item")
@XmlAccessorType(XmlAccessType.FIELD)
public class Item implements Serializable {

	private static final long serialVersionUID = 1L;
	@XmlElement
	private Producto producto;
	@XmlElement
	private Integer cantidad;

	public Item() {
	}

	public Item(Producto producto, Integer cantidad) {
		this.producto = producto;
		this.cantidad = cantidad;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	
	public Double getTotal() {
	   return this.producto.getPrecio() * this.cantidad.doubleValue();	
	}

}
