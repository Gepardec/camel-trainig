package com.gepardec.trainings.camel.best;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import com.gepardec.training.camel.commons.domain.OrderItem;


public class TypeConverterTest extends CamelTestSupport {

    @Test
    public void when_order_in_orders_message_is_in_processed() throws InterruptedException {

        MockEndpoint resultEndpoint = resolveMandatoryEndpoint("mock:result", MockEndpoint.class);
        
        resultEndpoint.expectedBodiesReceived(createOrderDto());
        
        context().getTypeConverterRegistry().addTypeConverters(new OrderTypeConverters());
        
        template.sendBody("direct:start", createOrder());
        
        resultEndpoint.assertIsSatisfied();
    }

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            public void configure() {
                from("direct:start")
                .convertBodyTo(OrderItemDto.class)
                .log("result-processor: ${body}")
                .to("mock:result");
            }
        };
    } 
    
	private OrderItem createOrder() {
		return new OrderItem(OrderItem.EGG, 15);
	}

	private OrderItemDto createOrderDto() {
		return new OrderItemDto(OrderItem.EGG, 15);
	}
}