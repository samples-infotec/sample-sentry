package mx.infotec.sample.sentry.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FirmaMapperTest {

    private FirmaMapper firmaMapper;

    @BeforeEach
    public void setUp() {
        firmaMapper = new FirmaMapperImpl();
    }
}
