package com.gepardec.training.camel.best;

import com.gepardec.training.camel.best.config.ExchangeHeaders;
import com.gepardec.training.camel.best.misc.OrderSplitter;
import com.gepardec.training.camel.commons.processor.ExceptionLoggingProcessor;
import org.apache.camel.builder.RouteBuilder;

public class SplitterRouteBuilder extends RouteBuilder {

    public static final String ENTRY_SEDA_ENDOINT_URI = "seda:best_splitter_entry";
    public static final String ENTRY_SEDA_ENDOINT_ID = "best_splitter_entry";

    public static final String ROUTE_ID = "SplitterRouteBuilder";

    @Override
    public void configure() throws Exception {
        onException(Exception.class)
                .process(new ExceptionLoggingProcessor())
                .handled(true);
        from(ENTRY_SEDA_ENDOINT_URI).id(ENTRY_SEDA_ENDOINT_ID)
                .routeId(ROUTE_ID)
                .setHeader(ExchangeHeaders.PARTNER_ID, simple("${body.partnerId}"))
                .split().method(OrderSplitter.class).streaming()
                .to("seda:splitterDestination").id("best_splitter_destination")
                .end();
    }
}
