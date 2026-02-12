package com.semana;

public class HolaMundoMejorado {

	public static void main(String[] args) {
        // TODO: Declarar variables
        String nombre = "Yesenia";
        int edad = 25;
        double altura = 1.65;
        boolean esActivo = true;

        // TODO: Concatenacion con +
        String mensaje1 = "Me llamo " + nombre + ", tengo " + edad
                         + " años, mido " + altura + "m y estoy "
                         + (esActivo ? "activo" : "inactivo") + ".";
        System.out.println(mensaje1);

        // TODO: Usando String.format()
        String mensaje2 = String.format(
            "Me llamo %s, tengo %d anios, mido %.2f m y estoy %s.",
            nombre, edad, altura, esActivo ? "activo" : "inactivo"
        );
        System.out.println(mensaje2);

	}

}
