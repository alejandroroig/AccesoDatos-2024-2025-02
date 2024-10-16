package org.semana03.ejemplos;

import lombok.*;

import java.io.*;
import java.nio.file.Path;

// Al usar @Data, se generan automáticamente los siguientes métodos para una clase:
// Getters para todos los campos. -> @Getter
// Setters para todos los campos no finales. -> @Setter
// Métodos toString, equals y hashCode que incluyen los campos de la clase.-> @ToString, @EqualsAndHashCode
// Constructor que inicializa todos los campos finales y aquellos marcados con @NonNull. -> @RequiredArgsConstructor
@Data
// Constructor que inicializa todos los campos. -> @AllArgsConstructor
@AllArgsConstructor
class Empleado implements Serializable
{
    // El valor de serialVersionUID es un número de versión
    // que se utiliza para controlar la compatibilidad de la serialización.
    @Serial
    private static final long serialVersionUID = 100L;
    private String nombre;
    private int edad;
    private double sueldo;
    // Si no quiero serializar un atributo, lo marco con la palabra clave transient
    // private transient String password;
}

public class SerializacionObjetos {
    public static void main(String[] args) {
        Path fichero = Path.of(".", "src", "main", "resources", "empleados.dat");

        // En el caso de querer serializar una lista de objetos, lo más intuitivo
        // es recorrer la colección y serializar uno a uno los objetos que la contienen
        // Sin embargo, tenemos la opción de serializar colecciones enteras siempre
        // que los elementos contenidos sean serializables.
        //
        // Por ejemplo:
        // List<Empleado> empleadosToWrite = new ArrayList<>();
        // empleadosToWrite.add(new Empleado(...));
        // empleadosToWrite.add(new Empleado(...));
        // oos.writeObject(empleadosToWrite);
        // List<Empleado> empleadosToRead = (List<Empleado>)(ois.readObject());
        try (FileOutputStream fos = new FileOutputStream(fichero.toFile());
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            Empleado emp = new Empleado("Juan Palomo", 25, 1400);
            oos.writeObject(emp);
        } catch(Exception e) {
            System.err.println("Error al serializar el objeto");
        }

        try (FileInputStream fis = new FileInputStream(fichero.toFile());
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            Empleado emp = (Empleado) ois.readObject();
            System.out.println(emp.toString());
        } catch(Exception e) {
            System.err.println("Error al deserializar el objeto");
        }
    }
}
