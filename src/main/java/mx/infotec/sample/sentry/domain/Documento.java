package mx.infotec.sample.sentry.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Documento.
 */
@Document(collection = "documento")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Documento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull(message = "must not be null")
    @Field("uri")
    private String uri;

    @NotNull(message = "must not be null")
    @Field("data")
    private String data;

    @Field("firma")
    @JsonIgnoreProperties(value = { "documento" }, allowSetters = true)
    private Set<Firma> firmas = new HashSet<>();

    @Field("proyecto")
    @JsonIgnoreProperties(value = { "documentos", "convocatoria" }, allowSetters = true)
    private Proyecto proyecto;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Documento id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUri() {
        return this.uri;
    }

    public Documento uri(String uri) {
        this.setUri(uri);
        return this;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getData() {
        return this.data;
    }

    public Documento data(String data) {
        this.setData(data);
        return this;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Set<Firma> getFirmas() {
        return this.firmas;
    }

    public void setFirmas(Set<Firma> firmas) {
        if (this.firmas != null) {
            this.firmas.forEach(i -> i.setDocumento(null));
        }
        if (firmas != null) {
            firmas.forEach(i -> i.setDocumento(this));
        }
        this.firmas = firmas;
    }

    public Documento firmas(Set<Firma> firmas) {
        this.setFirmas(firmas);
        return this;
    }

    public Documento addFirma(Firma firma) {
        this.firmas.add(firma);
        firma.setDocumento(this);
        return this;
    }

    public Documento removeFirma(Firma firma) {
        this.firmas.remove(firma);
        firma.setDocumento(null);
        return this;
    }

    public Proyecto getProyecto() {
        return this.proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public Documento proyecto(Proyecto proyecto) {
        this.setProyecto(proyecto);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Documento)) {
            return false;
        }
        return id != null && id.equals(((Documento) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Documento{" +
            "id=" + getId() +
            ", uri='" + getUri() + "'" +
            ", data='" + getData() + "'" +
            "}";
    }
}
