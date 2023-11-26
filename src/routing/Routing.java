package routing;

import java.util.List;
import java.util.Optional;

import maritimeCircuit.MaritimeCircuit;
import stretch.Stretch;
import terminal.ManagedTerminal;
import terminal.Terminal;

public abstract class Routing {

	public abstract MaritimeCircuit bestCircuitBetween(ManagedTerminal origin, Terminal destin,
			List<MaritimeCircuit> maritimeCircuits);

	protected Integer getPositionOfDestinyInCircuit(Terminal destiny, MaritimeCircuit maritimeCircuit) {
		Optional<Stretch> stretch = maritimeCircuit.getStretchs().stream().filter(s -> s.getOrigin().equals(destiny))
				.findFirst();
		return stretch.map(st -> maritimeCircuit.getStretchs().indexOf(st)).orElse(-1);
	}

	protected Integer getPositionOfOriginInCircuit(Terminal origin, MaritimeCircuit maritimeCircuit) {
		Optional<Stretch> stretch = maritimeCircuit.getStretchs().stream().filter(s -> s.getOrigin().equals(origin))
				.findFirst();
		return stretch.map(st -> maritimeCircuit.getStretchs().indexOf(st)).orElse(-1);
	}

	protected void validateMaritimeCircuits(List<MaritimeCircuit> maritimeCircuits) {
		if (maritimeCircuits.isEmpty()) {
			throw new RuntimeException("The maritime cirtuis must not be empty.");
		}

	}

	protected void validateTerminalDestinyIn(Terminal destiny, List<MaritimeCircuit> maritimeCircuits) {
		if (maritimeCircuits.stream().noneMatch(
				circuit -> circuit.getStretchs().stream().anyMatch(stretch -> stretch.getDestiny().equals(destiny)))) {
			throw new RuntimeException("The destiny must be in the maritime circuits.");
		}

	}

}
