package mx.infotec.sample.sentry.service.mapper;

import mx.infotec.sample.sentry.domain.Convocatoria;
import mx.infotec.sample.sentry.service.dto.ConvocatoriaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Convocatoria} and its DTO {@link ConvocatoriaDTO}.
 */
@Mapper(componentModel = "spring")
public interface ConvocatoriaMapper extends EntityMapper<ConvocatoriaDTO, Convocatoria> {}
