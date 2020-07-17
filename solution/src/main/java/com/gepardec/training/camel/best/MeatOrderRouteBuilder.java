package com.gepardec.training.camel.best;

import com.gepardec.training.camel.best.config.Endpoints;
import org.apache.camel.builder.RouteBuilder;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public final class MeatOrderRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from(Endpoints.MEAT_ORDER_ENTRY_SEDA_ENDPOINT.endpointUri())
                .log("MEAT ${body}");
    }
}
