package mx.infotec.sample.sentry.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConvocatoriaMapperTest {

    private ConvocatoriaMapper convocatoriaMapper;

    @BeforeEach
    public void setUp() {
        convocatoriaMapper = new ConvocatoriaMapperImpl();
    }
}
