package service;

import load.Load;
import order.Order;

/**
 * Clase que representa al servicio de pesado.
 * 
 * Hereda de la clase abstracta Service.
 * 
 * @author Gabriela Fascetta
 */
public class Weigh extends Service {
	
	/**
	 * Constructor de la clase Pesado.
	 * 
	 * Crea una instancia de la clase Pesado.
	 * 
	 * @param price precio fijo de referencia por tonelada;
	 */
	public Weigh(Double price) {
		super(price);
	}
		
	public Double weighOn(Load load) {
		return load.getWeight();
	}

	/**
	 *getPriceTo(load)
	 *
	 * Permite calcular el precio total que se cobrará al servicio de Pesado
	 * aplicado a una carga dada.
	 *
	 * @param order la orden que conoce la carga a la que se aplicará el servicio. Una instancia tipo Order.
	 * 
	 */
	@Override
	public Double getPriceFor(Order order) {
		return price;
	}
}
