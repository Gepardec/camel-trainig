package com.gepardec.trainings.camel.best;

import org.apache.camel.Converter;
import org.apache.camel.TypeConverters;

import com.gepardec.training.camel.commons.domain.OrderItem;

public class OrderTypeConverters implements TypeConverters {

	@Converter
	public static OrderItemDto toDto(OrderItem item) {
		return new OrderItemDto(item.getCode(), item.getAmount());		
	}
	
	@Converter
	public static OrderItem fromDto(OrderItemDto item) {
		return new OrderItem(item.getType(), item.getAmount());		
	}
}
