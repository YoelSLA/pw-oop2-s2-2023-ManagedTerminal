package service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import order.Order;

/**
 * Clase que representa al servicio de electricidad.
 * 
 * Hereda de la clase abstracta Service e implementa metodo getPriceTo(unaLoad).
 * 
 * @author Gabriela Fascetta
 */
public class Electricity extends Service{
		
	
	private LocalDateTime startConnection;
	private LocalDateTime endConnection;	
	
	
	/**
	 * Constructor de la clase Electricity.
	 * 
	 * Crea una instancia de la clase Electricity.
	 * 
	 * @param price precio fijo de referencia por kw/hora consumido.
	 * @param startConnection  La fecha en que se conecta la carga a la terminal.
	 * @param endConnection La fecha en que se desconecta la carga de la terminal.
	 *
	 */
	public Electricity(Double price, LocalDateTime startConnection, LocalDateTime endConnection) {
		super(price);
		this.startConnection = startConnection;
		this.endConnection = endConnection;
	}
	
	/**
	 *getPriceFor(order)
	 *
	 * Permite calcular el precio total que se cobrará al servicio de electricidad
	 * aplicado a la carga dada.
	 *
	 * @param order la orden que conoce la carga a la que se aplicará el servicio. Una instancia tipo Order.
	 * 
	 */
	@Override
	public Double getPriceFor(Order order) {
		// TODO: validar si es contenedor reefer?
		
		Double hoursConnected = (double) ChronoUnit.HOURS.between(startConnection, endConnection);
		return order.getLoadEnergyConsumption() * price * hoursConnected; 
	}
	
	public LocalDateTime getStartConnection() {
		return startConnection;
	}

	public LocalDateTime getEndConnection() {
		return endConnection;
	}
	
}
