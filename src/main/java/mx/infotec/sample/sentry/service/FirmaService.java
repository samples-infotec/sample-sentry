package mx.infotec.sample.sentry.service;

import java.util.List;
import mx.infotec.sample.sentry.service.dto.FirmaDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Interface for managing {@link mx.infotec.sample.sentry.domain.Firma}.
 */
public interface FirmaService {
    /**
     * Save a firma.
     *
     * @param firmaDTO the entity to save.
     * @return the persisted entity.
     */
    Mono<FirmaDTO> save(FirmaDTO firmaDTO);

    /**
     * Updates a firma.
     *
     * @param firmaDTO the entity to update.
     * @return the persisted entity.
     */
    Mono<FirmaDTO> update(FirmaDTO firmaDTO);

    /**
     * Partially updates a firma.
     *
     * @param firmaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Mono<FirmaDTO> partialUpdate(FirmaDTO firmaDTO);

    /**
     * Get all the firmas.
     *
     * @return the list of entities.
     */
    Flux<FirmaDTO> findAll();

    /**
     * Returns the number of firmas available.
     * @return the number of entities in the database.
     *
     */
    Mono<Long> countAll();

    /**
     * Get the "id" firma.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Mono<FirmaDTO> findOne(String id);

    /**
     * Delete the "id" firma.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    Mono<Void> delete(String id);
}
