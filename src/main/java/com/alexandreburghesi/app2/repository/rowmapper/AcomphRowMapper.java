package com.alexandreburghesi.app2.repository.rowmapper;

import com.alexandreburghesi.app2.domain.Acomph;
import io.r2dbc.spi.Row;
import java.time.LocalDate;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Acomph}, with proper type conversions.
 */
@Service
public class AcomphRowMapper implements BiFunction<Row, String, Acomph> {

    private final ColumnConverter converter;

    public AcomphRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Acomph} stored in the database.
     */
    @Override
    public Acomph apply(Row row, String prefix) {
        Acomph entity = new Acomph();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setDate(converter.fromRow(row, prefix + "_date", LocalDate.class));
        entity.setVazaoDefluenteLido(converter.fromRow(row, prefix + "_vazao_defluente_lido", Double.class));
        entity.setVazaoDefluenteConsolidado(converter.fromRow(row, prefix + "_vazao_defluente_consolidado", Double.class));
        entity.setVazaoAfluenteLido(converter.fromRow(row, prefix + "_vazao_afluente_lido", Double.class));
        entity.setVazaoAfluenteConsolidado(converter.fromRow(row, prefix + "_vazao_afluente_consolidado", Double.class));
        entity.setVazaoIncrementalConsolidado(converter.fromRow(row, prefix + "_vazao_incremental_consolidado", Double.class));
        entity.setVazaoNaturalConsolidado(converter.fromRow(row, prefix + "_vazao_natural_consolidado", Double.class));
        entity.setNivelReservatorioLido(converter.fromRow(row, prefix + "_nivel_reservatorio_lido", Double.class));
        entity.setNivelReservatorioConsolidado(converter.fromRow(row, prefix + "_nivel_reservatorio_consolidado", Double.class));
        entity.setDataPublicacao(converter.fromRow(row, prefix + "_data_publicacao", LocalDate.class));
        entity.setNumPostoMedicaoId(converter.fromRow(row, prefix + "_num_posto_medicao_id", Long.class));
        return entity;
    }
}
