package com.semana;

public class ManipuladorStrings {

    public static String invertir(String s) {
        // TODO: usar StringBuilder.reverse()
        return new StringBuilder(s).reverse().toString();
    }

    public static boolean esPalindromo(String s) {
        // TODO: limpiar (toLowerCase, replaceAll espacios)
    	String wordClean = s.toLowerCase().replaceAll("\\s+", "");
    	
        // TODO: comparar con su version invertida
    		String reverseWord = invertir(wordClean); 
    	
    		return reverseWord.equals(wordClean);
    }

    public static int contarVocales(String s) {
        int count = 0;
        String vocales = "aeiouAEIOU";
       
       // TODO: recorrer cada caracter, verificar si es vocal
        for (int i = 0; i < s.length(); i++) {
        	char letter = s.charAt(i);
        	if (vocales.contains(String.valueOf(letter)))// letter es cada una de las letras que eran char y las convierte en string 
        		{
        	count++;
        }}
        return count;
    }

    public static String construirPiramide(int niveles) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= niveles; i++) {
            // TODO: agregar espacios (niveles - i)
        sb.repeat(" ", niveles - i);
            // TODO: agregar asteriscos (2*i - 1)
        sb.repeat("*", 2*i - 1);
            // TODO: agregar salto de linea
        sb.append("\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println("Invertir 'Hola Mundo': "
                         + invertir("Hola Mundo"));
        System.out.println("'Anita lava la tina' es palindromo: "
                         + esPalindromo("Anita lava la tina"));
        System.out.println("Vocales en 'Murcielago': "
                         + contarVocales("Murcielago"));
        System.out.println("Piramide de 5 niveles:");
        System.out.println(construirPiramide(5));
    }
}
