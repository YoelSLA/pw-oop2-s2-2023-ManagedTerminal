package maritimeCircuit;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import stretch.Stretch;
import terminal.Terminal;

public class MaritimeCircuit {

	private List<Stretch> stretches;

	public MaritimeCircuit(List<Stretch> stretches) {
		this.stretches = stretches;
	}

	public Integer calculateTotalHoursBetweenTerminals(Terminal origin, Terminal destiny) {
		final Long totalNanos = stretches.subList(getPositionOf(origin), getPositionOf(destiny)).stream()
				.mapToLong(s -> s.getTime().toNanos()).sum();
		return calculateHoursRounded(Duration.ofNanos(totalNanos));
	}

	public Integer getPositionOf(Terminal terminal) {
		Optional<Stretch> stretch = stretches.stream().filter(s -> s.getOrigin().equals(terminal)).findFirst();
		return stretch.map(s -> stretches.indexOf(s)).orElse(-1);
	}

	public Double getPrice() {
		return stretches.stream().mapToDouble(Stretch::getPrice).sum();
	}

	public Integer getTime() {
		return stretches.stream().mapToInt(s -> calculateHoursRounded(s.getTime())).sum();
	}

	public List<Stretch> getStretches() {
		return stretches;
	}

	public Boolean hasATerminal(Terminal destiny) {
		return originTerminals().stream().anyMatch(t -> t.equals(destiny));
	}

	public Terminal originTerminal() {
		return stretches.get(0).getOrigin();
	}

	public List<Terminal> originTerminals() {
		List<Terminal> origins = new ArrayList<>(stretches.stream().map(Stretch::getOrigin).toList());
		origins.add(stretches.get(0).getOrigin());
		return origins;
	}

	private Integer calculateHoursRounded(Duration time) {
		return (int) Math.round(time.toNanos() / (double) Duration.ofHours(1).toNanos());
	}

}
