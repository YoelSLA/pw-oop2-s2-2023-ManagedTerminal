package routing;

import java.util.Comparator;
import java.util.List;

import maritimeCircuit.MaritimeCircuit;
import stretch.Stretch;
import terminal.ManagedTerminal;
import terminal.Terminal;

public class ShorterTime extends Routing {

	/**
	 * Encuentra y devuelve el circuito mar�timo m�s corto entre un origen y un
	 * destino dados.
	 * 
	 * @param origin           Terminal de origen gestionada.
	 * @param destiny          Terminal de destino.
	 * @param maritimeCircuits Lista de circuitos mar�timos disponibles.
	 * @return El circuito mar�timo m�s corto entre el origen y el destino.
	 * @throws RuntimeException Si hay problemas con la validaci�n de los circuitos
	 *                          mar�timos o la terminal de destino.
	 */
	@Override
	public MaritimeCircuit bestCircuitBetween(ManagedTerminal origin, Terminal destiny,
			List<MaritimeCircuit> maritimeCircuits) throws Exception {

		validateMaritimeCircuits(maritimeCircuits);
		validateTerminalDestinyIn(destiny, maritimeCircuits);
		return maritimeCircuits.stream()
				.min(Comparator.comparingDouble(circuit -> calculateRouting(origin, destiny, circuit)))
				.orElseThrow(() -> new RuntimeException("No circuit found."));
	}

	/**
	 * Calcula la duraci�n total de la ruta en un circuito mar�timo entre dos
	 * terminales.
	 * 
	 * @param origin          Terminal de origen gestionado.
	 * @param destiny         Terminal de destino.
	 * @param maritimeCircuit Circuito mar�timo en el que se calcula la ruta.
	 * @return La duraci�n total de la ruta en el circuito mar�timo.
	 */
	protected Double calculateRouting(ManagedTerminal origin, Terminal destiny, MaritimeCircuit maritimeCircuit) {
		List<Stretch> stretches = maritimeCircuit.getStretches();
		int startPosition = maritimeCircuit.getPositionOf(origin);
		int endPosition = maritimeCircuit.getPositionOf(destiny);

		List<Stretch> relevantStretches = stretches.subList(startPosition, endPosition);
		return relevantStretches.stream().mapToDouble(s -> s.getTime().toSeconds()).sum();
	}

}
