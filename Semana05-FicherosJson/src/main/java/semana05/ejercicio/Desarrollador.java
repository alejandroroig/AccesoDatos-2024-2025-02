package semana05.ejercicio;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Desarrollador {
    @JsonProperty("name")
    private String nombre;

    @JsonProperty("country")
    private String pais;
}