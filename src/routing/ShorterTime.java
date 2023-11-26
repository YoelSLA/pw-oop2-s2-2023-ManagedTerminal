package routing;

import java.util.Comparator;
import java.util.List;

import maritimeCircuit.MaritimeCircuit;
import terminal.ManagedTerminal;
import terminal.Terminal;

public class ShorterTime extends Routing {

	@Override
	public MaritimeCircuit bestCircuitBetween(ManagedTerminal origin, Terminal destiny,
			List<MaritimeCircuit> maritimeCircuits) {

		validateMaritimeCircuits(maritimeCircuits);
		validateTerminalDestinyIn(destiny, maritimeCircuits);
		return maritimeCircuits.stream()
				.min(Comparator.comparingDouble(circuit -> calculateTimeBetween(origin, destiny, circuit)))
				.orElse(maritimeCircuits.get(0));
	}

	private Double calculateTimeBetween(ManagedTerminal origin, Terminal destiny, MaritimeCircuit maritimeCircuit) {
		final Integer originPosition = getPositionOfOriginInCircuit(origin, maritimeCircuit);
		final Integer destinyPosition = getPositionOfDestinyInCircuit(destiny, maritimeCircuit);

		return maritimeCircuit.getStretchs().subList(originPosition, destinyPosition).stream()
				.mapToDouble(s -> s.getTime().toSeconds()).sum();

	}
}
