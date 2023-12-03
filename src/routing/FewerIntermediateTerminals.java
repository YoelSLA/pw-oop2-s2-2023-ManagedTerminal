package routing;

import java.util.Comparator;
import java.util.List;

import maritimeCircuit.MaritimeCircuit;
import terminal.ManagedTerminal;
import terminal.Terminal;

public class FewerIntermediateTerminals extends Routing {

	/**
	 * Encuentra y devuelve el circuito mar�timo que tiene menos terminales
	 * intermedias entre un origen y un destino dados.
	 *
	 * @param origin           Terminal de origen gestionado.
	 * @param destiny          Terminal de destino.
	 * @param maritimeCircuits Lista de circuitos mar�timos disponibles.
	 * @return El circuito mar�timo con la menor de termminales intermedias entre el
	 *         origen y el destino.
	 * @throws RuntimeException Si hay problemas con la validaci�n de los circuitos
	 *                          mar�timos o la terminal de destino.
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
	 * Calcula la distancia entre dos terminales en un circuito mar�timo.
	 *
	 * @param origin          Terminal de origen gestionado.
	 * @param destiny         Terminal de destino.
	 * @param maritimeCircuit Circuito mar�timo en el que se calcula la distancia.
	 * @return La distancia entre las terminales en el circuito mar�timo.
	 */
	protected Double calculateRouting(ManagedTerminal origin, Terminal destiny, MaritimeCircuit maritimeCircuit) {
		return (double) Math.abs(maritimeCircuit.getPositionOf(origin) - maritimeCircuit.getPositionOf(destiny));
	}

}
