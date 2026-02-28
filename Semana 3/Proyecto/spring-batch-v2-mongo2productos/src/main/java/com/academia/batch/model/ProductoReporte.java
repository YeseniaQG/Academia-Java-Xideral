package com.academia.batch.model;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

// Modelo usado en el Step 2: agrega el campo salarioTotal para el reporte
// En esta version se persiste como documento en la coleccion "reportes" de MongoDB
@Document(collection = "reportes")
public class ProductoReporte {

    @Id
    private String id;
    private String nombreProducto;
    private String marca;
    private BigDecimal precio;
    private Integer quantity;
    private BigDecimal descuento;
    private BigDecimal precioReal;
    
	public String getNombreProducto() {
		return nombreProducto;
	}
	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
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
	public BigDecimal getPrecioReal() {
		return precioReal;
	}
	public void setPrecioReal(BigDecimal precioReal) {
		this.precioReal = precioReal;
	}
	@Override
	public String toString() {
		return "ProductoReporte [nombreProducto=" + nombreProducto + ", marca=" + marca + ", id=" + id + ", precio="
				+ precio + ", quantity=" + quantity + ", descuento=" + descuento + ", precioReal=" + precioReal + "]";
	}
    
    
}