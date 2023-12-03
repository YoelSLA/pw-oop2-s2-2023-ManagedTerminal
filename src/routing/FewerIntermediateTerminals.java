package routing;

import java.util.Comparator;
import java.util.List;

import maritimeCircuit.MaritimeCircuit;
import terminal.ManagedTerminal;
import terminal.Terminal;

public class FewerIntermediateTerminals extends Routing {

	/**
	 * Encuentra y devuelve el circuito marítimo que tiene menos terminales
	 * intermedias entre un origen y un destino dados.
	 *
	 * @param origin           Terminal de origen gestionado.
	 * @param destiny          Terminal de destino.
	 * @param maritimeCircuits Lista de circuitos marítimos disponibles.
	 * @return El circuito marítimo con la menor de termminales intermedias entre el
	 *         origen y el destino.
	 * @throws RuntimeException Si hay problemas con la validación de los circuitos
	 *                          marítimos o la terminal de destino.
	 */
	@Override
	public MaritimeCircuit bestCircuitBetween(ManagedTerminal origin, Terminal destiny,
			List<MaritimeCircuit> maritimeCircuits) {

		validateMaritimeCircuits(maritimeCircuits);
		validateTerminalDestinyIn(destiny, maritimeCircuits);

		return maritimeCircuits.stream()
				.min(Comparator.comparingDouble(circuit -> calculateRouting(origin, destiny, circuit)))
				.orElseThrow(() -> new RuntimeException("No circuit found."));
	}

	/**
	 * Calcula la distancia entre dos terminales en un circuito marítimo.
	 *
	 * @param origin          Terminal de origen gestionado.
	 * @param destiny         Terminal de destino.
	 * @param maritimeCircuit Circuito marítimo en el que se calcula la distancia.
	 * @return La distancia entre las terminales en el circuito marítimo.
	 */
	protected Double calculateRouting(ManagedTerminal origin, Terminal destiny, MaritimeCircuit maritimeCircuit) {
		return (double) Math.abs(maritimeCircuit.getPositionOf(origin) - maritimeCircuit.getPositionOf(destiny));
	}

}
