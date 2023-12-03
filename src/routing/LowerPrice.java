package routing;

import java.util.Comparator;
import java.util.List;

import maritimeCircuit.MaritimeCircuit;
import stretch.Stretch;
import terminal.ManagedTerminal;
import terminal.Terminal;

public class LowerPrice extends Routing {

	/**
	 * Encuentra y devuelve el circuito mar�timo con el costo m�nimo entre un origen
	 * y un destino dados.
	 *
	 * @param origin           Terminal de origen gestionada.
	 * @param destiny          Terminal de destino.
	 * @param maritimeCircuits Lista de circuitos mar�timos disponibles.
	 * @return El circuito mar�timo con el costo m�nimo entre el origen y el
	 *         destino.
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
	 * Calcula el costo total de la ruta en un circuito mar�timo entre dos
	 * terminales.
	 *
	 * @param origin          Terminal de origen gestionado.
	 * @param destiny         Terminal de destino.
	 * @param maritimeCircuit Circuito mar�timo en el que se calcula la ruta.
	 * @return El costo total de la ruta en el circuito mar�timo.
	 */
	@Override
	protected Double calculateRouting(ManagedTerminal origin, Terminal destiny, MaritimeCircuit maritimeCircuit) {
		List<Stretch> relevantStretches = maritimeCircuit.getStretches().subList(maritimeCircuit.getPositionOf(origin),
				maritimeCircuit.getPositionOf(destiny));

		return relevantStretches.stream().mapToDouble(Stretch::getPrice).sum();
	}

}