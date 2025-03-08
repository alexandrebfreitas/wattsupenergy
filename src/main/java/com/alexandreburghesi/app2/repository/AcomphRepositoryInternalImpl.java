package com.alexandreburghesi.app2.repository;

import com.alexandreburghesi.app2.domain.Acomph;
import com.alexandreburghesi.app2.repository.rowmapper.AcomphRowMapper;
import com.alexandreburghesi.app2.repository.rowmapper.PostoMedicaoRowMapper;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.convert.R2dbcConverter;
import org.springframework.data.r2dbc.core.R2dbcEntityOperations;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.repository.support.SimpleR2dbcRepository;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Comparison;
import org.springframework.data.relational.core.sql.Condition;
import org.springframework.data.relational.core.sql.Conditions;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Select;
import org.springframework.data.relational.core.sql.SelectBuilder.SelectFromAndJoinCondition;
import org.springframework.data.relational.core.sql.Table;
import org.springframework.data.relational.repository.support.MappingRelationalEntityInformation;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.r2dbc.core.RowsFetchSpec;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC custom repository implementation for the Acomph entity.
 */
@SuppressWarnings("unused")
class AcomphRepositoryInternalImpl extends SimpleR2dbcRepository<Acomph, Long> implements AcomphRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final PostoMedicaoRowMapper postomedicaoMapper;
    private final AcomphRowMapper acomphMapper;

    private static final Table entityTable = Table.aliased("acomph", EntityManager.ENTITY_ALIAS);
    private static final Table numPostoMedicaoTable = Table.aliased("posto_medicao", "numPostoMedicao");

    public AcomphRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        PostoMedicaoRowMapper postomedicaoMapper,
        AcomphRowMapper acomphMapper,
        R2dbcEntityOperations entityOperations,
        R2dbcConverter converter
    ) {
        super(
            new MappingRelationalEntityInformation(converter.getMappingContext().getRequiredPersistentEntity(Acomph.class)),
            entityOperations,
            converter
        );
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.postomedicaoMapper = postomedicaoMapper;
        this.acomphMapper = acomphMapper;
    }

    @Override
    public Flux<Acomph> findAllBy(Pageable pageable) {
        return createQuery(pageable, null).all();
    }

    RowsFetchSpec<Acomph> createQuery(Pageable pageable, Condition whereClause) {
        List<Expression> columns = AcomphSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        columns.addAll(PostoMedicaoSqlHelper.getColumns(numPostoMedicaoTable, "numPostoMedicao"));
        SelectFromAndJoinCondition selectFrom = Select.builder()
            .select(columns)
            .from(entityTable)
            .leftOuterJoin(numPostoMedicaoTable)
            .on(Column.create("num_posto_medicao_id", entityTable))
            .equals(Column.create("id", numPostoMedicaoTable));
        // we do not support Criteria here for now as of https://github.com/jhipster/generator-jhipster/issues/18269
        String select = entityManager.createSelect(selectFrom, Acomph.class, pageable, whereClause);
        return db.sql(select).map(this::process);
    }

    @Override
    public Flux<Acomph> findAll() {
        return findAllBy(null);
    }

    @Override
    public Mono<Acomph> findById(Long id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("id"), Conditions.just(id.toString()));
        return createQuery(null, whereClause).one();
    }

    private Acomph process(Row row, RowMetadata metadata) {
        Acomph entity = acomphMapper.apply(row, "e");
        entity.setNumPostoMedicao(postomedicaoMapper.apply(row, "numPostoMedicao"));
        return entity;
    }

    @Override
    public <S extends Acomph> Mono<S> save(S entity) {
        return super.save(entity);
    }
}
