package mx.infotec.sample.sentry.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import mx.infotec.sample.sentry.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ConvocatoriaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConvocatoriaDTO.class);
        ConvocatoriaDTO convocatoriaDTO1 = new ConvocatoriaDTO();
        convocatoriaDTO1.setId("id1");
        ConvocatoriaDTO convocatoriaDTO2 = new ConvocatoriaDTO();
        assertThat(convocatoriaDTO1).isNotEqualTo(convocatoriaDTO2);
        convocatoriaDTO2.setId(convocatoriaDTO1.getId());
        assertThat(convocatoriaDTO1).isEqualTo(convocatoriaDTO2);
        convocatoriaDTO2.setId("id2");
        assertThat(convocatoriaDTO1).isNotEqualTo(convocatoriaDTO2);
        convocatoriaDTO1.setId(null);
        assertThat(convocatoriaDTO1).isNotEqualTo(convocatoriaDTO2);
    }
}
