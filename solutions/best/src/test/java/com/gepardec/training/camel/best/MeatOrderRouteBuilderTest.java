package com.gepardec.training.camel.best;

import com.gepardec.training.camel.commons.domain.OrderItem;
import com.gepardec.training.camel.commons.domain.OrderToProducer;
import com.gepardec.training.camel.commons.test.routetest.CamelRouteCDITest;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.cdi.Uri;
import org.apache.camel.test.cdi.Beans;
import org.apache.camel.test.cdi.CamelCdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

@RunWith(CamelCdiRunner.class)
@Beans(classes = MeatOrderRouteBuilder.class)
public class MeatOrderRouteBuilderTest extends CamelRouteCDITest {

    @Inject
    @Uri(MeatOrderRouteBuilder.ENTRY_SEDA_ENDOINT_URI)
    private ProducerTemplate seda;

    @Test
    public void createOrderItemSendToMeatEndpointAndCreateJsonFile() {
        OrderItem orderItem = new OrderItem();
        orderItem.setAmount(110);
        orderItem.setCode(4);
        OrderToProducer orderToProducer = new OrderToProducer(orderItem, 1);

        seda.sendBody(orderToProducer);

        //TODO get created json file and compare to orderitem amount
    }
}