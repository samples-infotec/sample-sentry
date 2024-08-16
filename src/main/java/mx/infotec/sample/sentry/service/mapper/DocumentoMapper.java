package mx.infotec.sample.sentry.service.mapper;

import mx.infotec.sample.sentry.domain.Documento;
import mx.infotec.sample.sentry.domain.Proyecto;
import mx.infotec.sample.sentry.service.dto.DocumentoDTO;
import mx.infotec.sample.sentry.service.dto.ProyectoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Documento} and its DTO {@link DocumentoDTO}.
 */
@Mapper(componentModel = "spring")
public interface DocumentoMapper extends EntityMapper<DocumentoDTO, Documento> {
    @Mapping(target = "proyecto", source = "proyecto", qualifiedByName = "proyectoId")
    DocumentoDTO toDto(Documento s);

    @Named("proyectoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProyectoDTO toDtoProyectoId(Proyecto proyecto);
}
