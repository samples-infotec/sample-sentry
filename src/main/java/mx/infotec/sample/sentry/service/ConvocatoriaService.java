package mx.infotec.sample.sentry.service;

import java.util.List;
import mx.infotec.sample.sentry.service.dto.ConvocatoriaDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Interface for managing {@link mx.infotec.sample.sentry.domain.Convocatoria}.
 */
public interface ConvocatoriaService {
    /**
     * Save a convocatoria.
     *
     * @param convocatoriaDTO the entity to save.
     * @return the persisted entity.
     */
    Mono<ConvocatoriaDTO> save(ConvocatoriaDTO convocatoriaDTO);

    /**
     * Updates a convocatoria.
     *
     * @param convocatoriaDTO the entity to update.
     * @return the persisted entity.
     */
    Mono<ConvocatoriaDTO> update(ConvocatoriaDTO convocatoriaDTO);

    /**
     * Partially updates a convocatoria.
     *
     * @param convocatoriaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Mono<ConvocatoriaDTO> partialUpdate(ConvocatoriaDTO convocatoriaDTO);

    /**
     * Get all the convocatorias.
     *
     * @return the list of entities.
     */
    Flux<ConvocatoriaDTO> findAll();

    /**
     * Returns the number of convocatorias available.
     * @return the number of entities in the database.
     *
     */
    Mono<Long> countAll();

    /**
     * Get the "id" convocatoria.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Mono<ConvocatoriaDTO> findOne(String id);

    /**
     * Delete the "id" convocatoria.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    Mono<Void> delete(String id);
}
