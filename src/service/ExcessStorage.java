package service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import order.Order;

/**
 * Clase que representa al servicio de almacenamiento excedente
 * por parte de una carga en una terminal.
 * 
 * Hereda de la clase abstracta Service.
 * 
 * @author Gabriela Fascetta
 */
public class ExcessStorage extends Service{
	
	/**
	 * Constructor de la clase ExcessStorage.
	 * 
	 * Crea una instancia de la clase ExcessStorage.
	 * 
	 * @param price precio fijo de referencia por dia excedido en el almacenamiento.
	 * 
	 */
	public ExcessStorage(Double price) {
		super(price);
		this.name = "ExcessStorage";
	}
	
	/**
	 *getPriceTo(load)
	 *
	 * Permite calcular el precio total que se cobrará al servicio de ExcessStorage
	 * aplicado a una orden dada con determinada carga.
	 * Calcula la diferencia entre la fecha actual y la dateTruck.
	 * @param order la orden que conoce la carga a la que se aplicará el servicio. Una instancia tipo Order.
	 * 
	 */
	@Override
	public Double getPriceFor(Order order) {
		int extraDays = (int) Math.abs((int) ChronoUnit.DAYS.between(LocalDate.now(), order.getDateTruck()));
		return price * extraDays;
	}
}
