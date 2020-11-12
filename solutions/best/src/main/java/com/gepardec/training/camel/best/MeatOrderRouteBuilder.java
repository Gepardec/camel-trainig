package com.gepardec.training.camel.best;

import com.gepardec.training.camel.commons.domain.OrderToProducer;
import com.gepardec.training.camel.commons.processor.ExceptionLoggingProcessor;
import org.apache.camel.Endpoint;
import org.apache.camel.Predicate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.cdi.Uri;
import org.apache.camel.model.dataformat.JaxbDataFormat;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public final class MeatOrderRouteBuilder extends RouteBuilder {

    public static final String ENTRY_SEDA_ENDOINT_URI = "seda://meat_order_entry";
    public static final String OUTPUT_JMS_ENDPOINT_URI = "jms://queue:meat?disableReplyTo=true&username=quarkus&password=quarkus&connectionFactory=#JMSConnectionFactory";
    public static final String OUTPUT_JMS_ENDPOINT_ID = "jms_meat";

    @Inject
    @Uri(ENTRY_SEDA_ENDOINT_URI)
    private Endpoint entryEndpoint;

    @Inject
    @Uri(OUTPUT_JMS_ENDPOINT_URI)
    private Endpoint jmsEndpoint;

    @Override
    public void configure() {
        onException(Exception.class)
                .process(new ExceptionLoggingProcessor())
                .handled(true);

        JaxbDataFormat format = new JaxbDataFormat();

        from(entryEndpoint).routeId(ENTRY_SEDA_ENDOINT_URI)
                .filter(body().isInstanceOf(OrderToProducer.class))
//                .validate(new OwnPredicate().matches())
                .marshal(format)
                .log("MEAT ${body}").to(jmsEndpoint).id(OUTPUT_JMS_ENDPOINT_ID);
    }
}
