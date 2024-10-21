package org.semana03.ejercicio.modelos;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@NoArgsConstructor
public class ColeccionFunkos {

    private static final String DELIMITADOR_COMA = ",";

    List<Funko> funkos = new ArrayList<>();

    public ColeccionFunkos(String ficheroFunkos) {
        try (Stream<String> contenidoFichero = Files.lines(Path.of(ficheroFunkos))) {
            funkos = contenidoFichero.skip(1)
                    .map(l -> Arrays.asList(l.split(DELIMITADOR_COMA)))
                    .map(d -> new Funko(d.get(0), d.get(1), d.get(2),
                            Double.parseDouble(d.get(3)),
                            LocalDate.parse(d.get(4))))
                    .toList();
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }

    public Funko funkoMasCaro() {
        return funkos.stream().max(Comparator.comparing(Funko::getPrecio)).orElse(null);
    }

    public Map<String, List<Funko>> funkosPorModelo() {
        return funkos.stream().collect(Collectors.groupingBy(Funko::getModelo));
    }

    public Map<String, Long> cantidadFunkosPorModelo() {
        return funkos.stream().collect(Collectors.groupingBy(Funko::getModelo, Collectors.counting()));
    }

    public double precioMedioDeFunkos() {
        return funkos.stream().mapToDouble(Funko::getPrecio).average().orElse(0.0);
    }

    public List<Funko> funkosPorAnyo(int anyo) {
        return funkos.stream().filter(f -> f.getFechaLanzamiento().getYear() == anyo).toList();
    };

    public void serializarFunkos(String rutaFichero) {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(rutaFichero))) {
            oos.writeObject(funkos);
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }

    public void deserializarFunkos(String rutaFichero){
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(rutaFichero))) {
            // Lo correcto sería comprobar que los datos leídos son realmente
            // una lista de Funkos antes de hacer el cast. Por ahora no vamos a hacerlo.
            funkos = (List<Funko>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace(System.out);
        }
    }
}
