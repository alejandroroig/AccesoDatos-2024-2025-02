package org.semana03.ejemplos;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FicherosBinarios {

    public static void main(String[] args) {
        Path unFichero = Path.of(".", "src", "main", "resources", "ficheroBytes");
        byte[] bytes = "Ejemplo de texto para fichero binario".getBytes();

        // Opción 1: Escritura con Files.write() de java.nio y lectura con Files.readAllBytes
        System.out.println("\nLectura y escritura binaria con métodos de Files");
        try {
            // Escritura
            Files.write(unFichero, bytes, StandardOpenOption.CREATE);
            // Lectura
            byte[] leidos = Files.readAllBytes(unFichero);
            System.out.println(new String(leidos));
        }
        catch (IOException ex) {
            System.err.println("Error al leer o escribir el fichero");
        }

        // Opción 2: OutputStream e InputStream
        System.out.println("\nLectura y escritura binaria con FileInputStream y FileOutputStream");
        try (OutputStream os = Files.newOutputStream(unFichero);
             InputStream is = Files.newInputStream(unFichero)) {
            // Escritura
            os.write(bytes);

            // Lectura
            int c;
            while((c = is.read()) != -1){
                System.out.print((char) c);
            }
        } catch (IOException ex) {
            System.err.println("Error al leer o escribir el fichero");
        }

        // Opción 3: Lectura y escritura binaria con FileChannel y ByteBuffer
        // FileChannel se puede combinar con operaciones asíncronas y no bloqueantes, lo que lo hace más eficiente
        // en escenarios de E/S intensiva y alta concurrencia
        System.out.println("\nLectura y escritura binaria con FileChannel y ByteBuffer");
        try (FileChannel channel = FileChannel.open(unFichero, StandardOpenOption.READ, StandardOpenOption.WRITE)) {
            // Escritura
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.put("Este es un nuevo texto escrito con FileChannel y ByteBuffer".getBytes());
            buffer.flip(); // Cambiar el buffer de modo escritura a modo lectura para volcarlo al canal
            channel.write(buffer);

            // Lectura
            buffer.clear(); // Vacía el buffer para que esté listo para ser rellenado
            channel.position(0); // Volvemos al inicio del archivo para leerlo de nuevo
            int bytesRead = channel.read(buffer);
            buffer.flip(); // Preparar el buffer para la lectura
            System.out.println(new String(buffer.array(), 0, bytesRead));
        } catch (IOException ex) {
            System.err.println("Error al leer o escribir el fichero");
        }
    }
}
