package com.gepardec.trainings.camel.best;

import org.apache.camel.Handler;

public class OrderDtoProcessor {
	
	@Handler
	public OrderItemDto processDto(OrderItemDto dto) {
		dto.amount++;
		return dto;
	}

	public String doSomething(OrderItemDto dto) {
		return "Something";
	}


}
