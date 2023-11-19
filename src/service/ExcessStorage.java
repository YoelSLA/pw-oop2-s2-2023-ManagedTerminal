package service;

import load.Load;

public class ExcessStorage extends Service{
	/**
	 * Clase que representa al servicio de almacenamiento excedente
	 * por parte de una carga en una terminal.
	 * 
	 * Hereda de la clase abstracta Service.
	 * 
	 * @author Gabriela Fascetta
	 */
	
	/**
	 * Constructor de la clase ExcessStorage.
	 * 
	 * Crea una instancia de la clase ExcessStorage.
	 * 
	 * @param price precio fijo de referencia por dia excedido en el almacenamiento.
	 * k
	 * @author Gabriela Fascetta
	 */
	
	public ExcessStorage(Double price) {
		super(price);
	}
	
	/**
	 *getPriceTo(load)
	 *
	 * Permite calcular el precio total que se cobrará al servicio de ExcessStorage
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
