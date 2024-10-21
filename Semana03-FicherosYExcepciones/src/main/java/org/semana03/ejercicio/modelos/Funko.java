package org.semana03.ejercicio.modelos;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class Funko implements Serializable {
    private String id;
    private String nombre;
    private String modelo;
    private double precio;
    private LocalDate fechaLanzamiento;

    // Auxiliar para parsear la lista de Strings a un objeto Funko
    public Funko(List<String> lista) {
        this.id = lista.get(0);
        this.nombre = lista.get(1);
        this.modelo = lista.get(2);
        this.precio = Double.parseDouble(lista.get(3));
        this.fechaLanzamiento = LocalDate.parse(lista.get(4));
    }
}
