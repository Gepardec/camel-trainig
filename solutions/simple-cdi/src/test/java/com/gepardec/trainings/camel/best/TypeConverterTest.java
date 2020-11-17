package com.gepardec.trainings.camel.best;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import com.gepardec.training.camel.commons.domain.OrderItem;


public class TypeConverterTest extends CamelTestSupport {

    @Test
    public void convertBodyTo_uses_type_converter() throws InterruptedException {

        MockEndpoint resultEndpoint = resolveMandatoryEndpoint("mock:result", MockEndpoint.class);
        
        resultEndpoint.expectedBodiesReceived(createOrderDto());
        
        context().getTypeConverterRegistry().addTypeConverters(new OrderTypeConverters());
        
        template.sendBody("direct:convert", createOrder());
        
        resultEndpoint.assertIsSatisfied();
    }
    
    @Test
    public void bean_uses_type_converter_implicite() throws InterruptedException {

        MockEndpoint resultEndpoint = resolveMandatoryEndpoint("mock:result", MockEndpoint.class);
        
        resultEndpoint.expectedBodiesReceived(createOrderDto().setAmount(16));
        
        context().getTypeConverterRegistry().addTypeConverters(new OrderTypeConverters());
        
        template.sendBody("direct:bean", createOrder());
        
        resultEndpoint.assertIsSatisfied();
    }

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            public void configure() {
            	
                from("direct:convert")
                .convertBodyTo(OrderItemDto.class)
                .to("mock:result");
                
                from("direct:bean")
                .bean(new OrderDtoProcessor())
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