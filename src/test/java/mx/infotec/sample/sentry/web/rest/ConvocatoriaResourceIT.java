package mx.infotec.sample.sentry.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;
import mx.infotec.sample.sentry.IntegrationTest;
import mx.infotec.sample.sentry.domain.Convocatoria;
import mx.infotec.sample.sentry.repository.ConvocatoriaRepository;
import mx.infotec.sample.sentry.service.dto.ConvocatoriaDTO;
import mx.infotec.sample.sentry.service.mapper.ConvocatoriaMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Integration tests for the {@link ConvocatoriaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class ConvocatoriaResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final Integer DEFAULT_ANIO = 1960;
    private static final Integer UPDATED_ANIO = 1961;

    private static final LocalDate DEFAULT_FECHA_CREACION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_CREACION = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/convocatorias";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ConvocatoriaRepository convocatoriaRepository;

    @Autowired
    private ConvocatoriaMapper convocatoriaMapper;

    @Autowired
    private WebTestClient webTestClient;

    private Convocatoria convocatoria;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Convocatoria createEntity() {
        Convocatoria convocatoria = new Convocatoria().nombre(DEFAULT_NOMBRE).anio(DEFAULT_ANIO).fechaCreacion(DEFAULT_FECHA_CREACION);
        return convocatoria;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Convocatoria createUpdatedEntity() {
        Convocatoria convocatoria = new Convocatoria().nombre(UPDATED_NOMBRE).anio(UPDATED_ANIO).fechaCreacion(UPDATED_FECHA_CREACION);
        return convocatoria;
    }

    @BeforeEach
    public void initTest() {
        convocatoriaRepository.deleteAll().block();
        convocatoria = createEntity();
    }

    @Test
    void createConvocatoria() throws Exception {
        int databaseSizeBeforeCreate = convocatoriaRepository.findAll().collectList().block().size();
        // Create the Convocatoria
        ConvocatoriaDTO convocatoriaDTO = convocatoriaMapper.toDto(convocatoria);
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(convocatoriaDTO))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the Convocatoria in the database
        List<Convocatoria> convocatoriaList = convocatoriaRepository.findAll().collectList().block();
        assertThat(convocatoriaList).hasSize(databaseSizeBeforeCreate + 1);
        Convocatoria testConvocatoria = convocatoriaList.get(convocatoriaList.size() - 1);
        assertThat(testConvocatoria.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testConvocatoria.getAnio()).isEqualTo(DEFAULT_ANIO);
        assertThat(testConvocatoria.getFechaCreacion()).isEqualTo(DEFAULT_FECHA_CREACION);
    }

    @Test
    void createConvocatoriaWithExistingId() throws Exception {
        // Create the Convocatoria with an existing ID
        convocatoria.setId("existing_id");
        ConvocatoriaDTO convocatoriaDTO = convocatoriaMapper.toDto(convocatoria);

        int databaseSizeBeforeCreate = convocatoriaRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(convocatoriaDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Convocatoria in the database
        List<Convocatoria> convocatoriaList = convocatoriaRepository.findAll().collectList().block();
        assertThat(convocatoriaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = convocatoriaRepository.findAll().collectList().block().size();
        // set the field null
        convocatoria.setNombre(null);

        // Create the Convocatoria, which fails.
        ConvocatoriaDTO convocatoriaDTO = convocatoriaMapper.toDto(convocatoria);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(convocatoriaDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Convocatoria> convocatoriaList = convocatoriaRepository.findAll().collectList().block();
        assertThat(convocatoriaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllConvocatoriasAsStream() {
        // Initialize the database
        convocatoriaRepository.save(convocatoria).block();

        List<Convocatoria> convocatoriaList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(ConvocatoriaDTO.class)
            .getResponseBody()
            .map(convocatoriaMapper::toEntity)
            .filter(convocatoria::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(convocatoriaList).isNotNull();
        assertThat(convocatoriaList).hasSize(1);
        Convocatoria testConvocatoria = convocatoriaList.get(0);
        assertThat(testConvocatoria.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testConvocatoria.getAnio()).isEqualTo(DEFAULT_ANIO);
        assertThat(testConvocatoria.getFechaCreacion()).isEqualTo(DEFAULT_FECHA_CREACION);
    }

    @Test
    void getAllConvocatorias() {
        // Initialize the database
        convocatoriaRepository.save(convocatoria).block();

        // Get all the convocatoriaList
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
            .value(hasItem(convocatoria.getId()))
            .jsonPath("$.[*].nombre")
            .value(hasItem(DEFAULT_NOMBRE))
            .jsonPath("$.[*].anio")
            .value(hasItem(DEFAULT_ANIO))
            .jsonPath("$.[*].fechaCreacion")
            .value(hasItem(DEFAULT_FECHA_CREACION.toString()));
    }

    @Test
    void getConvocatoria() {
        // Initialize the database
        convocatoriaRepository.save(convocatoria).block();

        // Get the convocatoria
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, convocatoria.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(convocatoria.getId()))
            .jsonPath("$.nombre")
            .value(is(DEFAULT_NOMBRE))
            .jsonPath("$.anio")
            .value(is(DEFAULT_ANIO))
            .jsonPath("$.fechaCreacion")
            .value(is(DEFAULT_FECHA_CREACION.toString()));
    }

    @Test
    void getNonExistingConvocatoria() {
        // Get the convocatoria
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingConvocatoria() throws Exception {
        // Initialize the database
        convocatoriaRepository.save(convocatoria).block();

        int databaseSizeBeforeUpdate = convocatoriaRepository.findAll().collectList().block().size();

        // Update the convocatoria
        Convocatoria updatedConvocatoria = convocatoriaRepository.findById(convocatoria.getId()).block();
        updatedConvocatoria.nombre(UPDATED_NOMBRE).anio(UPDATED_ANIO).fechaCreacion(UPDATED_FECHA_CREACION);
        ConvocatoriaDTO convocatoriaDTO = convocatoriaMapper.toDto(updatedConvocatoria);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, convocatoriaDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(convocatoriaDTO))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Convocatoria in the database
        List<Convocatoria> convocatoriaList = convocatoriaRepository.findAll().collectList().block();
        assertThat(convocatoriaList).hasSize(databaseSizeBeforeUpdate);
        Convocatoria testConvocatoria = convocatoriaList.get(convocatoriaList.size() - 1);
        assertThat(testConvocatoria.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testConvocatoria.getAnio()).isEqualTo(UPDATED_ANIO);
        assertThat(testConvocatoria.getFechaCreacion()).isEqualTo(UPDATED_FECHA_CREACION);
    }

    @Test
    void putNonExistingConvocatoria() throws Exception {
        int databaseSizeBeforeUpdate = convocatoriaRepository.findAll().collectList().block().size();
        convocatoria.setId(UUID.randomUUID().toString());

        // Create the Convocatoria
        ConvocatoriaDTO convocatoriaDTO = convocatoriaMapper.toDto(convocatoria);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, convocatoriaDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(convocatoriaDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Convocatoria in the database
        List<Convocatoria> convocatoriaList = convocatoriaRepository.findAll().collectList().block();
        assertThat(convocatoriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchConvocatoria() throws Exception {
        int databaseSizeBeforeUpdate = convocatoriaRepository.findAll().collectList().block().size();
        convocatoria.setId(UUID.randomUUID().toString());

        // Create the Convocatoria
        ConvocatoriaDTO convocatoriaDTO = convocatoriaMapper.toDto(convocatoria);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(convocatoriaDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Convocatoria in the database
        List<Convocatoria> convocatoriaList = convocatoriaRepository.findAll().collectList().block();
        assertThat(convocatoriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamConvocatoria() throws Exception {
        int databaseSizeBeforeUpdate = convocatoriaRepository.findAll().collectList().block().size();
        convocatoria.setId(UUID.randomUUID().toString());

        // Create the Convocatoria
        ConvocatoriaDTO convocatoriaDTO = convocatoriaMapper.toDto(convocatoria);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(convocatoriaDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Convocatoria in the database
        List<Convocatoria> convocatoriaList = convocatoriaRepository.findAll().collectList().block();
        assertThat(convocatoriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateConvocatoriaWithPatch() throws Exception {
        // Initialize the database
        convocatoriaRepository.save(convocatoria).block();

        int databaseSizeBeforeUpdate = convocatoriaRepository.findAll().collectList().block().size();

        // Update the convocatoria using partial update
        Convocatoria partialUpdatedConvocatoria = new Convocatoria();
        partialUpdatedConvocatoria.setId(convocatoria.getId());

        partialUpdatedConvocatoria.fechaCreacion(UPDATED_FECHA_CREACION);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedConvocatoria.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedConvocatoria))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Convocatoria in the database
        List<Convocatoria> convocatoriaList = convocatoriaRepository.findAll().collectList().block();
        assertThat(convocatoriaList).hasSize(databaseSizeBeforeUpdate);
        Convocatoria testConvocatoria = convocatoriaList.get(convocatoriaList.size() - 1);
        assertThat(testConvocatoria.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testConvocatoria.getAnio()).isEqualTo(DEFAULT_ANIO);
        assertThat(testConvocatoria.getFechaCreacion()).isEqualTo(UPDATED_FECHA_CREACION);
    }

    @Test
    void fullUpdateConvocatoriaWithPatch() throws Exception {
        // Initialize the database
        convocatoriaRepository.save(convocatoria).block();

        int databaseSizeBeforeUpdate = convocatoriaRepository.findAll().collectList().block().size();

        // Update the convocatoria using partial update
        Convocatoria partialUpdatedConvocatoria = new Convocatoria();
        partialUpdatedConvocatoria.setId(convocatoria.getId());

        partialUpdatedConvocatoria.nombre(UPDATED_NOMBRE).anio(UPDATED_ANIO).fechaCreacion(UPDATED_FECHA_CREACION);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedConvocatoria.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedConvocatoria))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Convocatoria in the database
        List<Convocatoria> convocatoriaList = convocatoriaRepository.findAll().collectList().block();
        assertThat(convocatoriaList).hasSize(databaseSizeBeforeUpdate);
        Convocatoria testConvocatoria = convocatoriaList.get(convocatoriaList.size() - 1);
        assertThat(testConvocatoria.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testConvocatoria.getAnio()).isEqualTo(UPDATED_ANIO);
        assertThat(testConvocatoria.getFechaCreacion()).isEqualTo(UPDATED_FECHA_CREACION);
    }

    @Test
    void patchNonExistingConvocatoria() throws Exception {
        int databaseSizeBeforeUpdate = convocatoriaRepository.findAll().collectList().block().size();
        convocatoria.setId(UUID.randomUUID().toString());

        // Create the Convocatoria
        ConvocatoriaDTO convocatoriaDTO = convocatoriaMapper.toDto(convocatoria);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, convocatoriaDTO.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(convocatoriaDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Convocatoria in the database
        List<Convocatoria> convocatoriaList = convocatoriaRepository.findAll().collectList().block();
        assertThat(convocatoriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchConvocatoria() throws Exception {
        int databaseSizeBeforeUpdate = convocatoriaRepository.findAll().collectList().block().size();
        convocatoria.setId(UUID.randomUUID().toString());

        // Create the Convocatoria
        ConvocatoriaDTO convocatoriaDTO = convocatoriaMapper.toDto(convocatoria);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(convocatoriaDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Convocatoria in the database
        List<Convocatoria> convocatoriaList = convocatoriaRepository.findAll().collectList().block();
        assertThat(convocatoriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamConvocatoria() throws Exception {
        int databaseSizeBeforeUpdate = convocatoriaRepository.findAll().collectList().block().size();
        convocatoria.setId(UUID.randomUUID().toString());

        // Create the Convocatoria
        ConvocatoriaDTO convocatoriaDTO = convocatoriaMapper.toDto(convocatoria);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(convocatoriaDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Convocatoria in the database
        List<Convocatoria> convocatoriaList = convocatoriaRepository.findAll().collectList().block();
        assertThat(convocatoriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteConvocatoria() {
        // Initialize the database
        convocatoriaRepository.save(convocatoria).block();

        int databaseSizeBeforeDelete = convocatoriaRepository.findAll().collectList().block().size();

        // Delete the convocatoria
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, convocatoria.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<Convocatoria> convocatoriaList = convocatoriaRepository.findAll().collectList().block();
        assertThat(convocatoriaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
