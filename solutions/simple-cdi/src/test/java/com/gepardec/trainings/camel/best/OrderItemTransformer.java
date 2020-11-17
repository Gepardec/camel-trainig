package com.gepardec.trainings.camel.best;

import org.apache.camel.Message;
import org.apache.camel.spi.DataType;
import org.apache.camel.spi.Transformer;

import com.gepardec.training.camel.commons.domain.OrderItem;

public class OrderItemTransformer extends Transformer{

	@Override
	public void transform(Message message, DataType from, DataType to) throws Exception {
		// todo some checks for from and to
		message.setBody(OrderTypeConverters.toDto(message.getBody(OrderItem.class)));
		
	}

}
