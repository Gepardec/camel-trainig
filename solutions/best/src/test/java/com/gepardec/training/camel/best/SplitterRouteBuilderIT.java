package com.gepardec.training.camel.best;

import com.gepardec.training.camel.best.domain.Order;
import com.gepardec.training.camel.best.domain.OrderItem;
import com.gepardec.training.camel.commons.test.routetest.MockedEndpointId;
import com.gepardec.training.camel.commons.test.routetest.MockedRouteId;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.cdi.Uri;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.cdi.Beans;
import org.apache.camel.test.cdi.CamelCdiRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(CamelCdiRunner.class)
@Beans(classes = SplitterRouteBuilder.class)
@MockedRouteId(SplitterRouteBuilder.ROUTE_ID)
public class SplitterRouteBuilderIT {

    @Inject
    @Uri("mock:result")
    @MockedEndpointId("best_splitter_destination")
    private MockEndpoint result;

    @Inject
    @Uri(SplitterRouteBuilder.ENTRY_SEDA_ENDOINT_URI)
    private ProducerTemplate producerTemplate;

    @Inject
    private CamelContext camelContext;

    private Order order;

    @Before
    public void setup() throws Exception {

        camelContext.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("seda:splitterDestination")
                        .to("mock:result");
            }
        });


        order = new Order();
        List<OrderItem> items = new ArrayList<>();
        items.add(new OrderItem(OrderItem.EGG, 110));
        items.add(new OrderItem(OrderItem.PASTA, 120));
        items.add(new OrderItem(OrderItem.MILK, 130));
        items.add(new OrderItem(OrderItem.MEAT, 140));

        order.setItems(items);
        order.setPartnerId(1L);

    }

    @Test
    public void splitMessageTest() throws Exception {
        result.expectedMessageCount(1);

        // Then
        producerTemplate.sendBody(order);
        result.assertIsSatisfied();

        final Exchange exchange = result.getExchanges().get(0);
        assertThat(exchange).isNotNull();
        assertThat(result.getReceivedCounter() == 3);

    }
}
