package service;

import load.Load;

public class Weigh extends Service {
	/**
	 * Clase que representa al servicio de pesado
	 * aplicado a una carga en una terminal.
	 * 
	 * Hereda de la clase abstracta Service.
	 * 
	 * @author Gabriela Fascetta
	 */
	
	/**
	 * Constructor de la clase Pesado.
	 * 
	 * Crea una instancia de la clase Pesado.
	 * 
	 * @param price precio fijo de referencia por kilo/tonelada?.
	 * k
	 * @author Gabriela Fascetta
	 */
	
	public Weigh(Double price) {
		super(price);
	}
	
	/**
	 *getPriceTo(load)
	 *
	 * Permite calcular el precio total que se cobrará al servicio de Pesado
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
	
	public Double weighOn(Load load) {
		return 0.0; //TODO: implementar!
	}

}
