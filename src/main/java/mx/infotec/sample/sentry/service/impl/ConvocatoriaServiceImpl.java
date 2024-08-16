package mx.infotec.sample.sentry.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import mx.infotec.sample.sentry.domain.Convocatoria;
import mx.infotec.sample.sentry.repository.ConvocatoriaRepository;
import mx.infotec.sample.sentry.service.ConvocatoriaService;
import mx.infotec.sample.sentry.service.dto.ConvocatoriaDTO;
import mx.infotec.sample.sentry.service.mapper.ConvocatoriaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link Convocatoria}.
 */
@Service
public class ConvocatoriaServiceImpl implements ConvocatoriaService {

    private final Logger log = LoggerFactory.getLogger(ConvocatoriaServiceImpl.class);

    private final ConvocatoriaRepository convocatoriaRepository;

    private final ConvocatoriaMapper convocatoriaMapper;

    public ConvocatoriaServiceImpl(ConvocatoriaRepository convocatoriaRepository, ConvocatoriaMapper convocatoriaMapper) {
        this.convocatoriaRepository = convocatoriaRepository;
        this.convocatoriaMapper = convocatoriaMapper;
    }

    @Override
    public Mono<ConvocatoriaDTO> save(ConvocatoriaDTO convocatoriaDTO) {
        log.debug("Request to save Convocatoria : {}", convocatoriaDTO);
        return convocatoriaRepository.save(convocatoriaMapper.toEntity(convocatoriaDTO)).map(convocatoriaMapper::toDto);
    }

    @Override
    public Mono<ConvocatoriaDTO> update(ConvocatoriaDTO convocatoriaDTO) {
        log.debug("Request to update Convocatoria : {}", convocatoriaDTO);
        return convocatoriaRepository.save(convocatoriaMapper.toEntity(convocatoriaDTO)).map(convocatoriaMapper::toDto);
    }

    @Override
    public Mono<ConvocatoriaDTO> partialUpdate(ConvocatoriaDTO convocatoriaDTO) {
        log.debug("Request to partially update Convocatoria : {}", convocatoriaDTO);

        return convocatoriaRepository
            .findById(convocatoriaDTO.getId())
            .map(existingConvocatoria -> {
                convocatoriaMapper.partialUpdate(existingConvocatoria, convocatoriaDTO);

                return existingConvocatoria;
            })
            .flatMap(convocatoriaRepository::save)
            .map(convocatoriaMapper::toDto);
    }

    @Override
    public Flux<ConvocatoriaDTO> findAll() {
        log.debug("Request to get all Convocatorias");
        return convocatoriaRepository.findAll().map(convocatoriaMapper::toDto);
    }

    public Mono<Long> countAll() {
        return convocatoriaRepository.count();
    }

    @Override
    public Mono<ConvocatoriaDTO> findOne(String id) {
        log.debug("Request to get Convocatoria : {}", id);
        return convocatoriaRepository.findById(id).map(convocatoriaMapper::toDto);
    }

    @Override
    public Mono<Void> delete(String id) {
        log.debug("Request to delete Convocatoria : {}", id);
        return convocatoriaRepository.deleteById(id);
    }
}
