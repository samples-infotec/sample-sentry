package mx.infotec.sample.sentry.service.mapper;

import mx.infotec.sample.sentry.domain.Convocatoria;
import mx.infotec.sample.sentry.domain.Proyecto;
import mx.infotec.sample.sentry.service.dto.ConvocatoriaDTO;
import mx.infotec.sample.sentry.service.dto.ProyectoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Proyecto} and its DTO {@link ProyectoDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProyectoMapper extends EntityMapper<ProyectoDTO, Proyecto> {
    @Mapping(target = "convocatoria", source = "convocatoria", qualifiedByName = "convocatoriaId")
    ProyectoDTO toDto(Proyecto s);

    @Named("convocatoriaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ConvocatoriaDTO toDtoConvocatoriaId(Convocatoria convocatoria);
}
