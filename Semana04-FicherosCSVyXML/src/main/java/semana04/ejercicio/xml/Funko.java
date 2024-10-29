package semana04.ejercicio.xml;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
public class Funko {
    @JacksonXmlProperty(localName = "CODIGO")
    private UUID codigo;
    @JacksonXmlProperty(localName = "NOMBRE")
    private String nombre;
    @JacksonXmlProperty(localName = "MODELO")
    private String modelo;
    @JacksonXmlProperty(localName = "PRECIO")
    private double precio;
    @JacksonXmlProperty(localName = "FECHA_LANZAMIENTO")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fechaLanzamiento;
}
