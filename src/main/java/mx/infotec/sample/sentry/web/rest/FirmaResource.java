package mx.infotec.sample.sentry.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import mx.infotec.sample.sentry.repository.FirmaRepository;
import mx.infotec.sample.sentry.service.FirmaService;
import mx.infotec.sample.sentry.service.dto.FirmaDTO;
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
 * REST controller for managing {@link mx.infotec.sample.sentry.domain.Firma}.
 */
@RestController
@RequestMapping("/api")
public class FirmaResource {

    private final Logger log = LoggerFactory.getLogger(FirmaResource.class);

    private static final String ENTITY_NAME = "firma";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FirmaService firmaService;

    private final FirmaRepository firmaRepository;

    public FirmaResource(FirmaService firmaService, FirmaRepository firmaRepository) {
        this.firmaService = firmaService;
        this.firmaRepository = firmaRepository;
    }

    /**
     * {@code POST  /firmas} : Create a new firma.
     *
     * @param firmaDTO the firmaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new firmaDTO, or with status {@code 400 (Bad Request)} if the firma has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/firmas")
    public Mono<ResponseEntity<FirmaDTO>> createFirma(@Valid @RequestBody FirmaDTO firmaDTO) throws URISyntaxException {
        log.debug("REST request to save Firma : {}", firmaDTO);
        if (firmaDTO.getId() != null) {
            throw new BadRequestAlertException("A new firma cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return firmaService
            .save(firmaDTO)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/firmas/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /firmas/:id} : Updates an existing firma.
     *
     * @param id the id of the firmaDTO to save.
     * @param firmaDTO the firmaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated firmaDTO,
     * or with status {@code 400 (Bad Request)} if the firmaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the firmaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/firmas/{id}")
    public Mono<ResponseEntity<FirmaDTO>> updateFirma(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody FirmaDTO firmaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Firma : {}, {}", id, firmaDTO);
        if (firmaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, firmaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return firmaRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return firmaService
                    .update(firmaDTO)
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
     * {@code PATCH  /firmas/:id} : Partial updates given fields of an existing firma, field will ignore if it is null
     *
     * @param id the id of the firmaDTO to save.
     * @param firmaDTO the firmaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated firmaDTO,
     * or with status {@code 400 (Bad Request)} if the firmaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the firmaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the firmaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/firmas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<FirmaDTO>> partialUpdateFirma(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody FirmaDTO firmaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Firma partially : {}, {}", id, firmaDTO);
        if (firmaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, firmaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return firmaRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<FirmaDTO> result = firmaService.partialUpdate(firmaDTO);

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
     * {@code GET  /firmas} : get all the firmas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of firmas in body.
     */
    @GetMapping("/firmas")
    public Mono<List<FirmaDTO>> getAllFirmas() {
        log.debug("REST request to get all Firmas");
        return firmaService.findAll().collectList();
    }

    /**
     * {@code GET  /firmas} : get all the firmas as a stream.
     * @return the {@link Flux} of firmas.
     */
    @GetMapping(value = "/firmas", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<FirmaDTO> getAllFirmasAsStream() {
        log.debug("REST request to get all Firmas as a stream");
        return firmaService.findAll();
    }

    /**
     * {@code GET  /firmas/:id} : get the "id" firma.
     *
     * @param id the id of the firmaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the firmaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/firmas/{id}")
    public Mono<ResponseEntity<FirmaDTO>> getFirma(@PathVariable String id) {
        log.debug("REST request to get Firma : {}", id);
        Mono<FirmaDTO> firmaDTO = firmaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(firmaDTO);
    }

    /**
     * {@code DELETE  /firmas/:id} : delete the "id" firma.
     *
     * @param id the id of the firmaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/firmas/{id}")
    public Mono<ResponseEntity<Void>> deleteFirma(@PathVariable String id) {
        log.debug("REST request to delete Firma : {}", id);
        return firmaService
            .delete(id)
            .then(
                Mono.just(
                    ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build()
                )
            );
    }
}
