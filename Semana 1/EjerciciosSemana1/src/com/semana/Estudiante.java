package com.semana;

import java.util.ArrayList;

public class Estudiante {
	 private String nombre;
	    private String matricula;
	    private ArrayList<Double> calificaciones;

	    // TODO: constructor
	    public Estudiante(String nombre, String matricula) {
	        this.nombre = nombre;
	        this.matricula = matricula;
	        this.calificaciones = new ArrayList<>();}
	        
	    

	    // TODO: getters
	    public String getNombre() { return nombre; }
	    public String getMatricula() { return matricula; }

	    // TODO: agregar calificacion con validacion
	    public void agregarCalificacion(double cal) {
	        if (cal >= 0 && cal <= 100) {  // validar rango 0-100
	            calificaciones.add(cal);
	        } else {
	            System.out.println("Calificacion invalida: " + cal);
	        }
	    }

	    // TODO: calcular promedio
	    public double calcularPromedio() {
	        if (calificaciones.isEmpty()) return 0.0;
	        double suma = 0;
	        for (double s : calificaciones) {
	        	suma += s;
	        }
	        
	        // TODO: sumar todas las calificaciones
	        return suma / calificaciones.size();
	    }

	    @Override
	    public String toString() {
	        return String.format(
	            "Estudiante{nombre='%s', matricula='%s', promedio=%.2f}",
	            nombre, matricula, calcularPromedio());
	    }

	    public static void main(String[] args) {
	        Estudiante e1 = new Estudiante("Ana Garcia", "A001");
	        Estudiante e2 = new Estudiante("Carlos Lopez", "A002");
	        Estudiante e3 = new Estudiante("Maria Torres", "A003");

	        e1.agregarCalificacion(95);
	        e1.agregarCalificacion(88);
	        e1.agregarCalificacion(92);

	        e2.agregarCalificacion(78);
	        e2.agregarCalificacion(85);
	        e2.agregarCalificacion(90);

	        e3.agregarCalificacion(100);
	        e3.agregarCalificacion(96);
	        e3.agregarCalificacion(98);

	        ArrayList<Estudiante> lista = new ArrayList<>();
	        lista.add(e1); lista.add(e2); lista.add(e3);

	        System.out.println("=== Lista de Estudiantes ===");
	        Estudiante mejor = lista.get(0);
	        for (Estudiante e : lista) {
	            System.out.println(e);
	            // TODO: actualizar 'mejor' si e tiene mayor promedio
	          if(e.calcularPromedio() > mejor.calcularPromedio()){
	        	  mejor=e;
	          }
	        }
	        System.out.println("\nMejor promedio: " + mejor.getNombre()
	            + " (" + String.format("%.2f", mejor.calcularPromedio())
	            + ")");
	    }

}
