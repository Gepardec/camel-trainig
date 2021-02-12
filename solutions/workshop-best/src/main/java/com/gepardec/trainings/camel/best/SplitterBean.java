package com.gepardec.trainings.camel.best;

import com.gepardec.training.camel.commons.domain.Order;
import com.gepardec.training.camel.commons.domain.OrderToProducer;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
@Named("splitter")
public class SplitterBean {

    public List<OrderToProducer> split (Order order){
        return order.getItems().stream()
                .map(item -> new OrderToProducer(item, order.getPartnerId()))
                .collect(Collectors.toList());
    }

}
