package com.gepardec.trainings.camel.best;

import com.gepardec.training.camel.commons.test.routetest.CamelRouteCDITest;
import com.gepardec.training.camel.commons.test.routetest.MockableEndpoint;
import com.gepardec.training.camel.commons.test.routetest.RouteId;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.cdi.Uri;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.cdi.Beans;
import org.apache.camel.test.cdi.CamelCdiRunner;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

@RunWith(CamelCdiRunner.class)
@Beans(classes = OrderProcessing.class)
public class OrderProcessingTest extends CamelRouteCDITest {

    //replace resultEndpoint of MyRoutes with mock endpoint
    @Inject
    @Uri("mock:result")
    @MockableEndpoint(OrderProcessing.URL_FILE_ORDERS_OUT)
    @RouteId("OrderProcessing")
    private MockEndpoint result;

    //replace input file endpoint with a direct endpoint, simulating file input
    @Inject
    @Uri("direct:fileinput")
    @MockableEndpoint(OrderProcessing.URL_FILE_ORDERS_IN)
    @RouteId("OrderProcessing")
    private ProducerTemplate fileInputProducer;

    @Test
    public void testRoute() throws InterruptedException {
        // set expected message count
        result.expectedMessageCount(1);

        // simulate file input
        String orderIn = "{\"partnerId\": 1, \"items\": [{ \"code\": 1, \"amount\": 110 }]}";
        fileInputProducer.sendBody(orderIn);

        // assert number of messages
        result.assertIsSatisfied();

        // get exchange
        Exchange exchange = result.getExchanges().get(0);

        // assert exchange content (simulated output to file)
        Assertions.assertThat(exchange.getIn().getBody(String.class)).isEqualToIgnoringWhitespace("{\"partnerId\": 34, \"items\": [{ \"code\": 1, \"amount\": 110 }]}");
    }
}
