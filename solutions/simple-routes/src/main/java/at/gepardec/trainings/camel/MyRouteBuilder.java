package at.gepardec.trainings.camel;

import org.apache.camel.builder.RouteBuilder;

/**
 * A Camel Java DSL Router
 */
public class MyRouteBuilder extends RouteBuilder {

    /**
     * Let's configure the Camel routing rules using Java code...
     */
    public void configure() {

        from("file:target/messages/others?noop=true")
        .to("file:target/messages/somewhere");
        
        from("file:target/messages/at?noop=true")
        .to("activemq:wien");       
 
        from("activemq:wien")
        .to("log:read-wien")
        .to("file:target/messages/wien");       

    }

}
