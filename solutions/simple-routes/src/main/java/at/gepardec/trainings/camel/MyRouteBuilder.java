package at.gepardec.trainings.camel;

import org.apache.camel.builder.RouteBuilder;

public class MyRouteBuilder extends RouteBuilder {

    @Override
    public void configure() {
        from("file:src/data?noop=true")
                .choice()
                .when(xpath("/person/city = 'Vienna'"))
                .log("AT")
                .to("file:target/messages/result/vienna")
                .otherwise()
                .log("OTHER")
                .to("file:target/messages/result/others")
                .end();

        from("file:target/messages/result/vienna?noop=true")
                .to("activemq:wien");

        from("activemq:wien")
                .to("log:read-wien")
                .to("file:target/messages/result/wien");
    }

}
