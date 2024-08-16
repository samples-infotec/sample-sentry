package mx.infotec.sample.sentry.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link mx.infotec.sample.sentry.domain.Proyecto} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProyectoDTO implements Serializable {

    private String id;

    private String detalle;

    @NotNull(message = "must not be null")
    private String modalidad;

    @NotNull(message = "must not be null")
    private String titulo;

    private String solicitudClave;

    private Instant solicitudFecha;

    @NotNull(message = "must not be null")
    private Boolean completo;

    private ConvocatoriaDTO convocatoria;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSolicitudClave() {
        return solicitudClave;
    }

    public void setSolicitudClave(String solicitudClave) {
        this.solicitudClave = solicitudClave;
    }

    public Instant getSolicitudFecha() {
        return solicitudFecha;
    }

    public void setSolicitudFecha(Instant solicitudFecha) {
        this.solicitudFecha = solicitudFecha;
    }

    public Boolean getCompleto() {
        return completo;
    }

    public void setCompleto(Boolean completo) {
        this.completo = completo;
    }

    public ConvocatoriaDTO getConvocatoria() {
        return convocatoria;
    }

    public void setConvocatoria(ConvocatoriaDTO convocatoria) {
        this.convocatoria = convocatoria;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProyectoDTO)) {
            return false;
        }

        ProyectoDTO proyectoDTO = (ProyectoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, proyectoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProyectoDTO{" +
            "id='" + getId() + "'" +
            ", detalle='" + getDetalle() + "'" +
            ", modalidad='" + getModalidad() + "'" +
            ", titulo='" + getTitulo() + "'" +
            ", solicitudClave='" + getSolicitudClave() + "'" +
            ", solicitudFecha='" + getSolicitudFecha() + "'" +
            ", completo='" + getCompleto() + "'" +
            ", convocatoria=" + getConvocatoria() +
            "}";
    }
}
