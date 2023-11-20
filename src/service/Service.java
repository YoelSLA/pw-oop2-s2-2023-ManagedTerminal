package service;

import load.Load;
import order.Order;

/**
 * Clase que representa un servicio.
 * 
 * Esta clase abstracta provee un molde para creación de subclases (services).
 * Requiere setear un precio e implementar el algoritmo para calcular el precio
 * correspondiente a una carga o load.
 * 
 * @author Gabriela Fascetta
 */

public abstract class Service {

	protected Double price;
	protected Double secondPrice;
	
	public Service(Double price, Double extraPrice) {
		this.price = price;
		this.secondPrice = 0.0;
	}
	
	public Service(Double price) {
		this.price = price;
	}

	public Double getPrice() {return price;}
	
	/**
	 * Este método es necesario que sea implementado por las subclases.
	 *
	 * Permite calcular el precio total que se cobrará al servicio aplicado a determinada carga.
	 *
	 * @param order la orden que conoce la carga a la que se aplicará el servicio. Una instancia tipo Order.
	 * 
	 */
	
	public abstract Double getPriceFor(Order order);

}
