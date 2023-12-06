package ship;

import phase.Outbound;
import phase.Phase;
import position.Position;
import terminal.Terminal;
import trip.Trip;

/**
 * @author alejandrabesel Clase que representa un buque.
 * 
 *        Esta clase gestiona la información del buque a realizar el viaje,
 *        incluyendo su nombre y codigo IMO (indetificacion univoca de cada
 *        buque).
 */

public class Ship {

	private String name;
	private Phase phase;
	private Position position;
	/**
	 * @author alejandrabesel Un codigo identificador de un buque generado por el
	 *         String "IMO-" seguido de 7 letras (IMOXXXXXXX)
	 */
	private String imo;
	private Trip trip;
	private Terminal terminal;
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
	 * @throws Exception 
	 * 
	 */
	public Ship(String name, String imo, Trip trip) {
		this.name = name;
		this.imo = imo;
		this.trip = trip;
		this.position = trip.getOriginTerminal().getPosition();
		this.terminal = trip.getOriginTerminal();
		isOnTrip = false;
	}
	
	// ----------------------------------------------------------------------------------------------------
	// GETTERS
	// ----------------------------------------------------------------------------------------------------
	
	public String getName() {
		return name;
	}

	public String getImo() {
		return imo;
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
	public Position getPosition() {
		return position;
	}
	
	public Trip getTrip() {
		return trip;
	}

	public boolean getIsOnTrip() {
		return isOnTrip;
	}
	
	public Terminal getTerminal() {
		return this.terminal;
	}
	
	// ----------------------------------------------------------------------------------------------------
	// SETTERS
	// ----------------------------------------------------------------------------------------------------
	
	/**
	 * Setea el viaje al buque. Devuelve una excepcion en caso que ya exista un viaje en curso
	 */
	
	public void setTrip(Trip trip) throws Exception {
		if (isOnTrip) throw new RuntimeException("The ship has already started a trip");
		this.trip = trip;
	}

	public void setPhase(Phase phase) {
		this.phase = phase;
	}

	/**
	 * Setea la nueva posicion geografica del buque.
	 * Ademas,verifica con la fase si el buque cumple las condiciones para pasar a la proxima fase
	 */
	
	public void setPosition(Position newPosition) {
		this.position = newPosition;
		phase.proceedToNextPhase(this);
	}
	
	public void setNextTerminal() {
		terminal = trip.getNextTerminal(terminal);

	}
	
	// ----------------------------------------------------------------------------------------------------
	// ON TRIP METHODS
	// ----------------------------------------------------------------------------------------------------

	/**
	 * Comienza el viaje del buque. Para eso, setea al booleano isOnTrip como true,
	 * setea la fase como Outbound y ademas setea la nueva terminal de destino
	 */
	
	public void startTrip() {
		isOnTrip = true;
		setPhase(new Outbound());
		setNextTerminal();
	}
	
	/**
	 * Este metodo es llamado por la terminal cuando se le notifica que el buque ha llegado
	 * Setea el status para poder pasar a la fase Working
	 */
	public void startWorking() {
		// TODO Auto-generated method stub
		phase.setStatus(true);
	}

	/**
	 * Este metodo es llamado por la terminal cuando se le notifica que el buque ha terminado su descarga
	 * Setea el status para poder pasar a la fase Departing
	 */	
	public void depart() {
		phase.changePhase(this);
	}

	/**
	 * Calcula la distancia entre la posicion actual del buque y la posicion de la temrinal de desitno
	 */	
	public Integer calculateDistanceToTerminal() {
		return Position.distanceInKilometersBetween(this.getPosition(), terminal.getPosition());
	}

	public void notifyInminentArrival() {
		terminal.notifyShipInminentArrival(this);
	}

	public void notifyArrival() {
		terminal.notifyShipArrival(this);
	}

	public void notifyDeparture() {
		terminal.notifyShipDeparture(this);
	}
}
