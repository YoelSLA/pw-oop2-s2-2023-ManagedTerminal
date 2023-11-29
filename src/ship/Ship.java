package ship;

import geographicalPosition.GeographicalPosition;
import phase.Phase;
import trip.Trip;

/**
 * @author alejandrabesel Clase que representa un buque.
 * 
 *         Esta clase gestiona la información del buque a realizar el viaje,
 *         incluyendo su nombre y codigo IMO (indetificacion univoca de cada
 *         buque).
 */

public class Ship {

	private String name;
	private Phase phase;
	private GeographicalPosition geographicalPosition;
	/**
	 * @author alejandrabesel Un codigo identificador de un buque generado por el
	 *         String "IMO-" seguido de 7 letras (IMOXXXXXXX)
	 */
	private String imo;
	private Trip trip;
	private boolean isOnTrip;

	/**
	 * @author alejandrabesel Constructor de la clase Ship.
	 * 
	 *         Crea una instancia de la clase Ship con el nomrbe y el codigo imo
	 *         identificador de cada buque.
	 * 
	 * @param name  El nombre del buque.
	 * @param imo   El codigo indetificador del buque en formato "IMO-XXXXXXX".
	 * @param phase La fase en la que se encuentra el buque.
	 * 
	 */
	public Ship(String name, String imo, GeographicalPosition geographicalPosition) {
		setName(name);
		setImo(imo);
		setPhase(phase);
		setPosition(geographicalPosition);
	}

	public String getName() {
		return name;
	}

	private void setName(String name) {
		this.name = name;
	}

	public String getImo() {
		return imo;
	}

	private void setImo(String imo) {
		this.imo = imo;
	}

	private void setPhase(Phase phase) {
		this.phase = phase;
	}

	/**
	 * Devuelve la fase en la que se encuentra el buque.
	 */
	public Phase getPhase() {
		return phase;
	}

	/**
	 * Devuelve la posicion geografica actual en la que se encuentra el buque
	 * gracias a su sistema de gps.
	 */
	public GeographicalPosition getPosition() {
		return geographicalPosition;
	}

	/**
	 * Setea la nueva posicion geografica del buque que le devuelve dicho gps.
	 * Observacion: la dejo en private porque entiendo que el buque se setea su
	 * propia posicion
	 */
	private void setPosition(GeographicalPosition newPosition) {
		this.geographicalPosition = newPosition;
	}

	public void otorgarViaje(Trip trip) {
		this.trip = trip;
	}

	public Trip getTrip() {
		return trip;
	}

	public boolean getIsOnTrip() {
		return isOnTrip;
	}

	public void startTrip() {
		// TODO Auto-generated method stub

	}
}
