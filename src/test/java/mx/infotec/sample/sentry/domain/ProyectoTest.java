package mx.infotec.sample.sentry.domain;

import static org.assertj.core.api.Assertions.assertThat;

import mx.infotec.sample.sentry.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProyectoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Proyecto.class);
        Proyecto proyecto1 = new Proyecto();
        proyecto1.setId("id1");
        Proyecto proyecto2 = new Proyecto();
        proyecto2.setId(proyecto1.getId());
        assertThat(proyecto1).isEqualTo(proyecto2);
        proyecto2.setId("id2");
        assertThat(proyecto1).isNotEqualTo(proyecto2);
        proyecto1.setId(null);
        assertThat(proyecto1).isNotEqualTo(proyecto2);
    }
}
