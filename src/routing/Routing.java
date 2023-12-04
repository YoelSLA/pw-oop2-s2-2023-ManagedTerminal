package routing;

import java.util.Comparator;
import java.util.List;

import maritimeCircuit.MaritimeCircuit;
import stretch.Stretch;
import terminal.ManagedTerminal;
import terminal.Terminal;

public abstract class Routing {

	/**
	 * Encuentra el mejor circuito marítimo entre dos terminales.
	 *
	 * @param origin           Terminal de origen.
	 * @param destiny          Terminal de destino.
	 * @param maritimeCircuits Lista de circuitos marítimos disponibles.
	 * @return El mejor circuito marítimo.
	 */
	public final MaritimeCircuit bestCircuitBetween(ManagedTerminal origin, Terminal destiny,
			List<MaritimeCircuit> maritimeCircuits) {
		validateMaritimeCircuits(maritimeCircuits);
		validateTerminalDestinyIn(destiny, maritimeCircuits);
		return maritimeCircuits.stream()
				.min(Comparator.comparingDouble(circuit -> calculateSpecificRouting(origin, destiny, circuit)))
				.orElse(maritimeCircuits.get(0));
	}

	/**
	 * Calcula el enrutamiento especifico para un circuito marítimo entre dos
	 * terminales.
	 *
	 * @param origin          Terminal de origen.
	 * @param destiny         Terminal de destino.
	 * @param maritimeCircuit Circuito marítimo para el cálculo.
	 * @return El resultado del cálculo de enrutamiento.
	 */
	private final Double calculateSpecificRouting(ManagedTerminal origin, Terminal destiny,
			MaritimeCircuit maritimeCircuit) {
		int startPosition = maritimeCircuit.getPositionOf(origin);
		int endPosition = maritimeCircuit.getPositionOf(destiny);
		return calculateRouting(maritimeCircuit.getStretches().subList(startPosition, endPosition));
	}

	/**
	 * Valida que la lista de circuitos marítimos no esté vacía.
	 *
	 * @param maritimeCircuits Lista de circuitos marítimos.
	 * @throws RuntimeException si la lista está vacía.
	 */
	private void validateMaritimeCircuits(List<MaritimeCircuit> maritimeCircuits) {
		if (maritimeCircuits.isEmpty()) {
			throw new RuntimeException("The maritime circuit must not be empty.");
		}
	}

	/**
	 * Valida que el destino esté presente en al menos uno de los circuitos
	 * marítimos.
	 *
	 * @param destiny          Terminal de destino.
	 * @param maritimeCircuits Lista de circuitos marítimos.
	 * @throws RuntimeException si el destino no está en la lista de circuitos
	 *                          marítimos.
	 */
	private void validateTerminalDestinyIn(Terminal destiny, List<MaritimeCircuit> maritimeCircuits) {
		if (maritimeCircuits.stream().noneMatch(circuit -> circuit.hasATerminal(destiny))) {
			throw new RuntimeException("The destiny must be in the maritime circuits.");
		}
	}

	/**
	 * Calcula el enrutamiento para un conjunto de tramos.
	 *
	 * @param stretches Lista de tramos.
	 * @return El resultado del cálculo de enrutamiento específico.
	 */
	protected abstract Double calculateRouting(List<Stretch> stretches);

}
