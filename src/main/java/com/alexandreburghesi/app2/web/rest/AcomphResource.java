package com.alexandreburghesi.app2.web.rest;

import com.alexandreburghesi.app2.domain.Acomph;
import com.alexandreburghesi.app2.repository.AcomphRepository;
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
 * REST controller for managing {@link com.alexandreburghesi.app2.domain.Acomph}.
 */
@RestController
@RequestMapping("/api/acomphs")
@Transactional
public class AcomphResource {

    private static final Logger LOG = LoggerFactory.getLogger(AcomphResource.class);

    private static final String ENTITY_NAME = "acomph";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AcomphRepository acomphRepository;

    public AcomphResource(AcomphRepository acomphRepository) {
        this.acomphRepository = acomphRepository;
    }

    /**
     * {@code POST  /acomphs} : Create a new acomph.
     *
     * @param acomph the acomph to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new acomph, or with status {@code 400 (Bad Request)} if the acomph has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public Mono<ResponseEntity<Acomph>> createAcomph(@RequestBody Acomph acomph) throws URISyntaxException {
        LOG.debug("REST request to save Acomph : {}", acomph);
        if (acomph.getId() != null) {
            throw new BadRequestAlertException("A new acomph cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return acomphRepository
            .save(acomph)
            .map(result -> {
                try {
                    return ResponseEntity.created(new URI("/api/acomphs/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /acomphs/:id} : Updates an existing acomph.
     *
     * @param id the id of the acomph to save.
     * @param acomph the acomph to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated acomph,
     * or with status {@code 400 (Bad Request)} if the acomph is not valid,
     * or with status {@code 500 (Internal Server Error)} if the acomph couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public Mono<ResponseEntity<Acomph>> updateAcomph(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Acomph acomph
    ) throws URISyntaxException {
        LOG.debug("REST request to update Acomph : {}, {}", id, acomph);
        if (acomph.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, acomph.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return acomphRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return acomphRepository
                    .save(acomph)
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(result ->
                        ResponseEntity.ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                            .body(result)
                    );
            });
    }

    /**
     * {@code PATCH  /acomphs/:id} : Partial updates given fields of an existing acomph, field will ignore if it is null
     *
     * @param id the id of the acomph to save.
     * @param acomph the acomph to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated acomph,
     * or with status {@code 400 (Bad Request)} if the acomph is not valid,
     * or with status {@code 404 (Not Found)} if the acomph is not found,
     * or with status {@code 500 (Internal Server Error)} if the acomph couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<Acomph>> partialUpdateAcomph(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Acomph acomph
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Acomph partially : {}, {}", id, acomph);
        if (acomph.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, acomph.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return acomphRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<Acomph> result = acomphRepository
                    .findById(acomph.getId())
                    .map(existingAcomph -> {
                        if (acomph.getDate() != null) {
                            existingAcomph.setDate(acomph.getDate());
                        }
                        if (acomph.getVazaoDefluenteLido() != null) {
                            existingAcomph.setVazaoDefluenteLido(acomph.getVazaoDefluenteLido());
                        }
                        if (acomph.getVazaoDefluenteConsolidado() != null) {
                            existingAcomph.setVazaoDefluenteConsolidado(acomph.getVazaoDefluenteConsolidado());
                        }
                        if (acomph.getVazaoAfluenteLido() != null) {
                            existingAcomph.setVazaoAfluenteLido(acomph.getVazaoAfluenteLido());
                        }
                        if (acomph.getVazaoAfluenteConsolidado() != null) {
                            existingAcomph.setVazaoAfluenteConsolidado(acomph.getVazaoAfluenteConsolidado());
                        }
                        if (acomph.getVazaoIncrementalConsolidado() != null) {
                            existingAcomph.setVazaoIncrementalConsolidado(acomph.getVazaoIncrementalConsolidado());
                        }
                        if (acomph.getVazaoNaturalConsolidado() != null) {
                            existingAcomph.setVazaoNaturalConsolidado(acomph.getVazaoNaturalConsolidado());
                        }
                        if (acomph.getNivelReservatorioLido() != null) {
                            existingAcomph.setNivelReservatorioLido(acomph.getNivelReservatorioLido());
                        }
                        if (acomph.getNivelReservatorioConsolidado() != null) {
                            existingAcomph.setNivelReservatorioConsolidado(acomph.getNivelReservatorioConsolidado());
                        }
                        if (acomph.getDataPublicacao() != null) {
                            existingAcomph.setDataPublicacao(acomph.getDataPublicacao());
                        }

                        return existingAcomph;
                    })
                    .flatMap(acomphRepository::save);

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
     * {@code GET  /acomphs} : get all the acomphs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of acomphs in body.
     */
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<List<Acomph>> getAllAcomphs() {
        LOG.debug("REST request to get all Acomphs");
        return acomphRepository.findAll().collectList();
    }

    /**
     * {@code GET  /acomphs} : get all the acomphs as a stream.
     * @return the {@link Flux} of acomphs.
     */
    @GetMapping(value = "", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<Acomph> getAllAcomphsAsStream() {
        LOG.debug("REST request to get all Acomphs as a stream");
        return acomphRepository.findAll();
    }

    /**
     * {@code GET  /acomphs/:id} : get the "id" acomph.
     *
     * @param id the id of the acomph to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the acomph, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public Mono<ResponseEntity<Acomph>> getAcomph(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Acomph : {}", id);
        Mono<Acomph> acomph = acomphRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(acomph);
    }

    /**
     * {@code DELETE  /acomphs/:id} : delete the "id" acomph.
     *
     * @param id the id of the acomph to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteAcomph(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Acomph : {}", id);
        return acomphRepository
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
