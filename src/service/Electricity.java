package service;

import java.util.Date;

import load.Load;

public class Electricity extends Service{
	/**
	 * Clase que representa al servicio de electricidad.
	 * 
	 * Hereda de la clase abstracta Service.
	 * 
	 * @author Gabriela Fascetta
	 */
	
	
	private Date startConnection;


	private Date endConnection;	
	
	
	/**
	 * Constructor de la clase Electricity.
	 * 
	 * Crea una instancia de la clase Electricity.
	 * 
	 * @param price precio fijo de referencia por kw/hora consumido.
	 * @param startConnection  La fecha en que se conecta la carga a la terminal.
	 * @param endConnection La fecha en que se desconecta la carga de la terminal.
	 *
	 * @author Gabriela Fascetta
	 */
	
	public Electricity(Double price, Date startConnection, Date endConnection) {
		super(price);
		this.startConnection = startConnection;
		this.endConnection = endConnection;
	}
	
	/**
	 *getPriceTo(load)
	 *
	 * Permite calcular el precio total que se cobrará al servicio de electricidad
	 * aplicado a determinada carga.
	 *
	 * @param load la carga a la que se aplicará el servicio. Una instancia de tipo Load.
	 * 
	 * @author Gabriela Fascetta
	 */
	
	@Override
	public Double getPriceTo(Load load) {
		return null; //TODO: implementar!
	}
	
	
	public void startConnection() {}
	
	public void endConnection() {}
	
	public Date getStartConnection() {
		return startConnection;
	}

	public Date getEndConnection() {
		return endConnection;
	}
	
}
