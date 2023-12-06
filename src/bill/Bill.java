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

	public void printInvoice() {
		System.out.println("Broadcast Date: " + broadcastDate);

		System.out.println("Services:");

		order.getServices().forEach(service -> System.out.println(" - " + service.getName() + ": $" + service.getPrice()));
		
		System.out.println("Total COST SERVICES: $" + order.priceOfServices());
		
		System.out.println("TRIP:");
		
		System.out.println(order.getOrigin().getName() + "  A  " + order.getDestiny().getName());
		
		System.out.println("TOTAL COST TRIP: $" + order.travelCost());
		
		System.out.println("TOTAL COST: $" + (order.travelCost() + order.priceOfServices()));
		

	}
}
