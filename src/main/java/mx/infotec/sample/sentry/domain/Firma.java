package mx.infotec.sample.sentry.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Firma.
 */
@Document(collection = "firma")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Firma implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull(message = "must not be null")
    @Field("rol")
    private String rol;

    @Field("documento_uri")
    private String documentoUri;

    @Field("certificado")
    private String certificado;

    @NotNull(message = "must not be null")
    @Field("rfc")
    private String rfc;

    @Field("curp")
    private String curp;

    @Field("signature")
    private String signature;

    @NotNull(message = "must not be null")
    @Field("active")
    private Boolean active;

    @Field("documento")
    @JsonIgnoreProperties(value = { "firmas", "proyecto" }, allowSetters = true)
    private Documento documento;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Firma id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRol() {
        return this.rol;
    }

    public Firma rol(String rol) {
        this.setRol(rol);
        return this;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getDocumentoUri() {
        return this.documentoUri;
    }

    public Firma documentoUri(String documentoUri) {
        this.setDocumentoUri(documentoUri);
        return this;
    }

    public void setDocumentoUri(String documentoUri) {
        this.documentoUri = documentoUri;
    }

    public String getCertificado() {
        return this.certificado;
    }

    public Firma certificado(String certificado) {
        this.setCertificado(certificado);
        return this;
    }

    public void setCertificado(String certificado) {
        this.certificado = certificado;
    }

    public String getRfc() {
        return this.rfc;
    }

    public Firma rfc(String rfc) {
        this.setRfc(rfc);
        return this;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getCurp() {
        return this.curp;
    }

    public Firma curp(String curp) {
        this.setCurp(curp);
        return this;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getSignature() {
        return this.signature;
    }

    public Firma signature(String signature) {
        this.setSignature(signature);
        return this;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Boolean getActive() {
        return this.active;
    }

    public Firma active(Boolean active) {
        this.setActive(active);
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Documento getDocumento() {
        return this.documento;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }

    public Firma documento(Documento documento) {
        this.setDocumento(documento);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Firma)) {
            return false;
        }
        return id != null && id.equals(((Firma) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Firma{" +
            "id=" + getId() +
            ", rol='" + getRol() + "'" +
            ", documentoUri='" + getDocumentoUri() + "'" +
            ", certificado='" + getCertificado() + "'" +
            ", rfc='" + getRfc() + "'" +
            ", curp='" + getCurp() + "'" +
            ", signature='" + getSignature() + "'" +
            ", active='" + getActive() + "'" +
            "}";
    }
}
