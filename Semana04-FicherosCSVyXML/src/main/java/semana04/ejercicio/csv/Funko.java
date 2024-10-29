package semana04.ejercicio.csv;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class Funko {
    @CsvBindByName(column = "CODIGO")
    private UUID codigo;
    @CsvBindByName(column = "NOMBRE")
    private String nombre;
    @CsvBindByName(column = "MODELO")
    private String modelo;
    @CsvBindByName(column = "PRECIO")
    private double precio;
    @CsvBindByName(column = "FECHA_LANZAMIENTO")
    @CsvDate("yyyy-MM-dd")
    private LocalDate fechaLanzamiento;
}
