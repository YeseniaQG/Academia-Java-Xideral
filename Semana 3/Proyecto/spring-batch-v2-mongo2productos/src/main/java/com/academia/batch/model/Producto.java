package com.academia.batch.model;

import java.math.BigDecimal;

// Modelo usado en el Step 1: representa un empleado leido del CSV
public class Producto {

    private String nombreProducto;
    private String marca;
	private Integer id;
    private BigDecimal precio;
    private Integer quantity;
    private BigDecimal descuento;
    
    public String getMarca() {
  		return marca;
  	}
  	public void setMarca(String marca) {
  		this.marca = marca;
  	}
	public String getNombreProducto() {
		return nombreProducto;
	}
	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public BigDecimal getPrecio() {
		return precio;
	}
	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public BigDecimal getDescuento() {
		return descuento;
	}
	public void setDescuento(BigDecimal descuento) {
		this.descuento = descuento;
	}
	@Override
	public String toString() {
		return "Producto [nombreProducto=" + nombreProducto + ", marca=" + marca + ", sku=" + id + ", precio=" + precio
				+ ", quantity=" + quantity + ", descuento=" + descuento + "]";
	}
}