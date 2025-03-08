package com.alexandreburghesi.app2.repository.rowmapper;

import com.alexandreburghesi.app2.domain.PostoMedicao;
import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link PostoMedicao}, with proper type conversions.
 */
@Service
public class PostoMedicaoRowMapper implements BiFunction<Row, String, PostoMedicao> {

    private final ColumnConverter converter;

    public PostoMedicaoRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link PostoMedicao} stored in the database.
     */
    @Override
    public PostoMedicao apply(Row row, String prefix) {
        PostoMedicao entity = new PostoMedicao();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setNome(converter.fromRow(row, prefix + "_nome", String.class));
        entity.setNumUsinaHidreletrica(converter.fromRow(row, prefix + "_num_usina_hidreletrica", Integer.class));
        entity.setBacia(converter.fromRow(row, prefix + "_bacia", String.class));
        entity.setSubbacia(converter.fromRow(row, prefix + "_subbacia", String.class));
        entity.setSubmercado(converter.fromRow(row, prefix + "_submercado", String.class));
        return entity;
    }
}
