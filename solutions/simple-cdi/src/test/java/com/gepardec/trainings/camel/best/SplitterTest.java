package com.gepardec.trainings.camel.best;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;


public class SplitterTest extends CamelTestSupport {

    @Test
    public void when_order_in_orders_message_is_in_processed() throws InterruptedException {

    	MockEndpoint partsEndpoint = resolveMandatoryEndpoint("mock:result", MockEndpoint.class);
    	partsEndpoint.expectedBodiesReceived("a","b","c","d");

        MockEndpoint resultEndpoint = resolveMandatoryEndpoint("mock:result", MockEndpoint.class);
        resultEndpoint.expectedBodiesReceived("a,b,c,d");
        
        
        template.sendBody("direct:start", "a,b,c,d");
        
        partsEndpoint.assertIsSatisfied();
        resultEndpoint.assertIsSatisfied();
    }

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            public void configure() {
                from("direct:start")
                .split().tokenize(",")
                    .log("parts-processor: ${body}")
                    .to("mock:parts")
                .end()
                .log("result-processor: ${body}")
                .to("mock:result");
            }
        };
    } 
}