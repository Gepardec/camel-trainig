package com.gepardec.trainings.camel.best;

import javax.inject.Named;
import javax.inject.Singleton;

import com.gepardec.training.camel.commons.domain.Order;

@Singleton
@Named("someBean")
public class SomeBean {

	int counter = 0;
	
    public Order someMethod(Order order) {
    	order.setPartnerId(order.getPartnerId() + counter);
        return order;
    }

}
