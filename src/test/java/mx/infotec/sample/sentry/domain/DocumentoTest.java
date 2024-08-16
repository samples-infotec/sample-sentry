package mx.infotec.sample.sentry.domain;

import static org.assertj.core.api.Assertions.assertThat;

import mx.infotec.sample.sentry.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DocumentoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Documento.class);
        Documento documento1 = new Documento();
        documento1.setId("id1");
        Documento documento2 = new Documento();
        documento2.setId(documento1.getId());
        assertThat(documento1).isEqualTo(documento2);
        documento2.setId("id2");
        assertThat(documento1).isNotEqualTo(documento2);
        documento1.setId(null);
        assertThat(documento1).isNotEqualTo(documento2);
    }
}
