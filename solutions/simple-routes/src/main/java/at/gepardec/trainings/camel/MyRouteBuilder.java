package at.gepardec.trainings.camel;

import org.apache.camel.builder.RouteBuilder;

public class MyRouteBuilder extends RouteBuilder {

    public void configure() {

        from("file:src/data?noop=true")
                .to("file:target/messages/result");
    }

}
