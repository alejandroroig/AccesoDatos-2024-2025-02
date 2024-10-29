package semana04.ejercicio.csv;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class OperacionesCsv {
    private static final Path CSV_READ_FILE_PATH = Path.of(".", "src", "main", "resources", "funkos.csv");
    private static final Path CSV_WRITE_FILE_PATH = Path.of(".", "src", "main", "resources", "funkosOutput.csv");

    public static List<Funko> leerFunkosDeCSV() {
        try (Reader reader = Files.newBufferedReader(CSV_READ_FILE_PATH)) {
            CsvToBean<Funko> csvToBean = new CsvToBeanBuilder<Funko>(reader)
                    .withType(Funko.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            return csvToBean.parse();

        } catch (Exception e) {
            System.err.println("Error al leer el fichero");
            return List.of();
        }
    }

    public static void escribirFunkosEnCSV(List<Funko> funkos) {
        try (Writer writer = Files.newBufferedWriter(CSV_WRITE_FILE_PATH)) {
            StatefulBeanToCsv<Funko> beanToCsv = new StatefulBeanToCsvBuilder<Funko>(writer)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .build();
            beanToCsv.write(funkos);

        } catch (Exception e) {
            System.err.println("Error al escribir el fichero: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        List<Funko> funkos = leerFunkosDeCSV();
        System.out.println("Funkos leídos del archivo CSV:");
        funkos.forEach(System.out::println);

        escribirFunkosEnCSV(funkos);
    }
}
