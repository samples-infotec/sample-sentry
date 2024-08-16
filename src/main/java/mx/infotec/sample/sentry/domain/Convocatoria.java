package mx.infotec.sample.sentry.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Convocatoria.
 */
@Document(collection = "convocatoria")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Convocatoria implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull(message = "must not be null")
    @Field("nombre")
    private String nombre;

    @Min(value = 1960)
    @Max(value = 2099)
    @Field("anio")
    private Integer anio;

    @Field("fecha_creacion")
    private LocalDate fechaCreacion;

    @Field("proyecto")
    @JsonIgnoreProperties(value = { "documentos", "convocatoria" }, allowSetters = true)
    private Set<Proyecto> proyectos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Convocatoria id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Convocatoria nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getAnio() {
        return this.anio;
    }

    public Convocatoria anio(Integer anio) {
        this.setAnio(anio);
        return this;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public LocalDate getFechaCreacion() {
        return this.fechaCreacion;
    }

    public Convocatoria fechaCreacion(LocalDate fechaCreacion) {
        this.setFechaCreacion(fechaCreacion);
        return this;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Set<Proyecto> getProyectos() {
        return this.proyectos;
    }

    public void setProyectos(Set<Proyecto> proyectos) {
        if (this.proyectos != null) {
            this.proyectos.forEach(i -> i.setConvocatoria(null));
        }
        if (proyectos != null) {
            proyectos.forEach(i -> i.setConvocatoria(this));
        }
        this.proyectos = proyectos;
    }

    public Convocatoria proyectos(Set<Proyecto> proyectos) {
        this.setProyectos(proyectos);
        return this;
    }

    public Convocatoria addProyecto(Proyecto proyecto) {
        this.proyectos.add(proyecto);
        proyecto.setConvocatoria(this);
        return this;
    }

    public Convocatoria removeProyecto(Proyecto proyecto) {
        this.proyectos.remove(proyecto);
        proyecto.setConvocatoria(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Convocatoria)) {
            return false;
        }
        return id != null && id.equals(((Convocatoria) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Convocatoria{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", anio=" + getAnio() +
            ", fechaCreacion='" + getFechaCreacion() + "'" +
            "}";
    }
}
