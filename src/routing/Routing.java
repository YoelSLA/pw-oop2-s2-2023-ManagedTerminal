package routing;

import java.util.Comparator;
import java.util.List;

import maritimeCircuit.MaritimeCircuit;
import stretch.Stretch;
import terminal.ManagedTerminal;
import terminal.Terminal;

public abstract class Routing {

	/**
	 * Encuentra el mejor circuito mar�timo entre dos terminales.
	 *
	 * @param origin           Terminal de origen.
	 * @param destiny          Terminal de destino.
	 * @param maritimeCircuits Lista de circuitos mar�timos disponibles.
	 * @return El mejor circuito mar�timo.
	 * @throws Exception
	 */
	public final MaritimeCircuit bestCircuitBetween(ManagedTerminal origin, Terminal destiny,
			List<MaritimeCircuit> maritimeCircuits) throws Exception {
		validateMaritimeCircuits(maritimeCircuits);
		validateTerminalDestinyIn(destiny, maritimeCircuits);
		return maritimeCircuits.stream()
				.min(Comparator.comparingDouble(circuit -> calculateSpecificRouting(origin, destiny, circuit)))
				.orElse(null);
	}

	/**
	 * Calcula el enrutamiento especifico para un circuito mar�timo entre dos
	 * terminales.
	 *
	 * @param origin          Terminal de origen.
	 * @param destiny         Terminal de destino.
	 * @param maritimeCircuit Circuito mar�timo para el c�lculo.
	 * @return El resultado del c�lculo de enrutamiento.
	 * @throws Exception
	 */
	private final Double calculateSpecificRouting(ManagedTerminal origin, Terminal destiny,
			MaritimeCircuit maritimeCircuit) {
		int startPosition = maritimeCircuit.getPositionOf(origin);
		int endPosition = maritimeCircuit.getPositionOf(destiny);
		return calculateRouting(maritimeCircuit.getStretches().subList(startPosition, endPosition));
	}

	/**
	 * Valida que la lista de circuitos mar�timos no est� vac�a.
	 *
	 * @param maritimeCircuits Lista de circuitos mar�timos.
	 * @throws RuntimeException si la lista est� vac�a.
	 */
	private void validateMaritimeCircuits(List<MaritimeCircuit> maritimeCircuits) {
		if (maritimeCircuits.isEmpty()) {
			throw new RuntimeException("The maritime circuit must not be empty.");
		}
	}

	/**
	 * Valida que el destino est� presente en al menos uno de los circuitos
	 * mar�timos.
	 *
	 * @param destiny          Terminal de destino.
	 * @param maritimeCircuits Lista de circuitos mar�timos.
	 * @throws RuntimeException si el destino no est� en la lista de circuitos
	 *                          mar�timos.
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
	 * @return El resultado del c�lculo de enrutamiento espec�fico.
	 */
	protected abstract Double calculateRouting(List<Stretch> stretches);

}
