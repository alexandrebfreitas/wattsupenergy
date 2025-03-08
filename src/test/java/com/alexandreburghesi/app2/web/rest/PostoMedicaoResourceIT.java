package com.alexandreburghesi.app2.web.rest;

import static com.alexandreburghesi.app2.domain.PostoMedicaoAsserts.*;
import static com.alexandreburghesi.app2.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;

import com.alexandreburghesi.app2.IntegrationTest;
import com.alexandreburghesi.app2.domain.PostoMedicao;
import com.alexandreburghesi.app2.repository.EntityManager;
import com.alexandreburghesi.app2.repository.PostoMedicaoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Duration;
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
 * Integration tests for the {@link PostoMedicaoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class PostoMedicaoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUM_USINA_HIDRELETRICA = 1;
    private static final Integer UPDATED_NUM_USINA_HIDRELETRICA = 2;

    private static final String DEFAULT_BACIA = "AAAAAAAAAA";
    private static final String UPDATED_BACIA = "BBBBBBBBBB";

    private static final String DEFAULT_SUBBACIA = "AAAAAAAAAA";
    private static final String UPDATED_SUBBACIA = "BBBBBBBBBB";

    private static final String DEFAULT_SUBMERCADO = "AAAAAAAAAA";
    private static final String UPDATED_SUBMERCADO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/posto-medicaos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PostoMedicaoRepository postoMedicaoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private PostoMedicao postoMedicao;

    private PostoMedicao insertedPostoMedicao;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PostoMedicao createEntity() {
        return new PostoMedicao()
            .nome(DEFAULT_NOME)
            .numUsinaHidreletrica(DEFAULT_NUM_USINA_HIDRELETRICA)
            .bacia(DEFAULT_BACIA)
            .subbacia(DEFAULT_SUBBACIA)
            .submercado(DEFAULT_SUBMERCADO);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PostoMedicao createUpdatedEntity() {
        return new PostoMedicao()
            .nome(UPDATED_NOME)
            .numUsinaHidreletrica(UPDATED_NUM_USINA_HIDRELETRICA)
            .bacia(UPDATED_BACIA)
            .subbacia(UPDATED_SUBBACIA)
            .submercado(UPDATED_SUBMERCADO);
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(PostoMedicao.class).block();
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
        postoMedicao = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedPostoMedicao != null) {
            postoMedicaoRepository.delete(insertedPostoMedicao).block();
            insertedPostoMedicao = null;
        }
        deleteEntities(em);
    }

    @Test
    void createPostoMedicao() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the PostoMedicao
        var returnedPostoMedicao = webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(postoMedicao))
            .exchange()
            .expectStatus()
            .isCreated()
            .expectBody(PostoMedicao.class)
            .returnResult()
            .getResponseBody();

        // Validate the PostoMedicao in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertPostoMedicaoUpdatableFieldsEquals(returnedPostoMedicao, getPersistedPostoMedicao(returnedPostoMedicao));

        insertedPostoMedicao = returnedPostoMedicao;
    }

    @Test
    void createPostoMedicaoWithExistingId() throws Exception {
        // Create the PostoMedicao with an existing ID
        postoMedicao.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(postoMedicao))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the PostoMedicao in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void getAllPostoMedicaosAsStream() {
        // Initialize the database
        postoMedicaoRepository.save(postoMedicao).block();

        List<PostoMedicao> postoMedicaoList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(PostoMedicao.class)
            .getResponseBody()
            .filter(postoMedicao::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(postoMedicaoList).isNotNull();
        assertThat(postoMedicaoList).hasSize(1);
        PostoMedicao testPostoMedicao = postoMedicaoList.get(0);

        // Test fails because reactive api returns an empty object instead of null
        // assertPostoMedicaoAllPropertiesEquals(postoMedicao, testPostoMedicao);
        assertPostoMedicaoUpdatableFieldsEquals(postoMedicao, testPostoMedicao);
    }

    @Test
    void getAllPostoMedicaos() {
        // Initialize the database
        insertedPostoMedicao = postoMedicaoRepository.save(postoMedicao).block();

        // Get all the postoMedicaoList
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
            .value(hasItem(postoMedicao.getId().intValue()))
            .jsonPath("$.[*].nome")
            .value(hasItem(DEFAULT_NOME))
            .jsonPath("$.[*].numUsinaHidreletrica")
            .value(hasItem(DEFAULT_NUM_USINA_HIDRELETRICA))
            .jsonPath("$.[*].bacia")
            .value(hasItem(DEFAULT_BACIA))
            .jsonPath("$.[*].subbacia")
            .value(hasItem(DEFAULT_SUBBACIA))
            .jsonPath("$.[*].submercado")
            .value(hasItem(DEFAULT_SUBMERCADO));
    }

    @Test
    void getPostoMedicao() {
        // Initialize the database
        insertedPostoMedicao = postoMedicaoRepository.save(postoMedicao).block();

        // Get the postoMedicao
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, postoMedicao.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(postoMedicao.getId().intValue()))
            .jsonPath("$.nome")
            .value(is(DEFAULT_NOME))
            .jsonPath("$.numUsinaHidreletrica")
            .value(is(DEFAULT_NUM_USINA_HIDRELETRICA))
            .jsonPath("$.bacia")
            .value(is(DEFAULT_BACIA))
            .jsonPath("$.subbacia")
            .value(is(DEFAULT_SUBBACIA))
            .jsonPath("$.submercado")
            .value(is(DEFAULT_SUBMERCADO));
    }

    @Test
    void getNonExistingPostoMedicao() {
        // Get the postoMedicao
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_PROBLEM_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingPostoMedicao() throws Exception {
        // Initialize the database
        insertedPostoMedicao = postoMedicaoRepository.save(postoMedicao).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the postoMedicao
        PostoMedicao updatedPostoMedicao = postoMedicaoRepository.findById(postoMedicao.getId()).block();
        updatedPostoMedicao
            .nome(UPDATED_NOME)
            .numUsinaHidreletrica(UPDATED_NUM_USINA_HIDRELETRICA)
            .bacia(UPDATED_BACIA)
            .subbacia(UPDATED_SUBBACIA)
            .submercado(UPDATED_SUBMERCADO);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedPostoMedicao.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(updatedPostoMedicao))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the PostoMedicao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPostoMedicaoToMatchAllProperties(updatedPostoMedicao);
    }

    @Test
    void putNonExistingPostoMedicao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        postoMedicao.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, postoMedicao.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(postoMedicao))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the PostoMedicao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchPostoMedicao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        postoMedicao.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, longCount.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(postoMedicao))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the PostoMedicao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamPostoMedicao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        postoMedicao.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(postoMedicao))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the PostoMedicao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdatePostoMedicaoWithPatch() throws Exception {
        // Initialize the database
        insertedPostoMedicao = postoMedicaoRepository.save(postoMedicao).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the postoMedicao using partial update
        PostoMedicao partialUpdatedPostoMedicao = new PostoMedicao();
        partialUpdatedPostoMedicao.setId(postoMedicao.getId());

        partialUpdatedPostoMedicao.nome(UPDATED_NOME).numUsinaHidreletrica(UPDATED_NUM_USINA_HIDRELETRICA);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedPostoMedicao.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(partialUpdatedPostoMedicao))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the PostoMedicao in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPostoMedicaoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedPostoMedicao, postoMedicao),
            getPersistedPostoMedicao(postoMedicao)
        );
    }

    @Test
    void fullUpdatePostoMedicaoWithPatch() throws Exception {
        // Initialize the database
        insertedPostoMedicao = postoMedicaoRepository.save(postoMedicao).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the postoMedicao using partial update
        PostoMedicao partialUpdatedPostoMedicao = new PostoMedicao();
        partialUpdatedPostoMedicao.setId(postoMedicao.getId());

        partialUpdatedPostoMedicao
            .nome(UPDATED_NOME)
            .numUsinaHidreletrica(UPDATED_NUM_USINA_HIDRELETRICA)
            .bacia(UPDATED_BACIA)
            .subbacia(UPDATED_SUBBACIA)
            .submercado(UPDATED_SUBMERCADO);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedPostoMedicao.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(partialUpdatedPostoMedicao))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the PostoMedicao in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPostoMedicaoUpdatableFieldsEquals(partialUpdatedPostoMedicao, getPersistedPostoMedicao(partialUpdatedPostoMedicao));
    }

    @Test
    void patchNonExistingPostoMedicao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        postoMedicao.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, postoMedicao.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(postoMedicao))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the PostoMedicao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchPostoMedicao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        postoMedicao.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, longCount.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(postoMedicao))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the PostoMedicao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamPostoMedicao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        postoMedicao.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(postoMedicao))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the PostoMedicao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deletePostoMedicao() {
        // Initialize the database
        insertedPostoMedicao = postoMedicaoRepository.save(postoMedicao).block();

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the postoMedicao
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, postoMedicao.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return postoMedicaoRepository.count().block();
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

    protected PostoMedicao getPersistedPostoMedicao(PostoMedicao postoMedicao) {
        return postoMedicaoRepository.findById(postoMedicao.getId()).block();
    }

    protected void assertPersistedPostoMedicaoToMatchAllProperties(PostoMedicao expectedPostoMedicao) {
        // Test fails because reactive api returns an empty object instead of null
        // assertPostoMedicaoAllPropertiesEquals(expectedPostoMedicao, getPersistedPostoMedicao(expectedPostoMedicao));
        assertPostoMedicaoUpdatableFieldsEquals(expectedPostoMedicao, getPersistedPostoMedicao(expectedPostoMedicao));
    }

    protected void assertPersistedPostoMedicaoToMatchUpdatableProperties(PostoMedicao expectedPostoMedicao) {
        // Test fails because reactive api returns an empty object instead of null
        // assertPostoMedicaoAllUpdatablePropertiesEquals(expectedPostoMedicao, getPersistedPostoMedicao(expectedPostoMedicao));
        assertPostoMedicaoUpdatableFieldsEquals(expectedPostoMedicao, getPersistedPostoMedicao(expectedPostoMedicao));
    }
}
