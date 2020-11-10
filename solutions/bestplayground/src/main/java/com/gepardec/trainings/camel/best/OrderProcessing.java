package com.gepardec.trainings.camel.best;

import com.gepardec.training.camel.commons.domain.Order;
import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.cdi.Uri;
import org.apache.camel.component.jackson.JacksonDataFormat;

import javax.inject.Inject;

public class OrderProcessing extends RouteBuilder {

    public static final String URL_FILE_ORDERS_IN = "file://src/in/orders?noop=true";
    public static final String URL_FILE_ORDERS_OUT = "file://target/out/orders/processed";

    @Inject
    @Uri(URL_FILE_ORDERS_IN)
    private Endpoint inputEndpoint;

    @Inject
    @Uri(URL_FILE_ORDERS_OUT)
    private Endpoint resultEndpoint;

    @Override
    public void configure() {
        JacksonDataFormat orderFormat = new JacksonDataFormat(Order.class);

        from(inputEndpoint).routeId("OrderProcessing")
                .unmarshal(orderFormat)
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        exchange.getIn().getBody(Order.class).setPartnerId(34);
                    }
                })
                .marshal(orderFormat)
                .to(resultEndpoint);
    }
}
