package com.arranque;

import java.util.ArrayList;

public class HerenciaVehiculos {
    public static void main(String[] args) {
        ArrayList<Vehiculo> flota = new ArrayList<>();
        
        // Polimorfismo: agregamos distintos tipos a una lista de Vehiculo
        flota.add(new Auto("Toyota", "Corolla", 2024, 4));
        flota.add(new Moto("Honda", "CBR600", 2023, 600));
        flota.add(new VehiculoElectrico("Tesla", "Model 3", 2025, 4, 580));
        flota.add(new Auto("Ford", "Mustang", 2024, 2));
        flota.add(new Moto("Yamaha", "MT-07", 2024, 689));

        System.out.println("=== Flota de Vehiculos ===");
        for (Vehiculo v : flota) {
            // Se invoca el toString() de la clase real (Polimorfismo)
            System.out.println(v); 
            v.arrancar();
            v.detener();
            System.out.println();
        }

        // Conteo por tipo usando instanceof
        int autos = 0, motos = 0, electricos = 0;
        for (Vehiculo v : flota) {
            // Importante: preguntar primero por el más específico (Electrico)
            // ya que un VehiculoElectrico también es un Auto.
            if (v instanceof VehiculoElectrico) {
                electricos++;
            } else if (v instanceof Auto) {
                autos++;
            } else if (v instanceof Moto) {
                motos++;
            }
        }
        
        System.out.println("=== Resumen ===");
        System.out.println("Autos: " + autos);
        System.out.println("Motos: " + motos);
        System.out.println("Electricos: " + electricos);
    }}