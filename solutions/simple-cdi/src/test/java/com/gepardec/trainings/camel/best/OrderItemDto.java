package com.gepardec.trainings.camel.best;

public class OrderItemDto {

	public long type;
	public int amount;
	
	public OrderItemDto() {
	}

	public OrderItemDto(long code, int amount) {
		this.type = code;
		this.amount = amount;
	}

	public long getType() {
		return type;
	}
	public void setType(long type) {
		this.type = type;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + amount;
		result = prime * result + (int) (type ^ (type >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderItemDto other = (OrderItemDto) obj;
		if (amount != other.amount)
			return false;
		if (type != other.type)
			return false;
		return true;
	}
}
