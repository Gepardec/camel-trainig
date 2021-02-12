package com.gepardec.trainings.camel.best;

import com.gepardec.training.camel.commons.processor.ExceptionLoggingProcessor;
import org.apache.camel.Endpoint;
import org.apache.camel.Predicate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.cdi.Uri;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public final class SplitterRouteBuilder extends RouteBuilder {

    public static final String SPLITTER_FROM_ENDOINT_URI = "seda://best_splitter_from";
    public static final String CHOICE_FROM_ENDOINT_URI = "direct://best_choice_from";

    @Override
    public void configure() {
        onException(Exception.class)
                .process(new ExceptionLoggingProcessor())
                .handled(true);

        //@formatter:off
        from(SPLITTER_FROM_ENDOINT_URI).routeId(SPLITTER_FROM_ENDOINT_URI)
                .split().method("splitter")
                    .to("log:im_splitter")
                .end();

        //@formatter:on


    }

    public Predicate hasItemCode(long itemCode) {
        return simple("${body.code} == " + itemCode);
    }
}
