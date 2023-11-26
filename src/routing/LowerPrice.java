package routing;

import java.util.Comparator;
import java.util.List;

import maritimeCircuit.MaritimeCircuit;
import stretch.Stretch;
import terminal.ManagedTerminal;
import terminal.Terminal;

public class LowerPrice extends Routing {

	@Override
	public MaritimeCircuit bestCircuitBetween(ManagedTerminal origin, Terminal destiny,
			List<MaritimeCircuit> maritimeCircuits) {

		validateMaritimeCircuits(maritimeCircuits);
		validateTerminalDestinyIn(destiny, maritimeCircuits);
		return maritimeCircuits.stream()
				.filter(circuit -> calculateLowerPriceBetween(origin, destiny, circuit) < circuit.getPrice())
				.min(Comparator.comparingDouble(circuit -> calculateLowerPriceBetween(origin, destiny, circuit)))
				.orElse(maritimeCircuits.get(0));
	}

	private Double calculateLowerPriceBetween(ManagedTerminal origin, Terminal destiny,
			MaritimeCircuit maritimeCircuit) {
		final Integer originPosition = getPositionOfOriginInCircuit(origin, maritimeCircuit);
		final Integer destinyPosition = getPositionOfDestinyInCircuit(destiny, maritimeCircuit);

		return maritimeCircuit.getStretchs().subList(originPosition, destinyPosition).stream()
				.mapToDouble(Stretch::getPrice).sum();

	}

}