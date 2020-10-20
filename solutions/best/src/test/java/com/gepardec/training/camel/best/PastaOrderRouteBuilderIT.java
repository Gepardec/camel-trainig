package com.gepardec.training.camel.best;

import com.gepardec.training.camel.best.config.DbConnection;
import com.gepardec.training.camel.best.db.CommonOperations;
import com.gepardec.training.camel.commons.test.integrationtest.CamelIntegrationTest;
import com.gepardec.training.camel.commons.test.integrationtest.RestServiceTestSupport;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.operation.Operation;
import org.assertj.db.type.Table;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

import static com.ninja_squad.dbsetup.operation.CompositeOperation.sequenceOf;
import static org.assertj.db.api.Assertions.assertThat;

public class PastaOrderRouteBuilderIT extends CamelIntegrationTest {

    private static final String MILK_JSON_FILE_PATH = "json/order_milk.json";
    private static final String PASTA_JSON_FILE_PATH = "json/order_pasta.json";

    @Test
    public void wrongInputJson_NothingInDB() throws IOException, SQLException {
        clearDB();

        String json = getFileAsString(MILK_JSON_FILE_PATH);

        //TODO: do a post call using RestServiceTestSupport (resorce url "" and expected status 202)
        RestServiceTestSupport.callPost("", json, 202);

        //TODO: using assertj-db make an assertion, that the db table order_to_producer contains no entries
        // Table table = new Table(DbConnection.getDatasource(), "order_to_producer");
        // assertThat(table).hasNumberOfRows(0);
    }

    @Test
    public void correctInputJson_CorrectJavaObjectIsCreated() throws IOException, SQLException {
        clearDB();

        String json = getFileAsString(PASTA_JSON_FILE_PATH);

        //TODO: do a post call using RestServiceTestSupport (resorce url "" and expected status 202)
        RestServiceTestSupport.callPost("", json, 202);

        //TODO: using assertj-db make an assertion, that the db table order_to_producer contains single row with correct partner_id, item_code and item_value corresponding order_pasta.json
//        Table table = new Table(DbConnection.getDatasource(), "order_to_producer");
//
//        assertThat(table).hasNumberOfRows(1)
//                .column("partner_id").value().isEqualTo(1)
//                .column("item_code").value().isEqualTo(2)
//                .column("item_count").value().isEqualTo(120);
    }

    private void clearDB() throws IOException, SQLException {
        Operation operation =
                sequenceOf(
                        CommonOperations.DELETE_ALL);
        DataSource dataSource = DbConnection.getDatasource();
        DbSetup dbSetup = new DbSetup(new DataSourceDestination(dataSource), operation);
        dbSetup.launch();

    }


}