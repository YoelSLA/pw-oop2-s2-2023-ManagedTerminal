package routing;

import java.util.List;
import java.util.Optional;

import maritimeCircuit.MaritimeCircuit;
import terminal.ManagedTerminal;
import terminal.Terminal;

public class FewerIntermediateTerminals extends Routing {

	@Override
	public MaritimeCircuit bestCircuitBetween(ManagedTerminal origin, Terminal destiny,
			List<MaritimeCircuit> maritimeCircuits) {

		validateMaritimeCircuits(maritimeCircuits);
		validateTerminalDestinyIn(destiny, maritimeCircuits);
		Optional<MaritimeCircuit> circuitWithDestiny = maritimeCircuits.stream()
				.min((circuit1, circuit2) -> Integer.compare(
						calculateIntermediateTerminalBetween(origin, destiny, circuit1),
						calculateIntermediateTerminalBetween(origin, destiny, circuit2)));
		return circuitWithDestiny.get();

	}

	private Integer calculateIntermediateTerminalBetween(ManagedTerminal origin, Terminal destiny,
			MaritimeCircuit maritimeCircuit) {
		final Integer originPosition = getPositionOfOriginInCircuit(origin, maritimeCircuit);
		final Integer destinyPosition = getPositionOfDestinyInCircuit(destiny, maritimeCircuit);

		return Math.abs(originPosition - destinyPosition);
	}

}
