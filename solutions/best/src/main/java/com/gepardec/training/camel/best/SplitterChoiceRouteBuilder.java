package com.gepardec.training.camel.best;

import com.gepardec.training.camel.best.config.ExchangeHeaders;
import com.gepardec.training.camel.best.domain.OrderItem;
import com.gepardec.training.camel.best.misc.OrderSplitter;
import com.gepardec.training.camel.best.misc.OrderTransformer;
import com.gepardec.training.camel.commons.processor.ExceptionLoggingProcessor;
import org.apache.camel.Endpoint;
import org.apache.camel.Predicate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.cdi.Uri;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public final class SplitterChoiceRouteBuilder extends RouteBuilder {

    public static final String ENTRY_SEDA_ENDOINT_URI = "seda:best_splitter_choice_entry";
    public static final String ENTRY_SEDA_ENDOINT_ID = "best_splitter_choice_entry";

    public static final String ROUTE_ID = "SplitterChoiceRouteBuilder";

    @Inject
    @Uri(EggOrderRouteBuilder.ENTRY_SEDA_ENDOINT_URI)
    Endpoint eggEndpoint;

    @Inject
    @Uri(MeatOrderRouteBuilder.ENTRY_SEDA_ENDOINT_URI)
    Endpoint meatEndpoint;

    @Inject
    @Uri(PastaOrderRouteBuilder.ENTRY_SEDA_ENDOINT_URI)
    Endpoint pastaEndpoint;

    @Inject
    @Uri(MilkOrderRouteBuilder.ENTRY_SEDA_ENDOINT_URI)
    Endpoint milkEndpoint;

    @Override
    public void configure() {
        onException(Exception.class)
                .process(new ExceptionLoggingProcessor())
                .handled(true);

        from(ENTRY_SEDA_ENDOINT_URI).id(ENTRY_SEDA_ENDOINT_ID)
                .routeId(ROUTE_ID)
                .setHeader(ExchangeHeaders.PARTNER_ID, simple("${body.partnerId}"))
                .split().method(OrderSplitter.class).streaming()

                .bean(OrderTransformer.class)
                .removeHeader(ExchangeHeaders.PARTNER_ID)
                .choice()
                .when(hasItemCode(OrderItem.EGG))
                .to(eggEndpoint).id(EggOrderRouteBuilder.ENTRY_SEDA_ENDOINT_ID)
                .when(hasItemCode(OrderItem.PASTA))
                .to(pastaEndpoint).id(PastaOrderRouteBuilder.ENTRY_SEDA_ENDOINT_ID)
                .when(hasItemCode(OrderItem.MILK))
                .to(milkEndpoint).id(MilkOrderRouteBuilder.ENTRY_SEDA_ENDOINT_ID)
                .when(hasItemCode(OrderItem.MEAT))
                .to(meatEndpoint).id(MeatOrderRouteBuilder.ENTRY_SEDA_ENDOINT_ID)
                .log("ERROR...")
                .end();

    }

    public Predicate hasItemCode(long itemCode) {
        return simple("${body.code} == " + itemCode);
    }
}
