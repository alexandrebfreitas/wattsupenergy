package com.alexandreburghesi.app2.web.rest;

import com.alexandreburghesi.app2.domain.PostoMedicao;
import com.alexandreburghesi.app2.repository.PostoMedicaoRepository;
import com.alexandreburghesi.app2.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.reactive.ResponseUtil;

/**
 * REST controller for managing {@link com.alexandreburghesi.app2.domain.PostoMedicao}.
 */
@RestController
@RequestMapping("/api/posto-medicaos")
@Transactional
public class PostoMedicaoResource {

    private static final Logger LOG = LoggerFactory.getLogger(PostoMedicaoResource.class);

    private static final String ENTITY_NAME = "postoMedicao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PostoMedicaoRepository postoMedicaoRepository;

    public PostoMedicaoResource(PostoMedicaoRepository postoMedicaoRepository) {
        this.postoMedicaoRepository = postoMedicaoRepository;
    }

    /**
     * {@code POST  /posto-medicaos} : Create a new postoMedicao.
     *
     * @param postoMedicao the postoMedicao to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new postoMedicao, or with status {@code 400 (Bad Request)} if the postoMedicao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public Mono<ResponseEntity<PostoMedicao>> createPostoMedicao(@RequestBody PostoMedicao postoMedicao) throws URISyntaxException {
        LOG.debug("REST request to save PostoMedicao : {}", postoMedicao);
        if (postoMedicao.getId() != null) {
            throw new BadRequestAlertException("A new postoMedicao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return postoMedicaoRepository
            .save(postoMedicao)
            .map(result -> {
                try {
                    return ResponseEntity.created(new URI("/api/posto-medicaos/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /posto-medicaos/:id} : Updates an existing postoMedicao.
     *
     * @param id the id of the postoMedicao to save.
     * @param postoMedicao the postoMedicao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated postoMedicao,
     * or with status {@code 400 (Bad Request)} if the postoMedicao is not valid,
     * or with status {@code 500 (Internal Server Error)} if the postoMedicao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public Mono<ResponseEntity<PostoMedicao>> updatePostoMedicao(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PostoMedicao postoMedicao
    ) throws URISyntaxException {
        LOG.debug("REST request to update PostoMedicao : {}, {}", id, postoMedicao);
        if (postoMedicao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, postoMedicao.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return postoMedicaoRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return postoMedicaoRepository
                    .save(postoMedicao)
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(result ->
                        ResponseEntity.ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                            .body(result)
                    );
            });
    }

    /**
     * {@code PATCH  /posto-medicaos/:id} : Partial updates given fields of an existing postoMedicao, field will ignore if it is null
     *
     * @param id the id of the postoMedicao to save.
     * @param postoMedicao the postoMedicao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated postoMedicao,
     * or with status {@code 400 (Bad Request)} if the postoMedicao is not valid,
     * or with status {@code 404 (Not Found)} if the postoMedicao is not found,
     * or with status {@code 500 (Internal Server Error)} if the postoMedicao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<PostoMedicao>> partialUpdatePostoMedicao(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PostoMedicao postoMedicao
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update PostoMedicao partially : {}, {}", id, postoMedicao);
        if (postoMedicao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, postoMedicao.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return postoMedicaoRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<PostoMedicao> result = postoMedicaoRepository
                    .findById(postoMedicao.getId())
                    .map(existingPostoMedicao -> {
                        if (postoMedicao.getNome() != null) {
                            existingPostoMedicao.setNome(postoMedicao.getNome());
                        }
                        if (postoMedicao.getNumUsinaHidreletrica() != null) {
                            existingPostoMedicao.setNumUsinaHidreletrica(postoMedicao.getNumUsinaHidreletrica());
                        }
                        if (postoMedicao.getBacia() != null) {
                            existingPostoMedicao.setBacia(postoMedicao.getBacia());
                        }
                        if (postoMedicao.getSubbacia() != null) {
                            existingPostoMedicao.setSubbacia(postoMedicao.getSubbacia());
                        }
                        if (postoMedicao.getSubmercado() != null) {
                            existingPostoMedicao.setSubmercado(postoMedicao.getSubmercado());
                        }

                        return existingPostoMedicao;
                    })
                    .flatMap(postoMedicaoRepository::save);

                return result
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(res ->
                        ResponseEntity.ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, res.getId().toString()))
                            .body(res)
                    );
            });
    }

    /**
     * {@code GET  /posto-medicaos} : get all the postoMedicaos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of postoMedicaos in body.
     */
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<List<PostoMedicao>> getAllPostoMedicaos() {
        LOG.debug("REST request to get all PostoMedicaos");
        return postoMedicaoRepository.findAll().collectList();
    }

    /**
     * {@code GET  /posto-medicaos} : get all the postoMedicaos as a stream.
     * @return the {@link Flux} of postoMedicaos.
     */
    @GetMapping(value = "", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<PostoMedicao> getAllPostoMedicaosAsStream() {
        LOG.debug("REST request to get all PostoMedicaos as a stream");
        return postoMedicaoRepository.findAll();
    }

    /**
     * {@code GET  /posto-medicaos/:id} : get the "id" postoMedicao.
     *
     * @param id the id of the postoMedicao to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the postoMedicao, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public Mono<ResponseEntity<PostoMedicao>> getPostoMedicao(@PathVariable("id") Long id) {
        LOG.debug("REST request to get PostoMedicao : {}", id);
        Mono<PostoMedicao> postoMedicao = postoMedicaoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(postoMedicao);
    }

    /**
     * {@code DELETE  /posto-medicaos/:id} : delete the "id" postoMedicao.
     *
     * @param id the id of the postoMedicao to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deletePostoMedicao(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete PostoMedicao : {}", id);
        return postoMedicaoRepository
            .deleteById(id)
            .then(
                Mono.just(
                    ResponseEntity.noContent()
                        .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                        .build()
                )
            );
    }
}
