package mx.infotec.sample.sentry.service;

import java.util.List;
import mx.infotec.sample.sentry.service.dto.DocumentoDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Interface for managing {@link mx.infotec.sample.sentry.domain.Documento}.
 */
public interface DocumentoService {
    /**
     * Save a documento.
     *
     * @param documentoDTO the entity to save.
     * @return the persisted entity.
     */
    Mono<DocumentoDTO> save(DocumentoDTO documentoDTO);

    /**
     * Updates a documento.
     *
     * @param documentoDTO the entity to update.
     * @return the persisted entity.
     */
    Mono<DocumentoDTO> update(DocumentoDTO documentoDTO);

    /**
     * Partially updates a documento.
     *
     * @param documentoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Mono<DocumentoDTO> partialUpdate(DocumentoDTO documentoDTO);

    /**
     * Get all the documentos.
     *
     * @return the list of entities.
     */
    Flux<DocumentoDTO> findAll();

    /**
     * Returns the number of documentos available.
     * @return the number of entities in the database.
     *
     */
    Mono<Long> countAll();

    /**
     * Get the "id" documento.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Mono<DocumentoDTO> findOne(String id);

    /**
     * Delete the "id" documento.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    Mono<Void> delete(String id);
}
