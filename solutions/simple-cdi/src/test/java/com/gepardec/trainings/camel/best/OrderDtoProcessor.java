package com.gepardec.trainings.camel.best;

public class OrderDtoProcessor {
	
	public OrderItemDto processDto(OrderItemDto dto) {
		dto.amount++;
		return dto;
	}

}
