package mx.infotec.sample.sentry.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import mx.infotec.sample.sentry.IntegrationTest;
import mx.infotec.sample.sentry.domain.Proyecto;
import mx.infotec.sample.sentry.repository.ProyectoRepository;
import mx.infotec.sample.sentry.service.dto.ProyectoDTO;
import mx.infotec.sample.sentry.service.mapper.ProyectoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Integration tests for the {@link ProyectoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class ProyectoResourceIT {

    private static final String DEFAULT_DETALLE = "AAAAAAAAAA";
    private static final String UPDATED_DETALLE = "BBBBBBBBBB";

    private static final String DEFAULT_MODALIDAD = "AAAAAAAAAA";
    private static final String UPDATED_MODALIDAD = "BBBBBBBBBB";

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_SOLICITUD_CLAVE = "AAAAAAAAAA";
    private static final String UPDATED_SOLICITUD_CLAVE = "BBBBBBBBBB";

    private static final Instant DEFAULT_SOLICITUD_FECHA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SOLICITUD_FECHA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_COMPLETO = false;
    private static final Boolean UPDATED_COMPLETO = true;

    private static final String ENTITY_API_URL = "/api/proyectos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ProyectoRepository proyectoRepository;

    @Autowired
    private ProyectoMapper proyectoMapper;

    @Autowired
    private WebTestClient webTestClient;

    private Proyecto proyecto;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Proyecto createEntity() {
        Proyecto proyecto = new Proyecto()
            .detalle(DEFAULT_DETALLE)
            .modalidad(DEFAULT_MODALIDAD)
            .titulo(DEFAULT_TITULO)
            .solicitudClave(DEFAULT_SOLICITUD_CLAVE)
            .solicitudFecha(DEFAULT_SOLICITUD_FECHA)
            .completo(DEFAULT_COMPLETO);
        return proyecto;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Proyecto createUpdatedEntity() {
        Proyecto proyecto = new Proyecto()
            .detalle(UPDATED_DETALLE)
            .modalidad(UPDATED_MODALIDAD)
            .titulo(UPDATED_TITULO)
            .solicitudClave(UPDATED_SOLICITUD_CLAVE)
            .solicitudFecha(UPDATED_SOLICITUD_FECHA)
            .completo(UPDATED_COMPLETO);
        return proyecto;
    }

    @BeforeEach
    public void initTest() {
        proyectoRepository.deleteAll().block();
        proyecto = createEntity();
    }

    @Test
    void createProyecto() throws Exception {
        int databaseSizeBeforeCreate = proyectoRepository.findAll().collectList().block().size();
        // Create the Proyecto
        ProyectoDTO proyectoDTO = proyectoMapper.toDto(proyecto);
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(proyectoDTO))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the Proyecto in the database
        List<Proyecto> proyectoList = proyectoRepository.findAll().collectList().block();
        assertThat(proyectoList).hasSize(databaseSizeBeforeCreate + 1);
        Proyecto testProyecto = proyectoList.get(proyectoList.size() - 1);
        assertThat(testProyecto.getDetalle()).isEqualTo(DEFAULT_DETALLE);
        assertThat(testProyecto.getModalidad()).isEqualTo(DEFAULT_MODALIDAD);
        assertThat(testProyecto.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testProyecto.getSolicitudClave()).isEqualTo(DEFAULT_SOLICITUD_CLAVE);
        assertThat(testProyecto.getSolicitudFecha()).isEqualTo(DEFAULT_SOLICITUD_FECHA);
        assertThat(testProyecto.getCompleto()).isEqualTo(DEFAULT_COMPLETO);
    }

    @Test
    void createProyectoWithExistingId() throws Exception {
        // Create the Proyecto with an existing ID
        proyecto.setId("existing_id");
        ProyectoDTO proyectoDTO = proyectoMapper.toDto(proyecto);

        int databaseSizeBeforeCreate = proyectoRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(proyectoDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Proyecto in the database
        List<Proyecto> proyectoList = proyectoRepository.findAll().collectList().block();
        assertThat(proyectoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkModalidadIsRequired() throws Exception {
        int databaseSizeBeforeTest = proyectoRepository.findAll().collectList().block().size();
        // set the field null
        proyecto.setModalidad(null);

        // Create the Proyecto, which fails.
        ProyectoDTO proyectoDTO = proyectoMapper.toDto(proyecto);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(proyectoDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Proyecto> proyectoList = proyectoRepository.findAll().collectList().block();
        assertThat(proyectoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkTituloIsRequired() throws Exception {
        int databaseSizeBeforeTest = proyectoRepository.findAll().collectList().block().size();
        // set the field null
        proyecto.setTitulo(null);

        // Create the Proyecto, which fails.
        ProyectoDTO proyectoDTO = proyectoMapper.toDto(proyecto);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(proyectoDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Proyecto> proyectoList = proyectoRepository.findAll().collectList().block();
        assertThat(proyectoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkCompletoIsRequired() throws Exception {
        int databaseSizeBeforeTest = proyectoRepository.findAll().collectList().block().size();
        // set the field null
        proyecto.setCompleto(null);

        // Create the Proyecto, which fails.
        ProyectoDTO proyectoDTO = proyectoMapper.toDto(proyecto);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(proyectoDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Proyecto> proyectoList = proyectoRepository.findAll().collectList().block();
        assertThat(proyectoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllProyectosAsStream() {
        // Initialize the database
        proyectoRepository.save(proyecto).block();

        List<Proyecto> proyectoList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(ProyectoDTO.class)
            .getResponseBody()
            .map(proyectoMapper::toEntity)
            .filter(proyecto::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(proyectoList).isNotNull();
        assertThat(proyectoList).hasSize(1);
        Proyecto testProyecto = proyectoList.get(0);
        assertThat(testProyecto.getDetalle()).isEqualTo(DEFAULT_DETALLE);
        assertThat(testProyecto.getModalidad()).isEqualTo(DEFAULT_MODALIDAD);
        assertThat(testProyecto.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testProyecto.getSolicitudClave()).isEqualTo(DEFAULT_SOLICITUD_CLAVE);
        assertThat(testProyecto.getSolicitudFecha()).isEqualTo(DEFAULT_SOLICITUD_FECHA);
        assertThat(testProyecto.getCompleto()).isEqualTo(DEFAULT_COMPLETO);
    }

    @Test
    void getAllProyectos() {
        // Initialize the database
        proyectoRepository.save(proyecto).block();

        // Get all the proyectoList
        webTestClient
            .get()
            .uri(ENTITY_API_URL + "?sort=id,desc")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.[*].id")
            .value(hasItem(proyecto.getId()))
            .jsonPath("$.[*].detalle")
            .value(hasItem(DEFAULT_DETALLE))
            .jsonPath("$.[*].modalidad")
            .value(hasItem(DEFAULT_MODALIDAD))
            .jsonPath("$.[*].titulo")
            .value(hasItem(DEFAULT_TITULO))
            .jsonPath("$.[*].solicitudClave")
            .value(hasItem(DEFAULT_SOLICITUD_CLAVE))
            .jsonPath("$.[*].solicitudFecha")
            .value(hasItem(DEFAULT_SOLICITUD_FECHA.toString()))
            .jsonPath("$.[*].completo")
            .value(hasItem(DEFAULT_COMPLETO.booleanValue()));
    }

    @Test
    void getProyecto() {
        // Initialize the database
        proyectoRepository.save(proyecto).block();

        // Get the proyecto
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, proyecto.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(proyecto.getId()))
            .jsonPath("$.detalle")
            .value(is(DEFAULT_DETALLE))
            .jsonPath("$.modalidad")
            .value(is(DEFAULT_MODALIDAD))
            .jsonPath("$.titulo")
            .value(is(DEFAULT_TITULO))
            .jsonPath("$.solicitudClave")
            .value(is(DEFAULT_SOLICITUD_CLAVE))
            .jsonPath("$.solicitudFecha")
            .value(is(DEFAULT_SOLICITUD_FECHA.toString()))
            .jsonPath("$.completo")
            .value(is(DEFAULT_COMPLETO.booleanValue()));
    }

    @Test
    void getNonExistingProyecto() {
        // Get the proyecto
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingProyecto() throws Exception {
        // Initialize the database
        proyectoRepository.save(proyecto).block();

        int databaseSizeBeforeUpdate = proyectoRepository.findAll().collectList().block().size();

        // Update the proyecto
        Proyecto updatedProyecto = proyectoRepository.findById(proyecto.getId()).block();
        updatedProyecto
            .detalle(UPDATED_DETALLE)
            .modalidad(UPDATED_MODALIDAD)
            .titulo(UPDATED_TITULO)
            .solicitudClave(UPDATED_SOLICITUD_CLAVE)
            .solicitudFecha(UPDATED_SOLICITUD_FECHA)
            .completo(UPDATED_COMPLETO);
        ProyectoDTO proyectoDTO = proyectoMapper.toDto(updatedProyecto);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, proyectoDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(proyectoDTO))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Proyecto in the database
        List<Proyecto> proyectoList = proyectoRepository.findAll().collectList().block();
        assertThat(proyectoList).hasSize(databaseSizeBeforeUpdate);
        Proyecto testProyecto = proyectoList.get(proyectoList.size() - 1);
        assertThat(testProyecto.getDetalle()).isEqualTo(UPDATED_DETALLE);
        assertThat(testProyecto.getModalidad()).isEqualTo(UPDATED_MODALIDAD);
        assertThat(testProyecto.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testProyecto.getSolicitudClave()).isEqualTo(UPDATED_SOLICITUD_CLAVE);
        assertThat(testProyecto.getSolicitudFecha()).isEqualTo(UPDATED_SOLICITUD_FECHA);
        assertThat(testProyecto.getCompleto()).isEqualTo(UPDATED_COMPLETO);
    }

    @Test
    void putNonExistingProyecto() throws Exception {
        int databaseSizeBeforeUpdate = proyectoRepository.findAll().collectList().block().size();
        proyecto.setId(UUID.randomUUID().toString());

        // Create the Proyecto
        ProyectoDTO proyectoDTO = proyectoMapper.toDto(proyecto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, proyectoDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(proyectoDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Proyecto in the database
        List<Proyecto> proyectoList = proyectoRepository.findAll().collectList().block();
        assertThat(proyectoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchProyecto() throws Exception {
        int databaseSizeBeforeUpdate = proyectoRepository.findAll().collectList().block().size();
        proyecto.setId(UUID.randomUUID().toString());

        // Create the Proyecto
        ProyectoDTO proyectoDTO = proyectoMapper.toDto(proyecto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(proyectoDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Proyecto in the database
        List<Proyecto> proyectoList = proyectoRepository.findAll().collectList().block();
        assertThat(proyectoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamProyecto() throws Exception {
        int databaseSizeBeforeUpdate = proyectoRepository.findAll().collectList().block().size();
        proyecto.setId(UUID.randomUUID().toString());

        // Create the Proyecto
        ProyectoDTO proyectoDTO = proyectoMapper.toDto(proyecto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(proyectoDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Proyecto in the database
        List<Proyecto> proyectoList = proyectoRepository.findAll().collectList().block();
        assertThat(proyectoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateProyectoWithPatch() throws Exception {
        // Initialize the database
        proyectoRepository.save(proyecto).block();

        int databaseSizeBeforeUpdate = proyectoRepository.findAll().collectList().block().size();

        // Update the proyecto using partial update
        Proyecto partialUpdatedProyecto = new Proyecto();
        partialUpdatedProyecto.setId(proyecto.getId());

        partialUpdatedProyecto.modalidad(UPDATED_MODALIDAD).completo(UPDATED_COMPLETO);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedProyecto.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedProyecto))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Proyecto in the database
        List<Proyecto> proyectoList = proyectoRepository.findAll().collectList().block();
        assertThat(proyectoList).hasSize(databaseSizeBeforeUpdate);
        Proyecto testProyecto = proyectoList.get(proyectoList.size() - 1);
        assertThat(testProyecto.getDetalle()).isEqualTo(DEFAULT_DETALLE);
        assertThat(testProyecto.getModalidad()).isEqualTo(UPDATED_MODALIDAD);
        assertThat(testProyecto.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testProyecto.getSolicitudClave()).isEqualTo(DEFAULT_SOLICITUD_CLAVE);
        assertThat(testProyecto.getSolicitudFecha()).isEqualTo(DEFAULT_SOLICITUD_FECHA);
        assertThat(testProyecto.getCompleto()).isEqualTo(UPDATED_COMPLETO);
    }

    @Test
    void fullUpdateProyectoWithPatch() throws Exception {
        // Initialize the database
        proyectoRepository.save(proyecto).block();

        int databaseSizeBeforeUpdate = proyectoRepository.findAll().collectList().block().size();

        // Update the proyecto using partial update
        Proyecto partialUpdatedProyecto = new Proyecto();
        partialUpdatedProyecto.setId(proyecto.getId());

        partialUpdatedProyecto
            .detalle(UPDATED_DETALLE)
            .modalidad(UPDATED_MODALIDAD)
            .titulo(UPDATED_TITULO)
            .solicitudClave(UPDATED_SOLICITUD_CLAVE)
            .solicitudFecha(UPDATED_SOLICITUD_FECHA)
            .completo(UPDATED_COMPLETO);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedProyecto.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedProyecto))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Proyecto in the database
        List<Proyecto> proyectoList = proyectoRepository.findAll().collectList().block();
        assertThat(proyectoList).hasSize(databaseSizeBeforeUpdate);
        Proyecto testProyecto = proyectoList.get(proyectoList.size() - 1);
        assertThat(testProyecto.getDetalle()).isEqualTo(UPDATED_DETALLE);
        assertThat(testProyecto.getModalidad()).isEqualTo(UPDATED_MODALIDAD);
        assertThat(testProyecto.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testProyecto.getSolicitudClave()).isEqualTo(UPDATED_SOLICITUD_CLAVE);
        assertThat(testProyecto.getSolicitudFecha()).isEqualTo(UPDATED_SOLICITUD_FECHA);
        assertThat(testProyecto.getCompleto()).isEqualTo(UPDATED_COMPLETO);
    }

    @Test
    void patchNonExistingProyecto() throws Exception {
        int databaseSizeBeforeUpdate = proyectoRepository.findAll().collectList().block().size();
        proyecto.setId(UUID.randomUUID().toString());

        // Create the Proyecto
        ProyectoDTO proyectoDTO = proyectoMapper.toDto(proyecto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, proyectoDTO.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(proyectoDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Proyecto in the database
        List<Proyecto> proyectoList = proyectoRepository.findAll().collectList().block();
        assertThat(proyectoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchProyecto() throws Exception {
        int databaseSizeBeforeUpdate = proyectoRepository.findAll().collectList().block().size();
        proyecto.setId(UUID.randomUUID().toString());

        // Create the Proyecto
        ProyectoDTO proyectoDTO = proyectoMapper.toDto(proyecto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(proyectoDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Proyecto in the database
        List<Proyecto> proyectoList = proyectoRepository.findAll().collectList().block();
        assertThat(proyectoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamProyecto() throws Exception {
        int databaseSizeBeforeUpdate = proyectoRepository.findAll().collectList().block().size();
        proyecto.setId(UUID.randomUUID().toString());

        // Create the Proyecto
        ProyectoDTO proyectoDTO = proyectoMapper.toDto(proyecto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(proyectoDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Proyecto in the database
        List<Proyecto> proyectoList = proyectoRepository.findAll().collectList().block();
        assertThat(proyectoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteProyecto() {
        // Initialize the database
        proyectoRepository.save(proyecto).block();

        int databaseSizeBeforeDelete = proyectoRepository.findAll().collectList().block().size();

        // Delete the proyecto
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, proyecto.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<Proyecto> proyectoList = proyectoRepository.findAll().collectList().block();
        assertThat(proyectoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
