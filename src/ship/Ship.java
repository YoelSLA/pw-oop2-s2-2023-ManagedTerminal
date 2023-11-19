package ship;
import phase.Phase;
import geographicalPosition.GeographicalPosition;
/**
 * @author alejandrabesel
 * Clase que representa un buque.
 * 
 * Esta clase gestiona la información del buque a realizar el viaje, incluyendo su nombre y codigo IMO 
 * (indetificacion univoca de cada buque).
 */

public class Ship {

	private String name;
	private Phase phase;
	private GeographicalPosition geographicalPosition;
	/**
	 * @author alejandrabesel
	 * Un codigo identificador de un buque generado por el String "IMO-" seguido de 7 letras (IMO-XXXXXXX)
	 */
	private String imo;
	
	/**
	 * @author alejandrabesel
	 * Constructor de la clase Ship.
	 * 
	 * Crea una instancia de la clase Ship con el nomrbe y el codigo imo identificador de cada buque.
	 * 
	 * @param name  El nombre del buque.
	 * @param imo  El codigo indetificador del buque en formato "IMO-XXXXXXX".
	 * 
	 */
	public Ship(String name, String imo) {
		setName(name);
		setImo(imo);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImo() {
		return imo;
	}

	public void setImo(String imo) {
		this.imo = imo;
	}
	
	/**
	 * Devuelve la fase en la que se encuentra el buque.
	 */
	public Phase getPhase() {
		return phase;
	}
	
	/**
	 * Devuelve la posicion geografica actual en la que se encuentra el buque gracias a su sistema de gps.
	 */
	public GeographicalPosition getPosition() {
		return geographicalPosition;
	}

	/**
	 * Setea la nueva posicion geografica del buque que le devuelve dicho gps. 
	 */
	public void setPosition(GeographicalPosition newPosition) {
		this.geographicalPosition = newPosition;
	}
}
