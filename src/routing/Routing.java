package routing;

import java.util.List;

import maritimeCircuit.MaritimeCircuit;
import terminal.ManagedTerminal;
import terminal.Terminal;

public abstract class Routing {

	public abstract MaritimeCircuit bestCircuitBetween(ManagedTerminal origin, Terminal destiny,
			List<MaritimeCircuit> maritimeCircuits) throws Exception;

	protected abstract Double calculateRouting(ManagedTerminal origin, Terminal destiny,
			MaritimeCircuit maritimeCircuit);

	protected void validateMaritimeCircuits(List<MaritimeCircuit> maritimeCircuits) {
		if (maritimeCircuits.isEmpty()) {
			throw new RuntimeException("The maritime cirtuit must not be empty.");
		}

	}

	protected void validateTerminalDestinyIn(Terminal destiny, List<MaritimeCircuit> maritimeCircuits) {
		if (maritimeCircuits.stream().noneMatch(
				circuit -> circuit.getStretches().stream().anyMatch(stretch -> stretch.getDestiny().equals(destiny)))) {
			throw new RuntimeException("The destiny must be in the maritime circuits.");
		}
	}

}
