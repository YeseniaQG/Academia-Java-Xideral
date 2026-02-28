package pipelineProcesamiento;

import java.util.*;
import java.util.stream.*;
import static java.util.stream.Collectors.*;

public class TextAnalyzer {
    private final List<String> lines;

    public TextAnalyzer(List<String> lines) {
        this.lines = lines;
    }

    // Genera un Stream básico de todas las palabras separadas por espacios
    private Stream<String> words() {
        return lines.stream()
            .flatMap(line -> Arrays.stream(line.split("\\s+")))
            .filter(w -> !w.isEmpty());
    }

    // Limpia las palabras: quita símbolos, pasa a minúsculas y elimina vacíos
    private Stream<String> cleanWords() {
        return words()
            .map(w -> w.replaceAll("[^a-zA-Z]", ""))
            .filter(w -> !w.isEmpty())
            .map(String::toLowerCase); // Method reference para minúsculas
    }

    public long wordCount() {
        // count() es una operación terminal que devuelve el total de elementos
        return words().count();
    }

    public Set<String> uniqueWords() {
        // toSet() elimina automáticamente los duplicados
        return cleanWords().collect(toSet());
    }

    public List<Map.Entry<String, Long>> topN(int n) {
        // 1. Agrupar por palabra y contar ocurrencias
        return cleanWords()
            .collect(groupingBy(w -> w, counting()))
            .entrySet().stream()
            // 2. Ordenar por el valor (conteo) de forma descendente
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            // 3. Tomar solo los primeros N
            .limit(n)
            .collect(toList());
    }

    public double averageWordLength() {
        // mapToInt convierte a un stream de primitivos para usar average()
        return cleanWords()
            .mapToInt(String::length)
            .average()
            .orElse(0.0);
    }

    public Map<Character, List<String>> wordsByFirstLetter() {
        // distinct() asegura que no tengamos palabras repetidas en las listas
        return cleanWords()
            .distinct()
            .collect(groupingBy(w -> w.charAt(0)));
    }

    public static void main(String[] args) {
        List<String> text = List.of(
            "Java is a powerful programming language",
            "Java streams make data processing elegant",
            "Lambdas and streams are the heart of modern Java"
        );

        TextAnalyzer analyzer = new TextAnalyzer(text);

        System.out.println("=== Estadisticas de Texto ===");
        System.out.println("Total palabras: " + analyzer.wordCount());
        System.out.println("Palabras unicas: " + analyzer.uniqueWords().size());
        System.out.printf("Longitud promedio: %.2f%n", analyzer.averageWordLength());

        System.out.println("\n=== Top 5 Palabras ===");
        analyzer.topN(5).forEach(e ->
            System.out.printf("  '%s': %d veces%n", e.getKey(), e.getValue()));

        System.out.println("\n=== Palabras por Letra ===");
        // Ordenamos el mapa por letra (Key) antes de imprimir
        analyzer.wordsByFirstLetter().entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .forEach(e -> System.out.printf("  %c: %s%n", e.getKey(), e.getValue()));
    }
}