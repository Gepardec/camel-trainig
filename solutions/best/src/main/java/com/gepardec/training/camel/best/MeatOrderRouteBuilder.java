package com.gepardec.training.camel.best;

import com.gepardec.training.camel.commons.domain.OrderToProducer;
import com.gepardec.training.camel.commons.processor.ExceptionLoggingProcessor;
import org.apache.camel.Endpoint;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.TransformerBuilder;
import org.apache.camel.cdi.Uri;
import org.apache.camel.model.dataformat.BindyDataFormat;
import org.apache.camel.model.dataformat.BindyType;
import org.apache.camel.model.dataformat.JaxbDataFormat;
import org.apache.camel.model.dataformat.JsonDataFormat;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public final class MeatOrderRouteBuilder extends RouteBuilder {

    public static final String ENTRY_SEDA_ENDOINT_URI = "seda://meat_order_entry";
    public static final String OUTPUT_FILE_ENDPOINT_URI = "file:target/orders/meat";

    @Inject
    @Uri(ENTRY_SEDA_ENDOINT_URI)
    private Endpoint entryEndpoint;

    @Inject
    @Uri(OUTPUT_FILE_ENDPOINT_URI)
    private Endpoint fileEndpoint;

    @Override
    public void configure() {

        transformer()
                .fromType(OrderToProducer.class)
                .toType("json:CSVOrder")
                .withDataFormat(new JsonDataFormat());

        onException(Exception.class)
                .process(new ExceptionLoggingProcessor())
                .handled(true);


        from(entryEndpoint).routeId(ENTRY_SEDA_ENDOINT_URI)
                .validate(exchange -> exchange.getIn().getBody(OrderToProducer.class).getAmount() >= 100)
                .to(fileEndpoint);

        from("direct://tofile").routeId("direct_tofile")
                .inputType("json:JSONOrder")
                .to(fileEndpoint);
    }
}
