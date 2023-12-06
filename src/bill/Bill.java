package bill;

import order.Order;

public class Bill {
//	private LocalDateTime broadcastDate;
//	private Double totalAmount;
	private Order order;

	public Bill(Order order) {
		this.order = order;

	}

//
//	public Double getTotalAmountPerServices() {
//		return order.getServices().stream().mapToDouble(serv -> serv.getPriceFor(order.getLoad())).sum();
//	}
//
////	public Double getTotalAmountPerTrip() {
////		
////		return order.getTripCost();
////	} TODO: IMPLEMENTAR / REVISAR
//
////	public Double getTotalAmountToPay() {
////
////		totalAmount = getTotalAmountPerServices() + getTotalAmountPerTrip();
////
////		return totalAmount;
////	} TODO: IMPLEMENTAR / REVISAR
//
////	public void printInvoice() {
////		// La factura contiene el desgloce de conceptos
////		StringBuilder sb = new StringBuilder("ServiceName,Date,Price\r\n");
////		order.getServices().stream().forEach(serv -> sb.append(
////				String.format("%s,%s,%s\r\n", serv.getName(), broadcastDate, serv.getPriceFor(order.getLoad()))));
////		sb.append("\r\n Trip: ");
////		sb.append(this.getTotalAmountPerTrip().toString());
////		sb.append("\r\n Total: ");
////		sb.append(this.getTotalAmountToPay().toString());
////
////		System.out.println(sb.toString());
////	} TODO: IMPLEMENTAR / REVISAR
//
	public Order getOrder() {
		return this.order;
	}
}
