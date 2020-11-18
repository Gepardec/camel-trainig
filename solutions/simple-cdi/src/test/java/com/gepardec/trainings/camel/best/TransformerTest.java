package com.gepardec.trainings.camel.best;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import com.gepardec.training.camel.commons.domain.OrderItem;


public class TransformerTest extends CamelTestSupport {

 
    @Test
    public void inputType_uses_transformer() throws InterruptedException {

        MockEndpoint resultEndpoint = resolveMandatoryEndpoint("mock:result", MockEndpoint.class);
        
        resultEndpoint.expectedBodiesReceived(createOrderDto());
                
        template.sendBody("direct:inputTypeTransformer", createOrder());
        
        resultEndpoint.assertIsSatisfied();
    }
    

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            public void configure() {
            	
                transformer()
                .fromType(OrderItem.class)
                .toType(OrderItemDto.class)
                .withJava(OrderItemTransformer.class);              
              
                from("direct:inputTypeTransformer").inputType(OrderItemDto.class)
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