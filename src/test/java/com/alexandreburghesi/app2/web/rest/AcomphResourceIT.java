package com.alexandreburghesi.app2.web.rest;

import static com.alexandreburghesi.app2.domain.AcomphAsserts.*;
import static com.alexandreburghesi.app2.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;

import com.alexandreburghesi.app2.IntegrationTest;
import com.alexandreburghesi.app2.domain.Acomph;
import com.alexandreburghesi.app2.repository.AcomphRepository;
import com.alexandreburghesi.app2.repository.EntityManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Integration tests for the {@link AcomphResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class AcomphResourceIT {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_VAZAO_DEFLUENTE_LIDO = 1D;
    private static final Double UPDATED_VAZAO_DEFLUENTE_LIDO = 2D;

    private static final Double DEFAULT_VAZAO_DEFLUENTE_CONSOLIDADO = 1D;
    private static final Double UPDATED_VAZAO_DEFLUENTE_CONSOLIDADO = 2D;

    private static final Double DEFAULT_VAZAO_AFLUENTE_LIDO = 1D;
    private static final Double UPDATED_VAZAO_AFLUENTE_LIDO = 2D;

    private static final Double DEFAULT_VAZAO_AFLUENTE_CONSOLIDADO = 1D;
    private static final Double UPDATED_VAZAO_AFLUENTE_CONSOLIDADO = 2D;

    private static final Double DEFAULT_VAZAO_INCREMENTAL_CONSOLIDADO = 1D;
    private static final Double UPDATED_VAZAO_INCREMENTAL_CONSOLIDADO = 2D;

    private static final Double DEFAULT_VAZAO_NATURAL_CONSOLIDADO = 1D;
    private static final Double UPDATED_VAZAO_NATURAL_CONSOLIDADO = 2D;

    private static final Double DEFAULT_NIVEL_RESERVATORIO_LIDO = 1D;
    private static final Double UPDATED_NIVEL_RESERVATORIO_LIDO = 2D;

    private static final Double DEFAULT_NIVEL_RESERVATORIO_CONSOLIDADO = 1D;
    private static final Double UPDATED_NIVEL_RESERVATORIO_CONSOLIDADO = 2D;

    private static final LocalDate DEFAULT_DATA_PUBLICACAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_PUBLICACAO = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/acomphs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AcomphRepository acomphRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private Acomph acomph;

    private Acomph insertedAcomph;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Acomph createEntity() {
        return new Acomph()
            .date(DEFAULT_DATE)
            .vazaoDefluenteLido(DEFAULT_VAZAO_DEFLUENTE_LIDO)
            .vazaoDefluenteConsolidado(DEFAULT_VAZAO_DEFLUENTE_CONSOLIDADO)
            .vazaoAfluenteLido(DEFAULT_VAZAO_AFLUENTE_LIDO)
            .vazaoAfluenteConsolidado(DEFAULT_VAZAO_AFLUENTE_CONSOLIDADO)
            .vazaoIncrementalConsolidado(DEFAULT_VAZAO_INCREMENTAL_CONSOLIDADO)
            .vazaoNaturalConsolidado(DEFAULT_VAZAO_NATURAL_CONSOLIDADO)
            .nivelReservatorioLido(DEFAULT_NIVEL_RESERVATORIO_LIDO)
            .nivelReservatorioConsolidado(DEFAULT_NIVEL_RESERVATORIO_CONSOLIDADO)
            .dataPublicacao(DEFAULT_DATA_PUBLICACAO);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Acomph createUpdatedEntity() {
        return new Acomph()
            .date(UPDATED_DATE)
            .vazaoDefluenteLido(UPDATED_VAZAO_DEFLUENTE_LIDO)
            .vazaoDefluenteConsolidado(UPDATED_VAZAO_DEFLUENTE_CONSOLIDADO)
            .vazaoAfluenteLido(UPDATED_VAZAO_AFLUENTE_LIDO)
            .vazaoAfluenteConsolidado(UPDATED_VAZAO_AFLUENTE_CONSOLIDADO)
            .vazaoIncrementalConsolidado(UPDATED_VAZAO_INCREMENTAL_CONSOLIDADO)
            .vazaoNaturalConsolidado(UPDATED_VAZAO_NATURAL_CONSOLIDADO)
            .nivelReservatorioLido(UPDATED_NIVEL_RESERVATORIO_LIDO)
            .nivelReservatorioConsolidado(UPDATED_NIVEL_RESERVATORIO_CONSOLIDADO)
            .dataPublicacao(UPDATED_DATA_PUBLICACAO);
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(Acomph.class).block();
        } catch (Exception e) {
            // It can fail, if other entities are still referring this - it will be removed later.
        }
    }

    @BeforeEach
    public void setupCsrf() {
        webTestClient = webTestClient.mutateWith(csrf());
    }

    @BeforeEach
    public void initTest() {
        acomph = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedAcomph != null) {
            acomphRepository.delete(insertedAcomph).block();
            insertedAcomph = null;
        }
        deleteEntities(em);
    }

    @Test
    void createAcomph() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Acomph
        var returnedAcomph = webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(acomph))
            .exchange()
            .expectStatus()
            .isCreated()
            .expectBody(Acomph.class)
            .returnResult()
            .getResponseBody();

        // Validate the Acomph in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAcomphUpdatableFieldsEquals(returnedAcomph, getPersistedAcomph(returnedAcomph));

        insertedAcomph = returnedAcomph;
    }

    @Test
    void createAcomphWithExistingId() throws Exception {
        // Create the Acomph with an existing ID
        acomph.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(acomph))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Acomph in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void getAllAcomphsAsStream() {
        // Initialize the database
        acomphRepository.save(acomph).block();

        List<Acomph> acomphList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(Acomph.class)
            .getResponseBody()
            .filter(acomph::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(acomphList).isNotNull();
        assertThat(acomphList).hasSize(1);
        Acomph testAcomph = acomphList.get(0);

        // Test fails because reactive api returns an empty object instead of null
        // assertAcomphAllPropertiesEquals(acomph, testAcomph);
        assertAcomphUpdatableFieldsEquals(acomph, testAcomph);
    }

    @Test
    void getAllAcomphs() {
        // Initialize the database
        insertedAcomph = acomphRepository.save(acomph).block();

        // Get all the acomphList
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
            .value(hasItem(acomph.getId().intValue()))
            .jsonPath("$.[*].date")
            .value(hasItem(DEFAULT_DATE.toString()))
            .jsonPath("$.[*].vazaoDefluenteLido")
            .value(hasItem(DEFAULT_VAZAO_DEFLUENTE_LIDO))
            .jsonPath("$.[*].vazaoDefluenteConsolidado")
            .value(hasItem(DEFAULT_VAZAO_DEFLUENTE_CONSOLIDADO))
            .jsonPath("$.[*].vazaoAfluenteLido")
            .value(hasItem(DEFAULT_VAZAO_AFLUENTE_LIDO))
            .jsonPath("$.[*].vazaoAfluenteConsolidado")
            .value(hasItem(DEFAULT_VAZAO_AFLUENTE_CONSOLIDADO))
            .jsonPath("$.[*].vazaoIncrementalConsolidado")
            .value(hasItem(DEFAULT_VAZAO_INCREMENTAL_CONSOLIDADO))
            .jsonPath("$.[*].vazaoNaturalConsolidado")
            .value(hasItem(DEFAULT_VAZAO_NATURAL_CONSOLIDADO))
            .jsonPath("$.[*].nivelReservatorioLido")
            .value(hasItem(DEFAULT_NIVEL_RESERVATORIO_LIDO))
            .jsonPath("$.[*].nivelReservatorioConsolidado")
            .value(hasItem(DEFAULT_NIVEL_RESERVATORIO_CONSOLIDADO))
            .jsonPath("$.[*].dataPublicacao")
            .value(hasItem(DEFAULT_DATA_PUBLICACAO.toString()));
    }

    @Test
    void getAcomph() {
        // Initialize the database
        insertedAcomph = acomphRepository.save(acomph).block();

        // Get the acomph
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, acomph.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(acomph.getId().intValue()))
            .jsonPath("$.date")
            .value(is(DEFAULT_DATE.toString()))
            .jsonPath("$.vazaoDefluenteLido")
            .value(is(DEFAULT_VAZAO_DEFLUENTE_LIDO))
            .jsonPath("$.vazaoDefluenteConsolidado")
            .value(is(DEFAULT_VAZAO_DEFLUENTE_CONSOLIDADO))
            .jsonPath("$.vazaoAfluenteLido")
            .value(is(DEFAULT_VAZAO_AFLUENTE_LIDO))
            .jsonPath("$.vazaoAfluenteConsolidado")
            .value(is(DEFAULT_VAZAO_AFLUENTE_CONSOLIDADO))
            .jsonPath("$.vazaoIncrementalConsolidado")
            .value(is(DEFAULT_VAZAO_INCREMENTAL_CONSOLIDADO))
            .jsonPath("$.vazaoNaturalConsolidado")
            .value(is(DEFAULT_VAZAO_NATURAL_CONSOLIDADO))
            .jsonPath("$.nivelReservatorioLido")
            .value(is(DEFAULT_NIVEL_RESERVATORIO_LIDO))
            .jsonPath("$.nivelReservatorioConsolidado")
            .value(is(DEFAULT_NIVEL_RESERVATORIO_CONSOLIDADO))
            .jsonPath("$.dataPublicacao")
            .value(is(DEFAULT_DATA_PUBLICACAO.toString()));
    }

    @Test
    void getNonExistingAcomph() {
        // Get the acomph
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_PROBLEM_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingAcomph() throws Exception {
        // Initialize the database
        insertedAcomph = acomphRepository.save(acomph).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the acomph
        Acomph updatedAcomph = acomphRepository.findById(acomph.getId()).block();
        updatedAcomph
            .date(UPDATED_DATE)
            .vazaoDefluenteLido(UPDATED_VAZAO_DEFLUENTE_LIDO)
            .vazaoDefluenteConsolidado(UPDATED_VAZAO_DEFLUENTE_CONSOLIDADO)
            .vazaoAfluenteLido(UPDATED_VAZAO_AFLUENTE_LIDO)
            .vazaoAfluenteConsolidado(UPDATED_VAZAO_AFLUENTE_CONSOLIDADO)
            .vazaoIncrementalConsolidado(UPDATED_VAZAO_INCREMENTAL_CONSOLIDADO)
            .vazaoNaturalConsolidado(UPDATED_VAZAO_NATURAL_CONSOLIDADO)
            .nivelReservatorioLido(UPDATED_NIVEL_RESERVATORIO_LIDO)
            .nivelReservatorioConsolidado(UPDATED_NIVEL_RESERVATORIO_CONSOLIDADO)
            .dataPublicacao(UPDATED_DATA_PUBLICACAO);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedAcomph.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(updatedAcomph))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Acomph in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAcomphToMatchAllProperties(updatedAcomph);
    }

    @Test
    void putNonExistingAcomph() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        acomph.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, acomph.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(acomph))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Acomph in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchAcomph() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        acomph.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, longCount.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(acomph))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Acomph in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamAcomph() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        acomph.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(acomph))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Acomph in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateAcomphWithPatch() throws Exception {
        // Initialize the database
        insertedAcomph = acomphRepository.save(acomph).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the acomph using partial update
        Acomph partialUpdatedAcomph = new Acomph();
        partialUpdatedAcomph.setId(acomph.getId());

        partialUpdatedAcomph
            .vazaoDefluenteLido(UPDATED_VAZAO_DEFLUENTE_LIDO)
            .vazaoIncrementalConsolidado(UPDATED_VAZAO_INCREMENTAL_CONSOLIDADO)
            .vazaoNaturalConsolidado(UPDATED_VAZAO_NATURAL_CONSOLIDADO)
            .nivelReservatorioConsolidado(UPDATED_NIVEL_RESERVATORIO_CONSOLIDADO);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedAcomph.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(partialUpdatedAcomph))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Acomph in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAcomphUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedAcomph, acomph), getPersistedAcomph(acomph));
    }

    @Test
    void fullUpdateAcomphWithPatch() throws Exception {
        // Initialize the database
        insertedAcomph = acomphRepository.save(acomph).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the acomph using partial update
        Acomph partialUpdatedAcomph = new Acomph();
        partialUpdatedAcomph.setId(acomph.getId());

        partialUpdatedAcomph
            .date(UPDATED_DATE)
            .vazaoDefluenteLido(UPDATED_VAZAO_DEFLUENTE_LIDO)
            .vazaoDefluenteConsolidado(UPDATED_VAZAO_DEFLUENTE_CONSOLIDADO)
            .vazaoAfluenteLido(UPDATED_VAZAO_AFLUENTE_LIDO)
            .vazaoAfluenteConsolidado(UPDATED_VAZAO_AFLUENTE_CONSOLIDADO)
            .vazaoIncrementalConsolidado(UPDATED_VAZAO_INCREMENTAL_CONSOLIDADO)
            .vazaoNaturalConsolidado(UPDATED_VAZAO_NATURAL_CONSOLIDADO)
            .nivelReservatorioLido(UPDATED_NIVEL_RESERVATORIO_LIDO)
            .nivelReservatorioConsolidado(UPDATED_NIVEL_RESERVATORIO_CONSOLIDADO)
            .dataPublicacao(UPDATED_DATA_PUBLICACAO);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedAcomph.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(partialUpdatedAcomph))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Acomph in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAcomphUpdatableFieldsEquals(partialUpdatedAcomph, getPersistedAcomph(partialUpdatedAcomph));
    }

    @Test
    void patchNonExistingAcomph() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        acomph.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, acomph.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(acomph))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Acomph in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchAcomph() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        acomph.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, longCount.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(acomph))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Acomph in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamAcomph() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        acomph.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(acomph))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Acomph in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteAcomph() {
        // Initialize the database
        insertedAcomph = acomphRepository.save(acomph).block();

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the acomph
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, acomph.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return acomphRepository.count().block();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Acomph getPersistedAcomph(Acomph acomph) {
        return acomphRepository.findById(acomph.getId()).block();
    }

    protected void assertPersistedAcomphToMatchAllProperties(Acomph expectedAcomph) {
        // Test fails because reactive api returns an empty object instead of null
        // assertAcomphAllPropertiesEquals(expectedAcomph, getPersistedAcomph(expectedAcomph));
        assertAcomphUpdatableFieldsEquals(expectedAcomph, getPersistedAcomph(expectedAcomph));
    }

    protected void assertPersistedAcomphToMatchUpdatableProperties(Acomph expectedAcomph) {
        // Test fails because reactive api returns an empty object instead of null
        // assertAcomphAllUpdatablePropertiesEquals(expectedAcomph, getPersistedAcomph(expectedAcomph));
        assertAcomphUpdatableFieldsEquals(expectedAcomph, getPersistedAcomph(expectedAcomph));
    }
}
