package turn;

import java.time.LocalDateTime;
import order.Order;

public class Turn {

	private LocalDateTime date;
	private Order order;

	public Turn(LocalDateTime date, Order order) {
		this.date = date;
		this.order = order;
	}

	public LocalDateTime getDate() {
		return date;
	}
	
	public Order getOrder() {
		return order;
	}

}
