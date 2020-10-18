package com.gepardec.training.camel.best;

import com.gepardec.training.camel.best.config.ConfigurationUtils;
import com.gepardec.training.camel.best.config.Endpoints;
import com.gepardec.training.camel.commons.test.integrationtest.CamelIntegrationTest;
import com.gepardec.training.camel.commons.test.integrationtest.RestServiceTestSupport;
import org.apache.camel.Exchange;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class EggOrderRouteBuilderIT extends CamelIntegrationTest {

    private static final String MILK_JSON_FILE_PATH = "json/order_milk.json";
    private static final String EGGS_JSON_FILE_PATH = "json/order_eggs.json";

    @Before
    public void setup(){
        camelContext.getRegistry().bind("JMSConnectionFactory", ConfigurationUtils.getJmsConnectionFactory("tcp://localhost:61616", "quarkus", "quarkus"));
        clearEndpointQueue(EggOrderRouteBuilder.OUTPUT_JMS_ENDPOINT_URI);
    }

    @Test
    public void wrongInputJson_NothingInQueue() throws IOException {
        String json = getFileAsString(MILK_JSON_FILE_PATH);
        RestServiceTestSupport.callPost("", json, 202);
        pollFromEndpoint(Endpoints.EGG_ORDER_JMS_ENDPOINT.endpointUri(), 2000);
    }

    @Test
    public void correctInputJson_CorrectJavaObjectIsCreated() throws IOException {
        String json = getFileAsString(EGGS_JSON_FILE_PATH);
        RestServiceTestSupport.callPost("", json, 202);
        Exchange exchange = pollFromEndpoint(EggOrderRouteBuilder.OUTPUT_JMS_ENDPOINT_URI);
        assertThat(exchange).isNotNull();
        assertThat(exchange.getIn().getBody()).isNotNull();
        assertThat(exchange.getIn().getBody(String.class))
                .containsIgnoringCase("orderToProducer>")
                .containsIgnoringCase("amount>110</")
                .containsIgnoringCase("code>1</")
                .containsIgnoringCase("partnerId>1</");
    }
}