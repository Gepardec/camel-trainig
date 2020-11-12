package com.gepardec.training.camel.best;

import com.gepardec.training.camel.commons.misc.ConfigurationProducer;
import com.gepardec.training.camel.commons.test.integrationtest.CamelIntegrationTest;
import com.gepardec.training.camel.commons.test.integrationtest.RestServiceTestSupport;
import org.apache.camel.Exchange;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class MeatOrderRouteBuilderIT extends CamelIntegrationTest {

    private static final String MEAT_GREATER_JSON_FILE_PATH = "json/order_meat_greater_100.json";
    private static final String MEAT_LESS_JSON_FILE_PATH = "json/order_meat_less_100.json";

    @Before
    public void setup(){
        camelContext.getRegistry().bind("JMSConnectionFactory", new ConfigurationProducer().createConnectionFactory());
        clearEndpointQueue(MeatOrderRouteBuilder.OUTPUT_JMS_ENDPOINT_URI);
    }

    @Test
    public void lessThan100NothingInQueue() throws IOException {
        String json = getFileAsString(MEAT_LESS_JSON_FILE_PATH);
        RestServiceTestSupport.callPost("", json, 202);
        Exchange exchange = pollFromEndpoint(MeatOrderRouteBuilder.OUTPUT_JMS_ENDPOINT_URI);
        assertThat(exchange).isNull();

    }

    @Test
    public void greaterThan100SomethingInQueue() throws IOException {
        String json = getFileAsString(MEAT_GREATER_JSON_FILE_PATH);
        RestServiceTestSupport.callPost("", json, 202);
        Exchange exchange = pollFromEndpoint(MeatOrderRouteBuilder.OUTPUT_JMS_ENDPOINT_URI);
        assertThat(exchange).isNotNull();
        assertThat(exchange.getIn().getBody()).isNotNull();
        assertThat(exchange.getIn().getBody(String.class))
                .containsIgnoringCase("orderToProducer>")
                .containsIgnoringCase("amount>120</")
                .containsIgnoringCase("code>4</")
                .containsIgnoringCase("partnerId>1</");
    }


}
