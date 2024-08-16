package mx.infotec.sample.sentry.service.mapper;

import mx.infotec.sample.sentry.domain.Documento;
import mx.infotec.sample.sentry.domain.Firma;
import mx.infotec.sample.sentry.service.dto.DocumentoDTO;
import mx.infotec.sample.sentry.service.dto.FirmaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Firma} and its DTO {@link FirmaDTO}.
 */
@Mapper(componentModel = "spring")
public interface FirmaMapper extends EntityMapper<FirmaDTO, Firma> {
    @Mapping(target = "documento", source = "documento", qualifiedByName = "documentoId")
    FirmaDTO toDto(Firma s);

    @Named("documentoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DocumentoDTO toDtoDocumentoId(Documento documento);
}
