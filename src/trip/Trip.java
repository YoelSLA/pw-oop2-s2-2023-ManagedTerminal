package trip;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import maritimeCircuit.MaritimeCircuit;
import ship.Ship;
import terminal.Terminal;

public class Trip {

	private MaritimeCircuit maritimeCircuit;
	private Ship ship;
	private LocalDateTime startDate;

	public Trip(MaritimeCircuit maritimeCircuit, Ship ship, LocalDateTime startDate) {
		this.maritimeCircuit = maritimeCircuit;
		this.ship = ship;
		this.startDate = startDate;
	}

	/**
	 * Calcula la fecha estimada de llegada a una terminal específica en el circuito
	 * marítimo.
	 *
	 * @param terminal Terminal de destino para la cual se calculará la fecha de
	 *                 llegada.
	 * @return Fecha estimada de llegada a la terminal especificada.
	 * @throws Exception
	 */
	public LocalDateTime calculateEstimatedArrivalDateToTerminal(Terminal terminal) throws Exception {
		// Se obtiene la terminal de origen del circuito marítimo.
		final Terminal ORIGIN_TERMINAL = maritimeCircuit.originTerminal();

		// Se calculan las horas totales hasta la llegada a la terminal de destino.
		final Integer HOURS_TO_ARRIVAL = maritimeCircuit.calculateTotalHoursBetweenTerminals(ORIGIN_TERMINAL, terminal);

		// Se suma las horas al startDate para obtener la fecha estimada de llegada a la
		// terminal de destino.
		return startDate.plus(HOURS_TO_ARRIVAL, ChronoUnit.HOURS);

	}

	public MaritimeCircuit getMaritimeCircuit() {
		return maritimeCircuit;
	}

	/**
	 * Obtiene la siguiente terminal en el circuito marítimo después de la terminal
	 * proporcionada.
	 *
	 * @param terminal Terminal de referencia.
	 * @return Siguiente terminal en el circuito marítimo.
	 */
	public Terminal getNextTerminal(Terminal terminal) {
		return maritimeCircuit.getNextTerminalInCircuit(terminal);
	}

	/**
	 * Obtiene la terminal de origen del circuito marítimo de la travesía.
	 *
	 * @return Terminal de origen del circuito marítimo.
	 */
	public Terminal getOriginTerminal() {
		return maritimeCircuit.originTerminal();
	}

	public Ship getShip() {
		return ship;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	/**
	 * Verifica si el viaje tiene una terminal específica en su circuito marítimo.
	 *
	 * @param terminal Terminal a verificar.
	 * @return true si la el viaje tiene la terminal, false de lo contrario.
	 */
	public Boolean hasTerminal(Terminal terminal) {
		return maritimeCircuit.hasATerminal(terminal);
	}

}
