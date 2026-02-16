package com.academia.rest.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table (name = "productos")
public class Producto {

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer sku;
	
	@Column (nullable = false, length = 100)
	private String nombreProducto;
	
	@Column(precision = 7, scale = 2)
	private BigDecimal precio;
	
	private Integer quantity;
	
	 @Column(name = "fecha_registro")
	    private LocalDate fechaRegistro;

	public Integer getSku() {
		return sku;
	}

	public void setSku(Integer sku) {
		this.sku = sku;
	}

	public String getNombreProducto() {
		return nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
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
	 
	public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
	
	
	
	
}
