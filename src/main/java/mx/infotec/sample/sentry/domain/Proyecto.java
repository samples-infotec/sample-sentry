package mx.infotec.sample.sentry.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Proyecto.
 */
@Document(collection = "proyecto")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Proyecto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("detalle")
    private String detalle;

    @NotNull(message = "must not be null")
    @Field("modalidad")
    private String modalidad;

    @NotNull(message = "must not be null")
    @Field("titulo")
    private String titulo;

    @Field("solicitud_clave")
    private String solicitudClave;

    @Field("solicitud_fecha")
    private Instant solicitudFecha;

    @NotNull(message = "must not be null")
    @Field("completo")
    private Boolean completo;

    @Field("documento")
    @JsonIgnoreProperties(value = { "firmas", "proyecto" }, allowSetters = true)
    private Set<Documento> documentos = new HashSet<>();

    @Field("convocatoria")
    @JsonIgnoreProperties(value = { "proyectos" }, allowSetters = true)
    private Convocatoria convocatoria;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Proyecto id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDetalle() {
        return this.detalle;
    }

    public Proyecto detalle(String detalle) {
        this.setDetalle(detalle);
        return this;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getModalidad() {
        return this.modalidad;
    }

    public Proyecto modalidad(String modalidad) {
        this.setModalidad(modalidad);
        return this;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public Proyecto titulo(String titulo) {
        this.setTitulo(titulo);
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSolicitudClave() {
        return this.solicitudClave;
    }

    public Proyecto solicitudClave(String solicitudClave) {
        this.setSolicitudClave(solicitudClave);
        return this;
    }

    public void setSolicitudClave(String solicitudClave) {
        this.solicitudClave = solicitudClave;
    }

    public Instant getSolicitudFecha() {
        return this.solicitudFecha;
    }

    public Proyecto solicitudFecha(Instant solicitudFecha) {
        this.setSolicitudFecha(solicitudFecha);
        return this;
    }

    public void setSolicitudFecha(Instant solicitudFecha) {
        this.solicitudFecha = solicitudFecha;
    }

    public Boolean getCompleto() {
        return this.completo;
    }

    public Proyecto completo(Boolean completo) {
        this.setCompleto(completo);
        return this;
    }

    public void setCompleto(Boolean completo) {
        this.completo = completo;
    }

    public Set<Documento> getDocumentos() {
        return this.documentos;
    }

    public void setDocumentos(Set<Documento> documentos) {
        if (this.documentos != null) {
            this.documentos.forEach(i -> i.setProyecto(null));
        }
        if (documentos != null) {
            documentos.forEach(i -> i.setProyecto(this));
        }
        this.documentos = documentos;
    }

    public Proyecto documentos(Set<Documento> documentos) {
        this.setDocumentos(documentos);
        return this;
    }

    public Proyecto addDocumento(Documento documento) {
        this.documentos.add(documento);
        documento.setProyecto(this);
        return this;
    }

    public Proyecto removeDocumento(Documento documento) {
        this.documentos.remove(documento);
        documento.setProyecto(null);
        return this;
    }

    public Convocatoria getConvocatoria() {
        return this.convocatoria;
    }

    public void setConvocatoria(Convocatoria convocatoria) {
        this.convocatoria = convocatoria;
    }

    public Proyecto convocatoria(Convocatoria convocatoria) {
        this.setConvocatoria(convocatoria);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Proyecto)) {
            return false;
        }
        return id != null && id.equals(((Proyecto) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Proyecto{" +
            "id=" + getId() +
            ", detalle='" + getDetalle() + "'" +
            ", modalidad='" + getModalidad() + "'" +
            ", titulo='" + getTitulo() + "'" +
            ", solicitudClave='" + getSolicitudClave() + "'" +
            ", solicitudFecha='" + getSolicitudFecha() + "'" +
            ", completo='" + getCompleto() + "'" +
            "}";
    }
}
