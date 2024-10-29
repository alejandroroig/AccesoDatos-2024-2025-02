package semana04.ejercicio.xml;


import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class OperacionesXml {
    private static final Path XML_READ_FILE_PATH = Path.of(".", "src", "main", "resources", "funkos.xml");
    private static final Path XML_WRITE_FILE_PATH = Path.of(".", "src", "main", "resources", "funkosOutput.xml");

    public static List<Funko> leerFunkosDeXML() {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.registerModule(new JavaTimeModule());

        try (InputStream inputStream = Files.newInputStream(XML_READ_FILE_PATH)) {
            ListaFunkos listaFunkos = xmlMapper.readValue(inputStream, ListaFunkos.class);
            return listaFunkos.getFunkos();

        } catch (IOException e) {
            System.err.println("Error al leer el fichero: " + e.getMessage());
            return List.of();
        }
    }

    public static void escribirFunkosEnXML(List<Funko> funkos) {
        ListaFunkos listaFunkos = new ListaFunkos(funkos);

        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.registerModule(new JavaTimeModule());
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);

        try (OutputStream outputStream = Files.newOutputStream(XML_WRITE_FILE_PATH)) {
            xmlMapper.writeValue(outputStream, listaFunkos);
        } catch (IOException e) {
            System.err.println("Error al escribir el fichero: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        List<Funko> funkos = leerFunkosDeXML();
        System.out.println("Funkos leídos del archivo CSV:");
        funkos.forEach(System.out::println);

        escribirFunkosEnXML(funkos);
    }
}
