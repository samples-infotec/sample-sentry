package mx.infotec.sample.sentry.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link mx.infotec.sample.sentry.domain.Documento} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DocumentoDTO implements Serializable {

    private String id;

    @NotNull(message = "must not be null")
    private String uri;

    @NotNull(message = "must not be null")
    private String data;

    private ProyectoDTO proyecto;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public ProyectoDTO getProyecto() {
        return proyecto;
    }

    public void setProyecto(ProyectoDTO proyecto) {
        this.proyecto = proyecto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DocumentoDTO)) {
            return false;
        }

        DocumentoDTO documentoDTO = (DocumentoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, documentoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DocumentoDTO{" +
            "id='" + getId() + "'" +
            ", uri='" + getUri() + "'" +
            ", data='" + getData() + "'" +
            ", proyecto=" + getProyecto() +
            "}";
    }
}
