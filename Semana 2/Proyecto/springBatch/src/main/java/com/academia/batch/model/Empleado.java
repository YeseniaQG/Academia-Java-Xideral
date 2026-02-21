package com.academia.batch.model;

// POJO que representa a un empleado.
// Spring Batch usa los setters para mapear las columnas del CSV a este objeto
// y los getters para escribir los valores en la base de datos.
public class Empleado {

    private String nombre;
    private String departamento;
    private double salario;
    private double bono;

    public Empleado() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public double getBono() {
        return bono;
    }

    public void setBono(double bono) {
        this.bono = bono;
    }

    @Override
    public String toString() {
        return nombre + " | " + departamento + " | Salario: " + salario + " | Bono: " + bono;
    }
}
