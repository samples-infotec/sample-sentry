package mx.infotec.sample.sentry.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import mx.infotec.sample.sentry.repository.DocumentoRepository;
import mx.infotec.sample.sentry.service.DocumentoService;
import mx.infotec.sample.sentry.service.dto.DocumentoDTO;
import mx.infotec.sample.sentry.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.reactive.ResponseUtil;

/**
 * REST controller for managing {@link mx.infotec.sample.sentry.domain.Documento}.
 */
@RestController
@RequestMapping("/api")
public class DocumentoResource {

    private final Logger log = LoggerFactory.getLogger(DocumentoResource.class);

    private static final String ENTITY_NAME = "documento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DocumentoService documentoService;

    private final DocumentoRepository documentoRepository;

    public DocumentoResource(DocumentoService documentoService, DocumentoRepository documentoRepository) {
        this.documentoService = documentoService;
        this.documentoRepository = documentoRepository;
    }

    /**
     * {@code POST  /documentos} : Create a new documento.
     *
     * @param documentoDTO the documentoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new documentoDTO, or with status {@code 400 (Bad Request)} if the documento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/documentos")
    public Mono<ResponseEntity<DocumentoDTO>> createDocumento(@Valid @RequestBody DocumentoDTO documentoDTO) throws URISyntaxException {
        log.debug("REST request to save Documento : {}", documentoDTO);
        if (documentoDTO.getId() != null) {
            throw new BadRequestAlertException("A new documento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return documentoService
            .save(documentoDTO)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/documentos/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /documentos/:id} : Updates an existing documento.
     *
     * @param id the id of the documentoDTO to save.
     * @param documentoDTO the documentoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated documentoDTO,
     * or with status {@code 400 (Bad Request)} if the documentoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the documentoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/documentos/{id}")
    public Mono<ResponseEntity<DocumentoDTO>> updateDocumento(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody DocumentoDTO documentoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Documento : {}, {}", id, documentoDTO);
        if (documentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, documentoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return documentoRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return documentoService
                    .update(documentoDTO)
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(result ->
                        ResponseEntity
                            .ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId()))
                            .body(result)
                    );
            });
    }

    /**
     * {@code PATCH  /documentos/:id} : Partial updates given fields of an existing documento, field will ignore if it is null
     *
     * @param id the id of the documentoDTO to save.
     * @param documentoDTO the documentoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated documentoDTO,
     * or with status {@code 400 (Bad Request)} if the documentoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the documentoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the documentoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/documentos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<DocumentoDTO>> partialUpdateDocumento(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody DocumentoDTO documentoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Documento partially : {}, {}", id, documentoDTO);
        if (documentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, documentoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return documentoRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<DocumentoDTO> result = documentoService.partialUpdate(documentoDTO);

                return result
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(res ->
                        ResponseEntity
                            .ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, res.getId()))
                            .body(res)
                    );
            });
    }

    /**
     * {@code GET  /documentos} : get all the documentos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of documentos in body.
     */
    @GetMapping("/documentos")
    public Mono<List<DocumentoDTO>> getAllDocumentos() {
        log.debug("REST request to get all Documentos");
        return documentoService.findAll().collectList();
    }

    /**
     * {@code GET  /documentos} : get all the documentos as a stream.
     * @return the {@link Flux} of documentos.
     */
    @GetMapping(value = "/documentos", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<DocumentoDTO> getAllDocumentosAsStream() {
        log.debug("REST request to get all Documentos as a stream");
        return documentoService.findAll();
    }

    /**
     * {@code GET  /documentos/:id} : get the "id" documento.
     *
     * @param id the id of the documentoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the documentoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/documentos/{id}")
    public Mono<ResponseEntity<DocumentoDTO>> getDocumento(@PathVariable String id) {
        log.debug("REST request to get Documento : {}", id);
        Mono<DocumentoDTO> documentoDTO = documentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(documentoDTO);
    }

    /**
     * {@code DELETE  /documentos/:id} : delete the "id" documento.
     *
     * @param id the id of the documentoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/documentos/{id}")
    public Mono<ResponseEntity<Void>> deleteDocumento(@PathVariable String id) {
        log.debug("REST request to delete Documento : {}", id);
        return documentoService
            .delete(id)
            .then(
                Mono.just(
                    ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build()
                )
            );
    }
}
