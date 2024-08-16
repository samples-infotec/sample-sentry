package mx.infotec.sample.sentry.domain;

import static org.assertj.core.api.Assertions.assertThat;

import mx.infotec.sample.sentry.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ConvocatoriaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Convocatoria.class);
        Convocatoria convocatoria1 = new Convocatoria();
        convocatoria1.setId("id1");
        Convocatoria convocatoria2 = new Convocatoria();
        convocatoria2.setId(convocatoria1.getId());
        assertThat(convocatoria1).isEqualTo(convocatoria2);
        convocatoria2.setId("id2");
        assertThat(convocatoria1).isNotEqualTo(convocatoria2);
        convocatoria1.setId(null);
        assertThat(convocatoria1).isNotEqualTo(convocatoria2);
    }
}
