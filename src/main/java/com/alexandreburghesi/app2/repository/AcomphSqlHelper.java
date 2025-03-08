package com.alexandreburghesi.app2.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class AcomphSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("date", table, columnPrefix + "_date"));
        columns.add(Column.aliased("vazao_defluente_lido", table, columnPrefix + "_vazao_defluente_lido"));
        columns.add(Column.aliased("vazao_defluente_consolidado", table, columnPrefix + "_vazao_defluente_consolidado"));
        columns.add(Column.aliased("vazao_afluente_lido", table, columnPrefix + "_vazao_afluente_lido"));
        columns.add(Column.aliased("vazao_afluente_consolidado", table, columnPrefix + "_vazao_afluente_consolidado"));
        columns.add(Column.aliased("vazao_incremental_consolidado", table, columnPrefix + "_vazao_incremental_consolidado"));
        columns.add(Column.aliased("vazao_natural_consolidado", table, columnPrefix + "_vazao_natural_consolidado"));
        columns.add(Column.aliased("nivel_reservatorio_lido", table, columnPrefix + "_nivel_reservatorio_lido"));
        columns.add(Column.aliased("nivel_reservatorio_consolidado", table, columnPrefix + "_nivel_reservatorio_consolidado"));
        columns.add(Column.aliased("data_publicacao", table, columnPrefix + "_data_publicacao"));

        columns.add(Column.aliased("num_posto_medicao_id", table, columnPrefix + "_num_posto_medicao_id"));
        return columns;
    }
}
