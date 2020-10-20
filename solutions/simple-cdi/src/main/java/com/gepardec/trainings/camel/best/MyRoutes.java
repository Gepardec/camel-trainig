package com.gepardec.trainings.camel.best;

import javax.inject.Inject;

import org.apache.camel.Endpoint;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.cdi.Uri;


public class MyRoutes extends RouteBuilder {

    public static final String URL_FILE_ORDERS_IN = "file:src/orders?noop=true";
	public static final String URL_FILE_ORDERS_OUT = "file:target/orders/processed";

//    public static final String ID_FILE_ORDERS_IN = "file_src_orders";
//    public static final String ID_FILE_ORDERS_OUT = "file_target_orders_processed";
//    public static final String ID_ROUTE = "MyRoutes";

    @Inject
    @Uri(URL_FILE_ORDERS_IN)
    private Endpoint inputEndpoint;

    @Inject
    @Uri(URL_FILE_ORDERS_OUT)
    private Endpoint resultEndpoint;

    @Override
    public void configure() {
        onException(Exception.class)
                .handled(true);

        from(inputEndpoint)
                //.id(ID_FILE_ORDERS_IN)
                //.routeId(ID_ROUTE)
        .to(resultEndpoint);
        //.id(ID_FILE_ORDERS_OUT);
    }
}
