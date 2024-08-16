package mx.infotec.sample.sentry.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import java.time.Duration;
import java.util.List;
import java.util.UUID;
import mx.infotec.sample.sentry.IntegrationTest;
import mx.infotec.sample.sentry.domain.Firma;
import mx.infotec.sample.sentry.repository.FirmaRepository;
import mx.infotec.sample.sentry.service.dto.FirmaDTO;
import mx.infotec.sample.sentry.service.mapper.FirmaMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Integration tests for the {@link FirmaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class FirmaResourceIT {

    private static final String DEFAULT_ROL = "AAAAAAAAAA";
    private static final String UPDATED_ROL = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENTO_URI = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENTO_URI = "BBBBBBBBBB";

    private static final String DEFAULT_CERTIFICADO = "AAAAAAAAAA";
    private static final String UPDATED_CERTIFICADO = "BBBBBBBBBB";

    private static final String DEFAULT_RFC = "AAAAAAAAAA";
    private static final String UPDATED_RFC = "BBBBBBBBBB";

    private static final String DEFAULT_CURP = "AAAAAAAAAA";
    private static final String UPDATED_CURP = "BBBBBBBBBB";

    private static final String DEFAULT_SIGNATURE = "AAAAAAAAAA";
    private static final String UPDATED_SIGNATURE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String ENTITY_API_URL = "/api/firmas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private FirmaRepository firmaRepository;

    @Autowired
    private FirmaMapper firmaMapper;

    @Autowired
    private WebTestClient webTestClient;

    private Firma firma;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Firma createEntity() {
        Firma firma = new Firma()
            .rol(DEFAULT_ROL)
            .documentoUri(DEFAULT_DOCUMENTO_URI)
            .certificado(DEFAULT_CERTIFICADO)
            .rfc(DEFAULT_RFC)
            .curp(DEFAULT_CURP)
            .signature(DEFAULT_SIGNATURE)
            .active(DEFAULT_ACTIVE);
        return firma;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Firma createUpdatedEntity() {
        Firma firma = new Firma()
            .rol(UPDATED_ROL)
            .documentoUri(UPDATED_DOCUMENTO_URI)
            .certificado(UPDATED_CERTIFICADO)
            .rfc(UPDATED_RFC)
            .curp(UPDATED_CURP)
            .signature(UPDATED_SIGNATURE)
            .active(UPDATED_ACTIVE);
        return firma;
    }

    @BeforeEach
    public void initTest() {
        firmaRepository.deleteAll().block();
        firma = createEntity();
    }

    @Test
    void createFirma() throws Exception {
        int databaseSizeBeforeCreate = firmaRepository.findAll().collectList().block().size();
        // Create the Firma
        FirmaDTO firmaDTO = firmaMapper.toDto(firma);
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(firmaDTO))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the Firma in the database
        List<Firma> firmaList = firmaRepository.findAll().collectList().block();
        assertThat(firmaList).hasSize(databaseSizeBeforeCreate + 1);
        Firma testFirma = firmaList.get(firmaList.size() - 1);
        assertThat(testFirma.getRol()).isEqualTo(DEFAULT_ROL);
        assertThat(testFirma.getDocumentoUri()).isEqualTo(DEFAULT_DOCUMENTO_URI);
        assertThat(testFirma.getCertificado()).isEqualTo(DEFAULT_CERTIFICADO);
        assertThat(testFirma.getRfc()).isEqualTo(DEFAULT_RFC);
        assertThat(testFirma.getCurp()).isEqualTo(DEFAULT_CURP);
        assertThat(testFirma.getSignature()).isEqualTo(DEFAULT_SIGNATURE);
        assertThat(testFirma.getActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    void createFirmaWithExistingId() throws Exception {
        // Create the Firma with an existing ID
        firma.setId("existing_id");
        FirmaDTO firmaDTO = firmaMapper.toDto(firma);

        int databaseSizeBeforeCreate = firmaRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(firmaDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Firma in the database
        List<Firma> firmaList = firmaRepository.findAll().collectList().block();
        assertThat(firmaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkRolIsRequired() throws Exception {
        int databaseSizeBeforeTest = firmaRepository.findAll().collectList().block().size();
        // set the field null
        firma.setRol(null);

        // Create the Firma, which fails.
        FirmaDTO firmaDTO = firmaMapper.toDto(firma);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(firmaDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Firma> firmaList = firmaRepository.findAll().collectList().block();
        assertThat(firmaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkRfcIsRequired() throws Exception {
        int databaseSizeBeforeTest = firmaRepository.findAll().collectList().block().size();
        // set the field null
        firma.setRfc(null);

        // Create the Firma, which fails.
        FirmaDTO firmaDTO = firmaMapper.toDto(firma);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(firmaDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Firma> firmaList = firmaRepository.findAll().collectList().block();
        assertThat(firmaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = firmaRepository.findAll().collectList().block().size();
        // set the field null
        firma.setActive(null);

        // Create the Firma, which fails.
        FirmaDTO firmaDTO = firmaMapper.toDto(firma);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(firmaDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Firma> firmaList = firmaRepository.findAll().collectList().block();
        assertThat(firmaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllFirmasAsStream() {
        // Initialize the database
        firmaRepository.save(firma).block();

        List<Firma> firmaList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(FirmaDTO.class)
            .getResponseBody()
            .map(firmaMapper::toEntity)
            .filter(firma::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(firmaList).isNotNull();
        assertThat(firmaList).hasSize(1);
        Firma testFirma = firmaList.get(0);
        assertThat(testFirma.getRol()).isEqualTo(DEFAULT_ROL);
        assertThat(testFirma.getDocumentoUri()).isEqualTo(DEFAULT_DOCUMENTO_URI);
        assertThat(testFirma.getCertificado()).isEqualTo(DEFAULT_CERTIFICADO);
        assertThat(testFirma.getRfc()).isEqualTo(DEFAULT_RFC);
        assertThat(testFirma.getCurp()).isEqualTo(DEFAULT_CURP);
        assertThat(testFirma.getSignature()).isEqualTo(DEFAULT_SIGNATURE);
        assertThat(testFirma.getActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    void getAllFirmas() {
        // Initialize the database
        firmaRepository.save(firma).block();

        // Get all the firmaList
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
            .value(hasItem(firma.getId()))
            .jsonPath("$.[*].rol")
            .value(hasItem(DEFAULT_ROL))
            .jsonPath("$.[*].documentoUri")
            .value(hasItem(DEFAULT_DOCUMENTO_URI))
            .jsonPath("$.[*].certificado")
            .value(hasItem(DEFAULT_CERTIFICADO))
            .jsonPath("$.[*].rfc")
            .value(hasItem(DEFAULT_RFC))
            .jsonPath("$.[*].curp")
            .value(hasItem(DEFAULT_CURP))
            .jsonPath("$.[*].signature")
            .value(hasItem(DEFAULT_SIGNATURE))
            .jsonPath("$.[*].active")
            .value(hasItem(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    void getFirma() {
        // Initialize the database
        firmaRepository.save(firma).block();

        // Get the firma
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, firma.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(firma.getId()))
            .jsonPath("$.rol")
            .value(is(DEFAULT_ROL))
            .jsonPath("$.documentoUri")
            .value(is(DEFAULT_DOCUMENTO_URI))
            .jsonPath("$.certificado")
            .value(is(DEFAULT_CERTIFICADO))
            .jsonPath("$.rfc")
            .value(is(DEFAULT_RFC))
            .jsonPath("$.curp")
            .value(is(DEFAULT_CURP))
            .jsonPath("$.signature")
            .value(is(DEFAULT_SIGNATURE))
            .jsonPath("$.active")
            .value(is(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    void getNonExistingFirma() {
        // Get the firma
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingFirma() throws Exception {
        // Initialize the database
        firmaRepository.save(firma).block();

        int databaseSizeBeforeUpdate = firmaRepository.findAll().collectList().block().size();

        // Update the firma
        Firma updatedFirma = firmaRepository.findById(firma.getId()).block();
        updatedFirma
            .rol(UPDATED_ROL)
            .documentoUri(UPDATED_DOCUMENTO_URI)
            .certificado(UPDATED_CERTIFICADO)
            .rfc(UPDATED_RFC)
            .curp(UPDATED_CURP)
            .signature(UPDATED_SIGNATURE)
            .active(UPDATED_ACTIVE);
        FirmaDTO firmaDTO = firmaMapper.toDto(updatedFirma);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, firmaDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(firmaDTO))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Firma in the database
        List<Firma> firmaList = firmaRepository.findAll().collectList().block();
        assertThat(firmaList).hasSize(databaseSizeBeforeUpdate);
        Firma testFirma = firmaList.get(firmaList.size() - 1);
        assertThat(testFirma.getRol()).isEqualTo(UPDATED_ROL);
        assertThat(testFirma.getDocumentoUri()).isEqualTo(UPDATED_DOCUMENTO_URI);
        assertThat(testFirma.getCertificado()).isEqualTo(UPDATED_CERTIFICADO);
        assertThat(testFirma.getRfc()).isEqualTo(UPDATED_RFC);
        assertThat(testFirma.getCurp()).isEqualTo(UPDATED_CURP);
        assertThat(testFirma.getSignature()).isEqualTo(UPDATED_SIGNATURE);
        assertThat(testFirma.getActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    void putNonExistingFirma() throws Exception {
        int databaseSizeBeforeUpdate = firmaRepository.findAll().collectList().block().size();
        firma.setId(UUID.randomUUID().toString());

        // Create the Firma
        FirmaDTO firmaDTO = firmaMapper.toDto(firma);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, firmaDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(firmaDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Firma in the database
        List<Firma> firmaList = firmaRepository.findAll().collectList().block();
        assertThat(firmaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchFirma() throws Exception {
        int databaseSizeBeforeUpdate = firmaRepository.findAll().collectList().block().size();
        firma.setId(UUID.randomUUID().toString());

        // Create the Firma
        FirmaDTO firmaDTO = firmaMapper.toDto(firma);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(firmaDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Firma in the database
        List<Firma> firmaList = firmaRepository.findAll().collectList().block();
        assertThat(firmaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamFirma() throws Exception {
        int databaseSizeBeforeUpdate = firmaRepository.findAll().collectList().block().size();
        firma.setId(UUID.randomUUID().toString());

        // Create the Firma
        FirmaDTO firmaDTO = firmaMapper.toDto(firma);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(firmaDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Firma in the database
        List<Firma> firmaList = firmaRepository.findAll().collectList().block();
        assertThat(firmaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateFirmaWithPatch() throws Exception {
        // Initialize the database
        firmaRepository.save(firma).block();

        int databaseSizeBeforeUpdate = firmaRepository.findAll().collectList().block().size();

        // Update the firma using partial update
        Firma partialUpdatedFirma = new Firma();
        partialUpdatedFirma.setId(firma.getId());

        partialUpdatedFirma.documentoUri(UPDATED_DOCUMENTO_URI).certificado(UPDATED_CERTIFICADO).rfc(UPDATED_RFC).curp(UPDATED_CURP);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedFirma.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedFirma))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Firma in the database
        List<Firma> firmaList = firmaRepository.findAll().collectList().block();
        assertThat(firmaList).hasSize(databaseSizeBeforeUpdate);
        Firma testFirma = firmaList.get(firmaList.size() - 1);
        assertThat(testFirma.getRol()).isEqualTo(DEFAULT_ROL);
        assertThat(testFirma.getDocumentoUri()).isEqualTo(UPDATED_DOCUMENTO_URI);
        assertThat(testFirma.getCertificado()).isEqualTo(UPDATED_CERTIFICADO);
        assertThat(testFirma.getRfc()).isEqualTo(UPDATED_RFC);
        assertThat(testFirma.getCurp()).isEqualTo(UPDATED_CURP);
        assertThat(testFirma.getSignature()).isEqualTo(DEFAULT_SIGNATURE);
        assertThat(testFirma.getActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    void fullUpdateFirmaWithPatch() throws Exception {
        // Initialize the database
        firmaRepository.save(firma).block();

        int databaseSizeBeforeUpdate = firmaRepository.findAll().collectList().block().size();

        // Update the firma using partial update
        Firma partialUpdatedFirma = new Firma();
        partialUpdatedFirma.setId(firma.getId());

        partialUpdatedFirma
            .rol(UPDATED_ROL)
            .documentoUri(UPDATED_DOCUMENTO_URI)
            .certificado(UPDATED_CERTIFICADO)
            .rfc(UPDATED_RFC)
            .curp(UPDATED_CURP)
            .signature(UPDATED_SIGNATURE)
            .active(UPDATED_ACTIVE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedFirma.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedFirma))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Firma in the database
        List<Firma> firmaList = firmaRepository.findAll().collectList().block();
        assertThat(firmaList).hasSize(databaseSizeBeforeUpdate);
        Firma testFirma = firmaList.get(firmaList.size() - 1);
        assertThat(testFirma.getRol()).isEqualTo(UPDATED_ROL);
        assertThat(testFirma.getDocumentoUri()).isEqualTo(UPDATED_DOCUMENTO_URI);
        assertThat(testFirma.getCertificado()).isEqualTo(UPDATED_CERTIFICADO);
        assertThat(testFirma.getRfc()).isEqualTo(UPDATED_RFC);
        assertThat(testFirma.getCurp()).isEqualTo(UPDATED_CURP);
        assertThat(testFirma.getSignature()).isEqualTo(UPDATED_SIGNATURE);
        assertThat(testFirma.getActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    void patchNonExistingFirma() throws Exception {
        int databaseSizeBeforeUpdate = firmaRepository.findAll().collectList().block().size();
        firma.setId(UUID.randomUUID().toString());

        // Create the Firma
        FirmaDTO firmaDTO = firmaMapper.toDto(firma);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, firmaDTO.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(firmaDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Firma in the database
        List<Firma> firmaList = firmaRepository.findAll().collectList().block();
        assertThat(firmaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchFirma() throws Exception {
        int databaseSizeBeforeUpdate = firmaRepository.findAll().collectList().block().size();
        firma.setId(UUID.randomUUID().toString());

        // Create the Firma
        FirmaDTO firmaDTO = firmaMapper.toDto(firma);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(firmaDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Firma in the database
        List<Firma> firmaList = firmaRepository.findAll().collectList().block();
        assertThat(firmaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamFirma() throws Exception {
        int databaseSizeBeforeUpdate = firmaRepository.findAll().collectList().block().size();
        firma.setId(UUID.randomUUID().toString());

        // Create the Firma
        FirmaDTO firmaDTO = firmaMapper.toDto(firma);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(firmaDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Firma in the database
        List<Firma> firmaList = firmaRepository.findAll().collectList().block();
        assertThat(firmaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteFirma() {
        // Initialize the database
        firmaRepository.save(firma).block();

        int databaseSizeBeforeDelete = firmaRepository.findAll().collectList().block().size();

        // Delete the firma
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, firma.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<Firma> firmaList = firmaRepository.findAll().collectList().block();
        assertThat(firmaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
