package org.semana03.ejemplos;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.stream.Stream;

public class OperacionesPath {
    public static void main(String[] args) throws IOException {

        // Para obtener un objeto Path utilizamos el Path.of()
        Path unPath = Path.of("/ruta/al/fichero/o/directorio");

        // Obtener el nombre de un fichero y unirlo a la ruta de otro directorio
        Path unFile = Path.of("/usr/local/file.txt");
        Path unDir = Path.of("/home/user");

        // Unir directorio con nombre de fichero: /home/user/file.txt
        System.out.println(unDir.resolve(unFile.getFileName()));

        // Relativizar una ruta respecto a otra
        Path otroDir = Path.of("/home/user/docs");
        Path otroFile = Path.of("/home/user/docs/file.txt");
        // Relativizar: file.txt
        System.out.println(otroDir.relativize(otroFile));

        // La clase Files contiene métodos estáticos para realizar operaciones
        // sobre ficheros y directorios

        // Crear un directorio y sus ascendentes
        try {
            Files.createDirectories(Path.of("/un/directorio/inexistente"));
        }
        catch (IOException e) {
            System.err.println("Error al crear el directorio");
        }

        // Imprimir el contenido de un directorio
        try (Stream<Path> stream = Files.list(Path.of("/un/directorio"))) {
            stream.forEach(System.out::println);
        }
        catch (IOException e) {
            System.err.println("Error al listar el directorio");
        }

        // Imprimir los ficheros regulares de un directorio ordenados por tamaño
        try (Stream<Path> stream = Files.walk(Path.of("/otro/directorio"))) {
            stream
                    .filter(Files::isRegularFile)
                    .sorted(Comparator.comparingLong(path -> {
                        try {
                            return Files.size(path);
                        } catch (IOException e) {
                            System.err.println("Error al obtener el tamaño del fichero");
                            return 0;
                        }
                    }))
                    .forEach(System.out::println);
        }
        catch (IOException e) {
            System.err.println("Error al listar el directorio");
        }

        // Comprobar si un fichero es ejecutable, legible o escribible
        Path filePath = Path.of("/algun/fichero");
        if (Files.isReadable(filePath)) {
            System.out.println(filePath + " es legible");
        }
        if (Files.isWritable(filePath)) {
            System.out.println(filePath + " es escribible");
        }
        if (Files.isExecutable(filePath)) {
            System.out.println(filePath + " es ejecutable");
        }

        // Búsqueda de archivos por patrón
        try (Stream<Path> stream = Files.find(Path.of("/otro/directorio/mas"),
                Integer.MAX_VALUE, (path, attr) -> path.toString().endsWith(".txt"))) {
            stream.forEach(System.out::println);
        }
        catch (IOException e) {
            System.err.println("Error al buscar ficheros");
        }

        // Obtener el año, mes y día de la fecha de la última modificación
        Path file = Path.of("/un/fichero/mas");
        // LocalDate representa una fecha con año, mes y día, sin hora ni zona horaria
        LocalDate date = LocalDate.parse(Files.getLastModifiedTime(file).toString(), DateTimeFormatter.ISO_DATE_TIME);
        System.out.println(date.getYear());
        System.out.println(date.getMonthValue());
        System.out.println(date.getDayOfMonth());
        // LocalDateTime representa una fecha con año, mes, día, hora, minuto, segundo y nanosegundo
        LocalDateTime datetime = LocalDateTime.parse(Files.getLastModifiedTime(file).toString(), DateTimeFormatter.ISO_DATE_TIME);
        System.out.println(datetime.toLocalDate().getYear());
        System.out.println(datetime.toLocalDate().getMonthValue());
        System.out.println(datetime.toLocalDate().getDayOfMonth());
        System.out.println(datetime.toLocalTime().getHour());
        System.out.println(datetime.toLocalTime().getMinute());
        System.out.println(datetime.toLocalTime().getSecond());

        // Navegación entre rutas
        Path relative = Path.of(".");
        Path absolute = relative.toAbsolutePath().normalize();
        System.out.printf("Relative: %s%n", relative);
        System.out.printf("Absolute: %s%n", absolute);
        System.out.printf("Name count: %d%n", absolute.getNameCount());
        System.out.printf("Parent: %s%n", absolute.getParent());
        System.out.printf("Subpath(0, 2): %s%n", absolute.subpath(0, 2));

        // Operaciones con ficheros
        Path original = Path.of("prueba.iml");
        Path backup = Path.of("prueba.iml.backup");
        Path rename = Path.of("prueba.iml.backup.1");
        Files.copy(original, backup, StandardCopyOption.REPLACE_EXISTING);
        Files.move(backup, rename, StandardCopyOption.REPLACE_EXISTING);
        Files.delete(rename);


    }
}

