package com.gepardec.trainings.camel.best;

import com.gepardec.training.camel.commons.test.routetest.CamelRouteCDITest;
import com.gepardec.training.camel.commons.test.routetest.MockableEndpoint;
import com.gepardec.training.camel.commons.test.routetest.RouteId;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.cdi.Uri;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.cdi.Beans;
import org.apache.camel.test.cdi.CamelCdiRunner;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static org.junit.Assert.*;

@RunWith(CamelCdiRunner.class)
@Beans(classes = MyRoutes.class)
public class MyRoutesTest extends CamelRouteCDITest {

    //replace resultEndpoint of MyRoutes with mock endpoint
    @Inject
    @Uri("mock:result")
    @MockableEndpoint(MyRoutes.URL_FILE_ORDERS_OUT)
    @RouteId("MyRoutes")
    private MockEndpoint result;

    //replace input file endpoint with a direct endpoint, simulating file input
    @Inject
    @Uri("direct:fileinput")
    @MockableEndpoint(MyRoutes.URL_FILE_ORDERS_IN)
    @RouteId("MyRoutes")
    private ProducerTemplate fileInputProducer;

    @Test
    public void testRoute() throws InterruptedException {
        // set expected message count
        result.expectedMessageCount(1);

        // simulate file input
        fileInputProducer.sendBody("My test content");

        // assert number of messages
        result.assertIsSatisfied();

        // get exchange
        Exchange exchange = result.getExchanges().get(0);

        // assert exchange content (simulated output to file)
        Assertions.assertThat(exchange.getIn().getBody(String.class)).isEqualTo("My test content");
    }
}