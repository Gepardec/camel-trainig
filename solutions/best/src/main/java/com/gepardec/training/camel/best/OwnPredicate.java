package com.gepardec.training.camel.best;

import com.gepardec.training.camel.commons.domain.OrderToProducer;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;

public class OwnPredicate implements org.apache.camel.Expression, org.apache.camel.Predicate {

    @Override
    public <T> T evaluate(Exchange exchange, Class<T> type) {
        return null;
    }

    @Override
    public void init(CamelContext context) {

    }

    @Override
    public boolean matches(Exchange exchange) {
        OrderToProducer order = exchange.getIn().getBody(OrderToProducer.class);
        return order.getAmount() > 100;
    }
}
