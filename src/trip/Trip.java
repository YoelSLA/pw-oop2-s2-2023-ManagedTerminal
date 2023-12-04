package trip;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import maritimeCircuit.MaritimeCircuit;
import ship.Ship;
import terminal.Terminal;

public class Trip {

	private LocalDateTime startDate;
	private Ship ship;
	private MaritimeCircuit maritimeCircuit;

	public Trip(MaritimeCircuit maritimeCircuit, Ship ship, LocalDateTime startDate) {
		this.maritimeCircuit = maritimeCircuit;
		this.ship = ship;
		this.startDate = startDate;
	}

	public MaritimeCircuit getMaritimeCircuit() {
		return maritimeCircuit;
	}

	public Ship getShip() {
		return ship;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public boolean hasATerminal(Terminal terminal) {
		return maritimeCircuit.hasATerminal(terminal);
	}

	public Terminal originTerminal() {
		return maritimeCircuit.originTerminal();
	}

	public Terminal nextTerminalOf(Terminal terminal) {
		Integer indexTerminal = maritimeCircuit.originTerminals().indexOf(terminal);
		return maritimeCircuit.originTerminals().get(indexTerminal + 1);

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

	/**
	 * Calcula la fecha estimada de llegada a una terminal específica en el circuito
	 * marítimo.
	 *
	 * @param terminal Terminal de destino para la cual se calculará la fecha de
	 *                 llegada.
	 * @return Fecha estimada de llegada a la terminal especificada.
	 */
	public LocalDateTime calculateEstimatedArrivalDateToTerminal(Terminal terminal) {
		// Se obtiene la terminal de origen del circuito marítimo.
		final Terminal ORIGIN_TERMINAL = maritimeCircuit.originTerminal();

		// Se calculan las horas totales hasta la llegada a la terminal de destino.
		final Integer HOURS_TO_ARRIVAL = maritimeCircuit.calculateTotalHoursBetweenTerminals(ORIGIN_TERMINAL, terminal);

		// Se suma las horas al startDate para obtener la fecha estimada de llegada a la
		// terminal de destino.
		return startDate.plus(HOURS_TO_ARRIVAL, ChronoUnit.HOURS);

	}
}