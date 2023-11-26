package routing;

import java.util.List;
import java.util.Optional;

import maritimeCircuit.MaritimeCircuit;
import stretch.Stretch;
import terminal.ManagedTerminal;
import terminal.Terminal;

public class FewerIntermediateTerminals implements Routing {

	@Override
	public MaritimeCircuit bestCircuitBetween(ManagedTerminal origin, Terminal destiny,
			List<MaritimeCircuit> maritimeCircuits) {

		Optional<MaritimeCircuit> circuitWithDestiny = maritimeCircuits.stream()
				.filter(circuit -> containsADestinyTerminal(destiny, circuit))
				.min((circuit1, circuit2) -> Integer.compare(
						calculateIntermediateTerminalCount(origin, destiny, circuit1),
						calculateIntermediateTerminalCount(origin, destiny, circuit2)));

		if (circuitWithDestiny.isPresent()) {
			return circuitWithDestiny.get();
		} else {
			throw new RuntimeException("The destiny terminal is not located in the Maritime Circuit.");
		}
	}

	private Integer calculateIntermediateTerminalCount(ManagedTerminal origin, Terminal destiny,
			MaritimeCircuit maritimeCircuit) {
		final Integer originPosition = getPositionOfOriginInCircuit(origin, maritimeCircuit);
		final Integer destinyPosition = getPositionOfDestinyInCircuit(destiny, maritimeCircuit);

		return Math.abs(originPosition - destinyPosition);
	}

	private boolean containsADestinyTerminal(Terminal destiny, MaritimeCircuit maritimeCircuit) {
		return maritimeCircuit.getStretchs().stream().anyMatch(s -> s.getDestiny().equals(destiny));
	}

	private Integer getPositionOfDestinyInCircuit(Terminal destiny, MaritimeCircuit maritimeCircuit) {
		Optional<Stretch> stretch = maritimeCircuit.getStretchs().stream().filter(s -> s.getOrigin().equals(destiny))
				.findFirst();
		return stretch.map(st -> maritimeCircuit.getStretchs().indexOf(st)).orElse(-1);
	}

	private Integer getPositionOfOriginInCircuit(Terminal origin, MaritimeCircuit maritimeCircuit) {
		Optional<Stretch> stretch = maritimeCircuit.getStretchs().stream().filter(s -> s.getOrigin().equals(origin))
				.findFirst();
		return stretch.map(st -> maritimeCircuit.getStretchs().indexOf(st)).orElse(-1);
	}

}
