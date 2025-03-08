package com.alexandreburghesi.app2.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class PostoMedicaoSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("nome", table, columnPrefix + "_nome"));
        columns.add(Column.aliased("num_usina_hidreletrica", table, columnPrefix + "_num_usina_hidreletrica"));
        columns.add(Column.aliased("bacia", table, columnPrefix + "_bacia"));
        columns.add(Column.aliased("subbacia", table, columnPrefix + "_subbacia"));
        columns.add(Column.aliased("submercado", table, columnPrefix + "_submercado"));

        return columns;
    }
}
