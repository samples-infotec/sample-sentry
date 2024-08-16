package mx.infotec.sample.sentry.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import mx.infotec.sample.sentry.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FirmaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FirmaDTO.class);
        FirmaDTO firmaDTO1 = new FirmaDTO();
        firmaDTO1.setId("id1");
        FirmaDTO firmaDTO2 = new FirmaDTO();
        assertThat(firmaDTO1).isNotEqualTo(firmaDTO2);
        firmaDTO2.setId(firmaDTO1.getId());
        assertThat(firmaDTO1).isEqualTo(firmaDTO2);
        firmaDTO2.setId("id2");
        assertThat(firmaDTO1).isNotEqualTo(firmaDTO2);
        firmaDTO1.setId(null);
        assertThat(firmaDTO1).isNotEqualTo(firmaDTO2);
    }
}
