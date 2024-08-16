package mx.infotec.sample.sentry.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link mx.infotec.sample.sentry.domain.Convocatoria} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ConvocatoriaDTO implements Serializable {

    private String id;

    @NotNull(message = "must not be null")
    private String nombre;

    @Min(value = 1960)
    @Max(value = 2099)
    private Integer anio;

    private LocalDate fechaCreacion;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConvocatoriaDTO)) {
            return false;
        }

        ConvocatoriaDTO convocatoriaDTO = (ConvocatoriaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, convocatoriaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConvocatoriaDTO{" +
            "id='" + getId() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", anio=" + getAnio() +
            ", fechaCreacion='" + getFechaCreacion() + "'" +
            "}";
    }
}
