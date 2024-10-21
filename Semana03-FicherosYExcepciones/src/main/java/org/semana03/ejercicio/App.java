package org.semana03.ejercicio;

import org.semana03.ejercicio.modelos.ColeccionFunkos;

public class App {
    public static void main(String[] args) {
        String ficheroFunkos = "src/main/resources/funkos.csv";

        // Lectura del fichero CSV
        ColeccionFunkos funkos = new ColeccionFunkos(ficheroFunkos);
        funkos.getFunkos().forEach(System.out::println);

        // Consultas
        System.out.println("El funko más caro es: " + funkos.funkoMasCaro());
        System.out.println("El precio medio de los funkos es: " + funkos.precioMedioDeFunkos());
        System.out.println("Los funkos por modelo son:");
        funkos.funkosPorModelo().forEach((k, v) -> System.out.println(k + " " + v));
        System.out.println("El número de funkos por modelo son:");
        funkos.cantidadFunkosPorModelo().forEach((k, v) -> System.out.println(k + " " + v));
        System.out.println("Los funkos del 2023 son: " + funkos.funkosPorAnyo(2023));

        // Serialización
        String ficheroSerializado = "src/main/resources/funkos.dat";
        funkos.serializarFunkos(ficheroSerializado);

        // Deserialización
        ColeccionFunkos funkosDeserializados = new ColeccionFunkos();
        funkosDeserializados.deserializarFunkos(ficheroSerializado);
        funkosDeserializados.getFunkos().forEach(System.out::println);
    }
}
