package bill;

import java.time.LocalDateTime;

import order.Order;

public class Bill {

	private LocalDateTime broadcastDate;
	private Order order;

	public Bill(LocalDateTime broadcastDate, Order order) {
		this.broadcastDate = broadcastDate;
		this.order = order;

	}

	public LocalDateTime getBroadcastDate() {
		return broadcastDate;
	}

	public Order getOrder() {
		return this.order;
	}

}
