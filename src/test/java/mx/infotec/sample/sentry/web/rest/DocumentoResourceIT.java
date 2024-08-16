package mx.infotec.sample.sentry.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import java.time.Duration;
import java.util.List;
import java.util.UUID;
import mx.infotec.sample.sentry.IntegrationTest;
import mx.infotec.sample.sentry.domain.Documento;
import mx.infotec.sample.sentry.repository.DocumentoRepository;
import mx.infotec.sample.sentry.service.dto.DocumentoDTO;
import mx.infotec.sample.sentry.service.mapper.DocumentoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Integration tests for the {@link DocumentoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class DocumentoResourceIT {

    private static final String DEFAULT_URI = "AAAAAAAAAA";
    private static final String UPDATED_URI = "BBBBBBBBBB";

    private static final String DEFAULT_DATA = "AAAAAAAAAA";
    private static final String UPDATED_DATA = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/documentos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private DocumentoRepository documentoRepository;

    @Autowired
    private DocumentoMapper documentoMapper;

    @Autowired
    private WebTestClient webTestClient;

    private Documento documento;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Documento createEntity() {
        Documento documento = new Documento().uri(DEFAULT_URI).data(DEFAULT_DATA);
        return documento;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Documento createUpdatedEntity() {
        Documento documento = new Documento().uri(UPDATED_URI).data(UPDATED_DATA);
        return documento;
    }

    @BeforeEach
    public void initTest() {
        documentoRepository.deleteAll().block();
        documento = createEntity();
    }

    @Test
    void createDocumento() throws Exception {
        int databaseSizeBeforeCreate = documentoRepository.findAll().collectList().block().size();
        // Create the Documento
        DocumentoDTO documentoDTO = documentoMapper.toDto(documento);
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(documentoDTO))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the Documento in the database
        List<Documento> documentoList = documentoRepository.findAll().collectList().block();
        assertThat(documentoList).hasSize(databaseSizeBeforeCreate + 1);
        Documento testDocumento = documentoList.get(documentoList.size() - 1);
        assertThat(testDocumento.getUri()).isEqualTo(DEFAULT_URI);
        assertThat(testDocumento.getData()).isEqualTo(DEFAULT_DATA);
    }

    @Test
    void createDocumentoWithExistingId() throws Exception {
        // Create the Documento with an existing ID
        documento.setId("existing_id");
        DocumentoDTO documentoDTO = documentoMapper.toDto(documento);

        int databaseSizeBeforeCreate = documentoRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(documentoDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Documento in the database
        List<Documento> documentoList = documentoRepository.findAll().collectList().block();
        assertThat(documentoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkUriIsRequired() throws Exception {
        int databaseSizeBeforeTest = documentoRepository.findAll().collectList().block().size();
        // set the field null
        documento.setUri(null);

        // Create the Documento, which fails.
        DocumentoDTO documentoDTO = documentoMapper.toDto(documento);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(documentoDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Documento> documentoList = documentoRepository.findAll().collectList().block();
        assertThat(documentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkDataIsRequired() throws Exception {
        int databaseSizeBeforeTest = documentoRepository.findAll().collectList().block().size();
        // set the field null
        documento.setData(null);

        // Create the Documento, which fails.
        DocumentoDTO documentoDTO = documentoMapper.toDto(documento);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(documentoDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Documento> documentoList = documentoRepository.findAll().collectList().block();
        assertThat(documentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllDocumentosAsStream() {
        // Initialize the database
        documentoRepository.save(documento).block();

        List<Documento> documentoList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(DocumentoDTO.class)
            .getResponseBody()
            .map(documentoMapper::toEntity)
            .filter(documento::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(documentoList).isNotNull();
        assertThat(documentoList).hasSize(1);
        Documento testDocumento = documentoList.get(0);
        assertThat(testDocumento.getUri()).isEqualTo(DEFAULT_URI);
        assertThat(testDocumento.getData()).isEqualTo(DEFAULT_DATA);
    }

    @Test
    void getAllDocumentos() {
        // Initialize the database
        documentoRepository.save(documento).block();

        // Get all the documentoList
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
            .value(hasItem(documento.getId()))
            .jsonPath("$.[*].uri")
            .value(hasItem(DEFAULT_URI))
            .jsonPath("$.[*].data")
            .value(hasItem(DEFAULT_DATA));
    }

    @Test
    void getDocumento() {
        // Initialize the database
        documentoRepository.save(documento).block();

        // Get the documento
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, documento.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(documento.getId()))
            .jsonPath("$.uri")
            .value(is(DEFAULT_URI))
            .jsonPath("$.data")
            .value(is(DEFAULT_DATA));
    }

    @Test
    void getNonExistingDocumento() {
        // Get the documento
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingDocumento() throws Exception {
        // Initialize the database
        documentoRepository.save(documento).block();

        int databaseSizeBeforeUpdate = documentoRepository.findAll().collectList().block().size();

        // Update the documento
        Documento updatedDocumento = documentoRepository.findById(documento.getId()).block();
        updatedDocumento.uri(UPDATED_URI).data(UPDATED_DATA);
        DocumentoDTO documentoDTO = documentoMapper.toDto(updatedDocumento);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, documentoDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(documentoDTO))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Documento in the database
        List<Documento> documentoList = documentoRepository.findAll().collectList().block();
        assertThat(documentoList).hasSize(databaseSizeBeforeUpdate);
        Documento testDocumento = documentoList.get(documentoList.size() - 1);
        assertThat(testDocumento.getUri()).isEqualTo(UPDATED_URI);
        assertThat(testDocumento.getData()).isEqualTo(UPDATED_DATA);
    }

    @Test
    void putNonExistingDocumento() throws Exception {
        int databaseSizeBeforeUpdate = documentoRepository.findAll().collectList().block().size();
        documento.setId(UUID.randomUUID().toString());

        // Create the Documento
        DocumentoDTO documentoDTO = documentoMapper.toDto(documento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, documentoDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(documentoDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Documento in the database
        List<Documento> documentoList = documentoRepository.findAll().collectList().block();
        assertThat(documentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchDocumento() throws Exception {
        int databaseSizeBeforeUpdate = documentoRepository.findAll().collectList().block().size();
        documento.setId(UUID.randomUUID().toString());

        // Create the Documento
        DocumentoDTO documentoDTO = documentoMapper.toDto(documento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(documentoDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Documento in the database
        List<Documento> documentoList = documentoRepository.findAll().collectList().block();
        assertThat(documentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamDocumento() throws Exception {
        int databaseSizeBeforeUpdate = documentoRepository.findAll().collectList().block().size();
        documento.setId(UUID.randomUUID().toString());

        // Create the Documento
        DocumentoDTO documentoDTO = documentoMapper.toDto(documento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(documentoDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Documento in the database
        List<Documento> documentoList = documentoRepository.findAll().collectList().block();
        assertThat(documentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateDocumentoWithPatch() throws Exception {
        // Initialize the database
        documentoRepository.save(documento).block();

        int databaseSizeBeforeUpdate = documentoRepository.findAll().collectList().block().size();

        // Update the documento using partial update
        Documento partialUpdatedDocumento = new Documento();
        partialUpdatedDocumento.setId(documento.getId());

        partialUpdatedDocumento.data(UPDATED_DATA);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedDocumento.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedDocumento))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Documento in the database
        List<Documento> documentoList = documentoRepository.findAll().collectList().block();
        assertThat(documentoList).hasSize(databaseSizeBeforeUpdate);
        Documento testDocumento = documentoList.get(documentoList.size() - 1);
        assertThat(testDocumento.getUri()).isEqualTo(DEFAULT_URI);
        assertThat(testDocumento.getData()).isEqualTo(UPDATED_DATA);
    }

    @Test
    void fullUpdateDocumentoWithPatch() throws Exception {
        // Initialize the database
        documentoRepository.save(documento).block();

        int databaseSizeBeforeUpdate = documentoRepository.findAll().collectList().block().size();

        // Update the documento using partial update
        Documento partialUpdatedDocumento = new Documento();
        partialUpdatedDocumento.setId(documento.getId());

        partialUpdatedDocumento.uri(UPDATED_URI).data(UPDATED_DATA);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedDocumento.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedDocumento))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Documento in the database
        List<Documento> documentoList = documentoRepository.findAll().collectList().block();
        assertThat(documentoList).hasSize(databaseSizeBeforeUpdate);
        Documento testDocumento = documentoList.get(documentoList.size() - 1);
        assertThat(testDocumento.getUri()).isEqualTo(UPDATED_URI);
        assertThat(testDocumento.getData()).isEqualTo(UPDATED_DATA);
    }

    @Test
    void patchNonExistingDocumento() throws Exception {
        int databaseSizeBeforeUpdate = documentoRepository.findAll().collectList().block().size();
        documento.setId(UUID.randomUUID().toString());

        // Create the Documento
        DocumentoDTO documentoDTO = documentoMapper.toDto(documento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, documentoDTO.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(documentoDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Documento in the database
        List<Documento> documentoList = documentoRepository.findAll().collectList().block();
        assertThat(documentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchDocumento() throws Exception {
        int databaseSizeBeforeUpdate = documentoRepository.findAll().collectList().block().size();
        documento.setId(UUID.randomUUID().toString());

        // Create the Documento
        DocumentoDTO documentoDTO = documentoMapper.toDto(documento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(documentoDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Documento in the database
        List<Documento> documentoList = documentoRepository.findAll().collectList().block();
        assertThat(documentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamDocumento() throws Exception {
        int databaseSizeBeforeUpdate = documentoRepository.findAll().collectList().block().size();
        documento.setId(UUID.randomUUID().toString());

        // Create the Documento
        DocumentoDTO documentoDTO = documentoMapper.toDto(documento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(documentoDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Documento in the database
        List<Documento> documentoList = documentoRepository.findAll().collectList().block();
        assertThat(documentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteDocumento() {
        // Initialize the database
        documentoRepository.save(documento).block();

        int databaseSizeBeforeDelete = documentoRepository.findAll().collectList().block().size();

        // Delete the documento
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, documento.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<Documento> documentoList = documentoRepository.findAll().collectList().block();
        assertThat(documentoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
