package service;

import load.Load;

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
	
	public Service(Double price) {
		// DUDA: es necesario? si es una clase abstracta que no se instancia
		this.price = price;
	}
	
	/**
	 * Este método es necesario que sea implementado por las subclases.
	 *
	 * Permite calcular el precio total que se cobrará al servicio aplicado a determinada carga.
	 *
	 * @param load la carga a la que se aplicará el servicio. Una instancia de tipo Load.
	 * 
	 * @author Gabriela Fascetta
	 */
	
	public abstract Double getPriceTo(Load load);

}
