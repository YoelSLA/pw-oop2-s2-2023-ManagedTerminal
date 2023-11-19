package service;

import load.Load;

public class Washed extends Service {
	
	/**
	 * Clase que representa al servicio de Lavado
	 * aplicado a una carga en una terminal.
	 * 
	 * Hereda de la clase abstracta Service.
	 * 
	 * @author Gabriela Fascetta
	 */
	
	/**
	 * Constructor de la clase Lavado.
	 * 
	 * Crea una instancia de la clase Lavado.
	 * 
	 * @param price precio fijo de referencia por la aplicación del servicio Lavado.
	 * k
	 * @author Gabriela Fascetta
	 */

	public Washed(Double price) {
		super(price);
	}
	
	/**
	 *getPriceTo(load)
	 *
	 * Permite calcular el precio total que se cobrará al servicio de Lavado
	 * aplicado a determinada carga.
	 *
	 * @param load la carga a la que se aplicará el servicio. Una instancia de tipo Load.
	 * 
	 * @author Gabriela Fascetta
	 */
	
	@Override
	public Double getPriceTo(Load load) {
		return 0.0; //TODO: implementar!
	}

}
