package com.alexandreburghesi.app2.repository;

import com.alexandreburghesi.app2.domain.Acomph;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the Acomph entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AcomphRepository extends ReactiveCrudRepository<Acomph, Long>, AcomphRepositoryInternal {
    @Query("SELECT * FROM acomph entity WHERE entity.num_posto_medicao_id = :id")
    Flux<Acomph> findByNumPostoMedicao(Long id);

    @Query("SELECT * FROM acomph entity WHERE entity.num_posto_medicao_id IS NULL")
    Flux<Acomph> findAllWhereNumPostoMedicaoIsNull();

    @Override
    <S extends Acomph> Mono<S> save(S entity);

    @Override
    Flux<Acomph> findAll();

    @Override
    Mono<Acomph> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface AcomphRepositoryInternal {
    <S extends Acomph> Mono<S> save(S entity);

    Flux<Acomph> findAllBy(Pageable pageable);

    Flux<Acomph> findAll();

    Mono<Acomph> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<Acomph> findAllBy(Pageable pageable, Criteria criteria);
}
