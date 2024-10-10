package org.semana03.ejemplos;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public class FicherosCSV {
    public static void main(String[] args) {

        // Lectura de ficheros CSV con Files.lines en java.nio
        try (Stream<String> lineasFichero = Files.lines(Path.of(".", "src", "main", "resources", "funkos.csv"))) {
            // Mapeamos cada línea del fichero a una lista de cadenas separadas por comas
            List<List<String>> libros = lineasFichero
                    .map(l -> List.of(l.split(","))) // Convertimos cada línea en una lista de campos (strings)
                    .toList(); // Convertimos el Stream en una lista, quedando una lista de listas de strings

            libros.forEach(System.out::println);
        } catch (IOException e) {
            System.out.println("Error al leer el fichero: " + e.getMessage());
        }
    }
}
