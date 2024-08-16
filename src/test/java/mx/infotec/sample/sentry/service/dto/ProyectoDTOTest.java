package mx.infotec.sample.sentry.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import mx.infotec.sample.sentry.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProyectoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProyectoDTO.class);
        ProyectoDTO proyectoDTO1 = new ProyectoDTO();
        proyectoDTO1.setId("id1");
        ProyectoDTO proyectoDTO2 = new ProyectoDTO();
        assertThat(proyectoDTO1).isNotEqualTo(proyectoDTO2);
        proyectoDTO2.setId(proyectoDTO1.getId());
        assertThat(proyectoDTO1).isEqualTo(proyectoDTO2);
        proyectoDTO2.setId("id2");
        assertThat(proyectoDTO1).isNotEqualTo(proyectoDTO2);
        proyectoDTO1.setId(null);
        assertThat(proyectoDTO1).isNotEqualTo(proyectoDTO2);
    }
}
