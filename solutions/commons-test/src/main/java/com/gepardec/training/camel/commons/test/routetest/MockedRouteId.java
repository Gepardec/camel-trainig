package com.gepardec.training.camel.commons.test.routetest;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface MockedRouteId {
    String value();
}