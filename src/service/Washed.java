package service;

import order.Order;

/**
 * Clase que representa al servicio de Lavado.
 * 
 * Hereda de la clase abstracta Service.
 * 
 * @author Gabriela Fascetta
 */
public class Washed extends Service {
	
	//en metros cubicos
	private final int maxVolumePerMinimumFee = 70;
	
	/**
	 * Constructor de la clase Lavado.
	 * 
	 * Crea una instancia de la clase Lavado.
	 * 
	 * @param order la orden que conoce la carga a la que se aplicará el servicio. Una instancia tipo Order.
	 */
	public Washed(Double price, Double extraPrice) {
		super(price);
		this.secondPrice = extraPrice;
	}
	
	/**
	 *getPriceFor(order)
	 *
	 * Permite calcular el precio total que se cobrará al servicio de Lavado
	 * aplicado por una orden dada a determinada carga.
	 *
	 * @param order la orden que conoce la carga a la que se aplicará el servicio. Una instancia tipo Order.
	 */
	@Override
	public Double getPriceFor(Order order) {
		if (order.getLoadVolume() <= maxVolumePerMinimumFee) {
			return price;
		} else return secondPrice;
	}

	public Double getBigVolumePrice() {
		return secondPrice;
	}

}
