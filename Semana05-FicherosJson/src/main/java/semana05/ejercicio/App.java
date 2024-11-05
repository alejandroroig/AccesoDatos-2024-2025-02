package semana05.ejercicio;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class App {

    public static void main(String[] args) {
        // 1. Leer el fichero JSON e imprimir los videojuegos iniciales.
        List<Videojuego> videojuegos = leerVideojuegosDesdeJSON("src/main/resources/videojuegos.json");
        System.out.println("Lista de videojuegos inicial:");
        videojuegos.forEach(System.out::println);

        // 2. Crear un nuevo videojuego y añadirlo a la lista.
        Videojuego nuevoJuego = crearNuevoVideojuego();
        videojuegos.add(nuevoJuego);
        System.out.println("\nVideojuego añadido:");
        System.out.println(nuevoJuego);

        // 3. Serializar la lista actualizada de videojuegos de nuevo a un archivo JSON.
        escribirVideojuegosAJSON(videojuegos, "src/main/resources/videojuegos_actualizados.json");
        System.out.println("\nVideojuegos serializados en 'videojuegos_actualizados.json'.");
    }

    // Lectura de videojuegos desde JSON
    public static List<Videojuego> leerVideojuegosDesdeJSON(String ruta) {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        try (Reader reader = Files.newBufferedReader(Path.of(ruta))) {
            return objectMapper.readValue(reader, new TypeReference<>() {});
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
            return List.of();
        }
    }

    // Escritura de videojuegos en JSON
    public static void escribirVideojuegosAJSON(List<Videojuego> videojuegos, String ruta) {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);  // Formatear JSON
        try (Writer writer = Files.newBufferedWriter(Path.of(ruta))) {
            objectMapper.writeValue(writer, videojuegos);
        } catch (IOException e) {
            System.out.println("Error al escribir el archivo: " + e.getMessage());
        }
    }

    // Función auxiliar para crear un nuevo videojuego
    private static Videojuego crearNuevoVideojuego() {
        Plataforma plataforma = new Plataforma("PlayStation 5", "Sony");
        Desarrollador desarrollador = new Desarrollador("Naughty Dog", "United States");
        List<Desarrollador> desarrolladores = new ArrayList<>();
        desarrolladores.add(desarrollador);

        List<Review> reviews = new ArrayList<>();
        reviews.add(new Review("Incredible visuals and story.", LocalDate.of(2022, 8, 5), 5));
        reviews.add(new Review("Best graphics ever!", LocalDate.of(2022, 9, 15), 5));

        return new Videojuego("The Last of Us Part II",
                plataforma,
                "Action",
                desarrolladores,
                LocalDate.of(2020, 6, 19),
                reviews);
    }
}

