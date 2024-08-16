package mx.infotec.sample.sentry.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import mx.infotec.sample.sentry.repository.ProyectoRepository;
import mx.infotec.sample.sentry.service.ProyectoService;
import mx.infotec.sample.sentry.service.dto.ProyectoDTO;
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
 * REST controller for managing {@link mx.infotec.sample.sentry.domain.Proyecto}.
 */
@RestController
@RequestMapping("/api")
public class ProyectoResource {

    private final Logger log = LoggerFactory.getLogger(ProyectoResource.class);

    private static final String ENTITY_NAME = "proyecto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProyectoService proyectoService;

    private final ProyectoRepository proyectoRepository;

    public ProyectoResource(ProyectoService proyectoService, ProyectoRepository proyectoRepository) {
        this.proyectoService = proyectoService;
        this.proyectoRepository = proyectoRepository;
    }

    /**
     * {@code POST  /proyectos} : Create a new proyecto.
     *
     * @param proyectoDTO the proyectoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new proyectoDTO, or with status {@code 400 (Bad Request)} if the proyecto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/proyectos")
    public Mono<ResponseEntity<ProyectoDTO>> createProyecto(@Valid @RequestBody ProyectoDTO proyectoDTO) throws URISyntaxException {
        log.debug("REST request to save Proyecto : {}", proyectoDTO);
        if (proyectoDTO.getId() != null) {
            throw new BadRequestAlertException("A new proyecto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return proyectoService
            .save(proyectoDTO)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/proyectos/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /proyectos/:id} : Updates an existing proyecto.
     *
     * @param id the id of the proyectoDTO to save.
     * @param proyectoDTO the proyectoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated proyectoDTO,
     * or with status {@code 400 (Bad Request)} if the proyectoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the proyectoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/proyectos/{id}")
    public Mono<ResponseEntity<ProyectoDTO>> updateProyecto(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody ProyectoDTO proyectoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Proyecto : {}, {}", id, proyectoDTO);
        if (proyectoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, proyectoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return proyectoRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return proyectoService
                    .update(proyectoDTO)
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
     * {@code PATCH  /proyectos/:id} : Partial updates given fields of an existing proyecto, field will ignore if it is null
     *
     * @param id the id of the proyectoDTO to save.
     * @param proyectoDTO the proyectoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated proyectoDTO,
     * or with status {@code 400 (Bad Request)} if the proyectoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the proyectoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the proyectoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/proyectos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<ProyectoDTO>> partialUpdateProyecto(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody ProyectoDTO proyectoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Proyecto partially : {}, {}", id, proyectoDTO);
        if (proyectoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, proyectoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return proyectoRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<ProyectoDTO> result = proyectoService.partialUpdate(proyectoDTO);

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
     * {@code GET  /proyectos} : get all the proyectos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of proyectos in body.
     */
    @GetMapping("/proyectos")
    public Mono<List<ProyectoDTO>> getAllProyectos() {
        log.debug("REST request to get all Proyectos");
        return proyectoService.findAll().collectList();
    }

    /**
     * {@code GET  /proyectos} : get all the proyectos as a stream.
     * @return the {@link Flux} of proyectos.
     */
    @GetMapping(value = "/proyectos", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<ProyectoDTO> getAllProyectosAsStream() {
        log.debug("REST request to get all Proyectos as a stream");
        return proyectoService.findAll();
    }

    /**
     * {@code GET  /proyectos/:id} : get the "id" proyecto.
     *
     * @param id the id of the proyectoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the proyectoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/proyectos/{id}")
    public Mono<ResponseEntity<ProyectoDTO>> getProyecto(@PathVariable String id) {
        log.debug("REST request to get Proyecto : {}", id);
        Mono<ProyectoDTO> proyectoDTO = proyectoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(proyectoDTO);
    }

    /**
     * {@code DELETE  /proyectos/:id} : delete the "id" proyecto.
     *
     * @param id the id of the proyectoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/proyectos/{id}")
    public Mono<ResponseEntity<Void>> deleteProyecto(@PathVariable String id) {
        log.debug("REST request to delete Proyecto : {}", id);
        return proyectoService
            .delete(id)
            .then(
                Mono.just(
                    ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build()
                )
            );
    }
}
