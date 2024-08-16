package mx.infotec.sample.sentry.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import mx.infotec.sample.sentry.repository.ConvocatoriaRepository;
import mx.infotec.sample.sentry.service.ConvocatoriaService;
import mx.infotec.sample.sentry.service.dto.ConvocatoriaDTO;
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
 * REST controller for managing {@link mx.infotec.sample.sentry.domain.Convocatoria}.
 */
@RestController
@RequestMapping("/api")
public class ConvocatoriaResource {

    private final Logger log = LoggerFactory.getLogger(ConvocatoriaResource.class);

    private static final String ENTITY_NAME = "convocatoria";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConvocatoriaService convocatoriaService;

    private final ConvocatoriaRepository convocatoriaRepository;

    public ConvocatoriaResource(ConvocatoriaService convocatoriaService, ConvocatoriaRepository convocatoriaRepository) {
        this.convocatoriaService = convocatoriaService;
        this.convocatoriaRepository = convocatoriaRepository;
    }

    /**
     * {@code POST  /convocatorias} : Create a new convocatoria.
     *
     * @param convocatoriaDTO the convocatoriaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new convocatoriaDTO, or with status {@code 400 (Bad Request)} if the convocatoria has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/convocatorias")
    public Mono<ResponseEntity<ConvocatoriaDTO>> createConvocatoria(@Valid @RequestBody ConvocatoriaDTO convocatoriaDTO)
        throws URISyntaxException {
        log.debug("REST request to save Convocatoria : {}", convocatoriaDTO);
        if (convocatoriaDTO.getId() != null) {
            throw new BadRequestAlertException("A new convocatoria cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return convocatoriaService
            .save(convocatoriaDTO)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/convocatorias/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /convocatorias/:id} : Updates an existing convocatoria.
     *
     * @param id the id of the convocatoriaDTO to save.
     * @param convocatoriaDTO the convocatoriaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated convocatoriaDTO,
     * or with status {@code 400 (Bad Request)} if the convocatoriaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the convocatoriaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/convocatorias/{id}")
    public Mono<ResponseEntity<ConvocatoriaDTO>> updateConvocatoria(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody ConvocatoriaDTO convocatoriaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Convocatoria : {}, {}", id, convocatoriaDTO);
        if (convocatoriaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, convocatoriaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return convocatoriaRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return convocatoriaService
                    .update(convocatoriaDTO)
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
     * {@code PATCH  /convocatorias/:id} : Partial updates given fields of an existing convocatoria, field will ignore if it is null
     *
     * @param id the id of the convocatoriaDTO to save.
     * @param convocatoriaDTO the convocatoriaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated convocatoriaDTO,
     * or with status {@code 400 (Bad Request)} if the convocatoriaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the convocatoriaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the convocatoriaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/convocatorias/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<ConvocatoriaDTO>> partialUpdateConvocatoria(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody ConvocatoriaDTO convocatoriaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Convocatoria partially : {}, {}", id, convocatoriaDTO);
        if (convocatoriaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, convocatoriaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return convocatoriaRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<ConvocatoriaDTO> result = convocatoriaService.partialUpdate(convocatoriaDTO);

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
     * {@code GET  /convocatorias} : get all the convocatorias.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of convocatorias in body.
     */
    @GetMapping("/convocatorias")
    public Mono<List<ConvocatoriaDTO>> getAllConvocatorias() {
        log.debug("REST request to get all Convocatorias");
        return convocatoriaService.findAll().collectList();
    }

    /**
     * {@code GET  /convocatorias} : get all the convocatorias as a stream.
     * @return the {@link Flux} of convocatorias.
     */
    @GetMapping(value = "/convocatorias", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<ConvocatoriaDTO> getAllConvocatoriasAsStream() {
        log.debug("REST request to get all Convocatorias as a stream");
        return convocatoriaService.findAll();
    }

    /**
     * {@code GET  /convocatorias/:id} : get the "id" convocatoria.
     *
     * @param id the id of the convocatoriaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the convocatoriaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/convocatorias/{id}")
    public Mono<ResponseEntity<ConvocatoriaDTO>> getConvocatoria(@PathVariable String id) {
        log.debug("REST request to get Convocatoria : {}", id);
        Mono<ConvocatoriaDTO> convocatoriaDTO = convocatoriaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(convocatoriaDTO);
    }

    /**
     * {@code DELETE  /convocatorias/:id} : delete the "id" convocatoria.
     *
     * @param id the id of the convocatoriaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/convocatorias/{id}")
    public Mono<ResponseEntity<Void>> deleteConvocatoria(@PathVariable String id) {
        log.debug("REST request to delete Convocatoria : {}", id);
        return convocatoriaService
            .delete(id)
            .then(
                Mono.just(
                    ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build()
                )
            );
    }
}
