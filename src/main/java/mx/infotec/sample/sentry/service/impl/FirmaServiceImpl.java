package mx.infotec.sample.sentry.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import mx.infotec.sample.sentry.domain.Firma;
import mx.infotec.sample.sentry.repository.FirmaRepository;
import mx.infotec.sample.sentry.service.FirmaService;
import mx.infotec.sample.sentry.service.dto.FirmaDTO;
import mx.infotec.sample.sentry.service.mapper.FirmaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link Firma}.
 */
@Service
public class FirmaServiceImpl implements FirmaService {

    private final Logger log = LoggerFactory.getLogger(FirmaServiceImpl.class);

    private final FirmaRepository firmaRepository;

    private final FirmaMapper firmaMapper;

    public FirmaServiceImpl(FirmaRepository firmaRepository, FirmaMapper firmaMapper) {
        this.firmaRepository = firmaRepository;
        this.firmaMapper = firmaMapper;
    }

    @Override
    public Mono<FirmaDTO> save(FirmaDTO firmaDTO) {
        log.debug("Request to save Firma : {}", firmaDTO);
        return firmaRepository.save(firmaMapper.toEntity(firmaDTO)).map(firmaMapper::toDto);
    }

    @Override
    public Mono<FirmaDTO> update(FirmaDTO firmaDTO) {
        log.debug("Request to update Firma : {}", firmaDTO);
        return firmaRepository.save(firmaMapper.toEntity(firmaDTO)).map(firmaMapper::toDto);
    }

    @Override
    public Mono<FirmaDTO> partialUpdate(FirmaDTO firmaDTO) {
        log.debug("Request to partially update Firma : {}", firmaDTO);

        return firmaRepository
            .findById(firmaDTO.getId())
            .map(existingFirma -> {
                firmaMapper.partialUpdate(existingFirma, firmaDTO);

                return existingFirma;
            })
            .flatMap(firmaRepository::save)
            .map(firmaMapper::toDto);
    }

    @Override
    public Flux<FirmaDTO> findAll() {
        log.debug("Request to get all Firmas");
        return firmaRepository.findAll().map(firmaMapper::toDto);
    }

    public Mono<Long> countAll() {
        return firmaRepository.count();
    }

    @Override
    public Mono<FirmaDTO> findOne(String id) {
        log.debug("Request to get Firma : {}", id);
        return firmaRepository.findById(id).map(firmaMapper::toDto);
    }

    @Override
    public Mono<Void> delete(String id) {
        log.debug("Request to delete Firma : {}", id);
        return firmaRepository.deleteById(id);
    }
}
