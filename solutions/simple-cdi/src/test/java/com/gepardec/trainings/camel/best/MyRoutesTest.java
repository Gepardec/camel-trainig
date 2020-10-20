package com.gepardec.trainings.camel.best;

import com.gepardec.training.camel.commons.test.routetest.CamelRouteCDITest;
import com.gepardec.training.camel.commons.test.routetest.MockedEndpointId;
import com.gepardec.training.camel.commons.test.routetest.MockedRouteId;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.cdi.Uri;
import org.apache.camel.component.direct.DirectEndpoint;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.cdi.Beans;
import org.apache.camel.test.cdi.CamelCdiRunner;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

@RunWith(CamelCdiRunner.class)
//@Beans(classes = MyRoutes.class)
//@MockedRouteId(MyRoutes.ID_ROUTE)
public class MyRoutesTest extends CamelRouteCDITest {

//    @Inject
//    @Uri("mock:result")
//    @MockedEndpointId(MyRoutes.ID_FILE_ORDERS_OUT)
//    private MockEndpoint resultEndpoint;
//
//    @Inject
//    @Uri("direct:input")
//    @MockedEndpointId(MyRoutes.ID_FILE_ORDERS_IN)
//    private ProducerTemplate inputEndpoint;

    @Test
    public void when_order_in_orders_message_is_in_processed() throws InterruptedException {
//        resultEndpoint.expectedMessageCount(1);
//        inputEndpoint.sendBody("test");
//
//        resultEndpoint.assertIsSatisfied();
//        Exchange exchange = resultEndpoint.getExchanges().get(0);
//        Assertions.assertThat(exchange).isNotNull();
//        Assertions.assertThat(exchange.getIn().getBody(String.class)).isEqualTo("test");
    }
}
