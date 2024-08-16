package mx.infotec.sample.sentry.domain;

import static org.assertj.core.api.Assertions.assertThat;

import mx.infotec.sample.sentry.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FirmaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Firma.class);
        Firma firma1 = new Firma();
        firma1.setId("id1");
        Firma firma2 = new Firma();
        firma2.setId(firma1.getId());
        assertThat(firma1).isEqualTo(firma2);
        firma2.setId("id2");
        assertThat(firma1).isNotEqualTo(firma2);
        firma1.setId(null);
        assertThat(firma1).isNotEqualTo(firma2);
    }
}
