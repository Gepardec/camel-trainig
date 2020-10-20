package com.gepardec.training.camel.best;

import com.gepardec.training.camel.best.config.Endpoints;
import com.gepardec.training.camel.best.domain.Order;
import com.gepardec.training.camel.commons.processor.ExceptionLoggingProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
public final class EntryRouteBuilder extends RouteBuilder {

    @Override
    public void configure() {

        onException(Exception.class)
                .process(new ExceptionLoggingProcessor())
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(400))
                .setHeader(Exchange.CONTENT_TYPE, constant("text/plain"))
                .handled(true);

        restConfiguration().component("servlet").bindingMode(RestBindingMode.json)
                .dataFormatProperty("prettyPrint", "true");

        //TODO EX1 Using documentation configure rest for using netty-http and binding on localhost at port 8080
        //restConfiguration().component("netty-http").host("localhost").port(8080).bindingMode(RestBindingMode.auto);

        //TODO EX1 Using documentation configure path "/best/" to accespt JSON objects via POST return http status accepted to the caller

        //TODO EX2 Using documentation configure path "/best/" to accespt JSON objects via POST and map them to Order.class. Then forward object to Splitter entry endpoint and return http status accepted to the caller.
//        rest("/best/")
//                .post()
//                .consumes(MediaType.APPLICATION_JSON)
//                .type(Order.class)
//                .id("best_rest")
//                .route().to(SplitterRouteBuilder.ENTRY_SEDA_ENDOINT_URI)
//                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(Response.Status.ACCEPTED.getStatusCode()))
//                .endRest();

    }
}
