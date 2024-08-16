package mx.infotec.sample.sentry.service;

import java.util.List;
import mx.infotec.sample.sentry.service.dto.ProyectoDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Interface for managing {@link mx.infotec.sample.sentry.domain.Proyecto}.
 */
public interface ProyectoService {
    /**
     * Save a proyecto.
     *
     * @param proyectoDTO the entity to save.
     * @return the persisted entity.
     */
    Mono<ProyectoDTO> save(ProyectoDTO proyectoDTO);

    /**
     * Updates a proyecto.
     *
     * @param proyectoDTO the entity to update.
     * @return the persisted entity.
     */
    Mono<ProyectoDTO> update(ProyectoDTO proyectoDTO);

    /**
     * Partially updates a proyecto.
     *
     * @param proyectoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Mono<ProyectoDTO> partialUpdate(ProyectoDTO proyectoDTO);

    /**
     * Get all the proyectos.
     *
     * @return the list of entities.
     */
    Flux<ProyectoDTO> findAll();

    /**
     * Returns the number of proyectos available.
     * @return the number of entities in the database.
     *
     */
    Mono<Long> countAll();

    /**
     * Get the "id" proyecto.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Mono<ProyectoDTO> findOne(String id);

    /**
     * Delete the "id" proyecto.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    Mono<Void> delete(String id);
}
