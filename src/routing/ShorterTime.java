package routing;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import maritimeCircuit.MaritimeCircuit;
import stretch.Stretch;
import terminal.ManagedTerminal;
import terminal.Terminal;

public class ShorterTime implements Routing {

	@Override
	public MaritimeCircuit bestCircuitBetween(ManagedTerminal origin, Terminal destiny,
			List<MaritimeCircuit> maritimeCircuits) {

		validateMaritimeCircuits(maritimeCircuits);
		validateTerminalDestinyIn(destiny, maritimeCircuits);
		return maritimeCircuits.stream()
				.min(Comparator.comparingDouble(circuit -> calculateTimeBetween(origin, destiny, circuit)))
				.orElse(maritimeCircuits.get(0));
	}

	private void validateMaritimeCircuits(List<MaritimeCircuit> maritimeCircuits) {
		if (maritimeCircuits.isEmpty()) {
			throw new RuntimeException("The maritime cirtuis must not be empty.");
		}

	}

	private void validateTerminalDestinyIn(Terminal destiny, List<MaritimeCircuit> maritimeCircuits) {
		if (maritimeCircuits.stream().noneMatch(
				circuit -> circuit.getStretchs().stream().anyMatch(stretch -> stretch.getDestiny().equals(destiny)))) {
			throw new RuntimeException("The destiny must be in the maritime circuits.");
		}

	}

	private Double calculateTimeBetween(ManagedTerminal origin, Terminal destiny, MaritimeCircuit maritimeCircuit) {
		final Integer originPosition = getPositionOfOriginInCircuit(origin, maritimeCircuit);
		final Integer destinyPosition = getPositionOfDestinyInCircuit(destiny, maritimeCircuit);

		return maritimeCircuit.getStretchs().subList(originPosition, destinyPosition).stream()
				.mapToDouble(s -> s.getTime().toSeconds()).sum();

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
