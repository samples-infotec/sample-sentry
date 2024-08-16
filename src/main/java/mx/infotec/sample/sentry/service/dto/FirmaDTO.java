package mx.infotec.sample.sentry.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link mx.infotec.sample.sentry.domain.Firma} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FirmaDTO implements Serializable {

    private String id;

    @NotNull(message = "must not be null")
    private String rol;

    private String documentoUri;

    private String certificado;

    @NotNull(message = "must not be null")
    private String rfc;

    private String curp;

    private String signature;

    @NotNull(message = "must not be null")
    private Boolean active;

    private DocumentoDTO documento;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getDocumentoUri() {
        return documentoUri;
    }

    public void setDocumentoUri(String documentoUri) {
        this.documentoUri = documentoUri;
    }

    public String getCertificado() {
        return certificado;
    }

    public void setCertificado(String certificado) {
        this.certificado = certificado;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public DocumentoDTO getDocumento() {
        return documento;
    }

    public void setDocumento(DocumentoDTO documento) {
        this.documento = documento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FirmaDTO)) {
            return false;
        }

        FirmaDTO firmaDTO = (FirmaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, firmaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FirmaDTO{" +
            "id='" + getId() + "'" +
            ", rol='" + getRol() + "'" +
            ", documentoUri='" + getDocumentoUri() + "'" +
            ", certificado='" + getCertificado() + "'" +
            ", rfc='" + getRfc() + "'" +
            ", curp='" + getCurp() + "'" +
            ", signature='" + getSignature() + "'" +
            ", active='" + getActive() + "'" +
            ", documento=" + getDocumento() +
            "}";
    }
}
