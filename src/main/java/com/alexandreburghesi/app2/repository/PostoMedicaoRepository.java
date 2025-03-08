package com.alexandreburghesi.app2.repository;

import com.alexandreburghesi.app2.domain.PostoMedicao;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the PostoMedicao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PostoMedicaoRepository extends ReactiveCrudRepository<PostoMedicao, Long>, PostoMedicaoRepositoryInternal {
    @Override
    <S extends PostoMedicao> Mono<S> save(S entity);

    @Override
    Flux<PostoMedicao> findAll();

    @Override
    Mono<PostoMedicao> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface PostoMedicaoRepositoryInternal {
    <S extends PostoMedicao> Mono<S> save(S entity);

    Flux<PostoMedicao> findAllBy(Pageable pageable);

    Flux<PostoMedicao> findAll();

    Mono<PostoMedicao> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<PostoMedicao> findAllBy(Pageable pageable, Criteria criteria);
}
