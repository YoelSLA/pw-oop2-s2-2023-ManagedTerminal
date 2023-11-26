package routing;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import maritimeCircuit.MaritimeCircuit;
import stretch.Stretch;
import terminal.ManagedTerminal;
import terminal.Terminal;

public class LowerPrice implements Routing {

	@Override
	public MaritimeCircuit bestCircuitBetween(ManagedTerminal origin, Terminal destiny,
			List<MaritimeCircuit> maritimeCircuits) {
		if (maritimeCircuits.stream().noneMatch(
				circuit -> circuit.getStretchs().stream().anyMatch(stretch -> stretch.getOrigin().equals(destiny)))) {
			throw new RuntimeException("The destny must be in the maritime circuits");
		}

		return maritimeCircuits.stream()
				.filter(circuit -> menorPrecioEntre(origin, destiny, circuit) < circuit.getPrice())
				.min(Comparator.comparingDouble(circuit -> menorPrecioEntre(origin, destiny, circuit)))
				.orElse(maritimeCircuits.get(0));
	}

	private Double menorPrecioEntre(ManagedTerminal origin, Terminal destiny, MaritimeCircuit maritimeCircuit) {
		final Integer originPosition = getPositionOfOriginInCircuit(origin, maritimeCircuit);
		final Integer destinyPosition = getPositionOfDestinyInCircuit(destiny, maritimeCircuit);

		return maritimeCircuit.getStretchs().subList(originPosition, destinyPosition).stream()
				.mapToDouble(Stretch::getPrice).sum();

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