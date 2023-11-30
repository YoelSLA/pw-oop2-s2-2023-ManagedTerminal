package bill;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import order.Order;

public class Bill {
	private LocalDateTime broadcastDate;
	private Double totalAmount;
	private Order order;

	public Bill(Order order) {
		this.order = order;
		this.broadcastDate = LocalDateTime.now();
		this.totalAmount = 0.0;		
	}

	public Double getTotalAmountPerServices() {
		return order.getServices()
					.stream()
					.mapToDouble(serv -> serv.getPriceFor(order))
					.sum();
	}
	
	public Double getTotalAmountPerTrip() {
		
		return order.getTripCost();
	}
	
	public Double getTotalAmountToPay() {
		
		totalAmount = getTotalAmountPerServices() + getTotalAmountPerTrip();
		
		return totalAmount;
	}
	
	public void printInvoice() {
		// La factura contiene el desgloce de conceptos
		StringBuilder sb = new StringBuilder("ServiceName,Date,Price\r\n");
		order.getServices()
			.stream()
			.forEach( serv -> sb.append(
								String.format("%s,%s,%s\r\n",
											serv.getName(), broadcastDate, serv.getPriceFor(order)
								)
						)
			);
		sb.append("\r\n Trip: ");
		sb.append(this.getTotalAmountPerTrip().toString());
		sb.append("\r\n Total: ");
		sb.append(this.getTotalAmountToPay().toString());
		
		System.out.println(sb.toString());
	}
	
	
	
	public Order getOrderToProcess() {
		return this.order;
	}
}
