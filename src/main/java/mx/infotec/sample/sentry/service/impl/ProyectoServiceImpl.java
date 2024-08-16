package mx.infotec.sample.sentry.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import mx.infotec.sample.sentry.domain.Proyecto;
import mx.infotec.sample.sentry.repository.ProyectoRepository;
import mx.infotec.sample.sentry.service.ProyectoService;
import mx.infotec.sample.sentry.service.dto.ProyectoDTO;
import mx.infotec.sample.sentry.service.mapper.ProyectoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link Proyecto}.
 */
@Service
public class ProyectoServiceImpl implements ProyectoService {

    private final Logger log = LoggerFactory.getLogger(ProyectoServiceImpl.class);

    private final ProyectoRepository proyectoRepository;

    private final ProyectoMapper proyectoMapper;

    public ProyectoServiceImpl(ProyectoRepository proyectoRepository, ProyectoMapper proyectoMapper) {
        this.proyectoRepository = proyectoRepository;
        this.proyectoMapper = proyectoMapper;
    }

    @Override
    public Mono<ProyectoDTO> save(ProyectoDTO proyectoDTO) {
        log.debug("Request to save Proyecto : {}", proyectoDTO);
        return proyectoRepository.save(proyectoMapper.toEntity(proyectoDTO)).map(proyectoMapper::toDto);
    }

    @Override
    public Mono<ProyectoDTO> update(ProyectoDTO proyectoDTO) {
        log.debug("Request to update Proyecto : {}", proyectoDTO);
        return proyectoRepository.save(proyectoMapper.toEntity(proyectoDTO)).map(proyectoMapper::toDto);
    }

    @Override
    public Mono<ProyectoDTO> partialUpdate(ProyectoDTO proyectoDTO) {
        log.debug("Request to partially update Proyecto : {}", proyectoDTO);

        return proyectoRepository
            .findById(proyectoDTO.getId())
            .map(existingProyecto -> {
                proyectoMapper.partialUpdate(existingProyecto, proyectoDTO);

                return existingProyecto;
            })
            .flatMap(proyectoRepository::save)
            .map(proyectoMapper::toDto);
    }

    @Override
    public Flux<ProyectoDTO> findAll() {
        log.debug("Request to get all Proyectos");
        return proyectoRepository.findAll().map(proyectoMapper::toDto);
    }

    public Mono<Long> countAll() {
        return proyectoRepository.count();
    }

    @Override
    public Mono<ProyectoDTO> findOne(String id) {
        log.debug("Request to get Proyecto : {}", id);
        return proyectoRepository.findById(id).map(proyectoMapper::toDto);
    }

    @Override
    public Mono<Void> delete(String id) {
        log.debug("Request to delete Proyecto : {}", id);
        return proyectoRepository.deleteById(id);
    }
}
