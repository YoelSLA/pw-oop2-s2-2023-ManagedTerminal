package bill;

import java.util.Date;

import order.Order;

/**
 * Clase que representa una Factura.
 * 
 * Esta clase gestiona la información de los servicios aplicados a la carga.
 * 
 * @author Gabriela Fascetta
 */
public class Bill {
	//private Date broadcastDate;
	private Double totalAmount= 0.0;
	private Order order;
	
	
	public Bill(Order order) {
		//this.broadcastDate = ;
		this.order = order;
	}
	
	public Double getTotalAmount() {
		//TODO: revisar este metodo y responsabilidades de los obj xq esta MUY feo
		return order.getServices().mapToDouble(serv -> serv.getPriceTo(order.getLoad())).sum();
	}
	
}
