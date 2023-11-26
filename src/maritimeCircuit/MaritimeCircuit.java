package maritimeCircuit;

import java.util.List;

import stretch.Stretch;
import terminal.Terminal;

public class MaritimeCircuit {

	private List<Stretch> stretchs;

	public MaritimeCircuit(List<Stretch> stretchs) {
		this.stretchs = stretchs;
	}

	public Double getPrice() {
		return getStretchs().stream().mapToDouble(Stretch::getPrice).sum();
	}

	public List<Stretch> getStretchs() {
		return stretchs;
	}

	private Integer originNumberTerminal(Terminal origin) {
		return getStretchs()
				.indexOf(getStretchs().stream().filter(s -> s.getOrigin().equals(origin)).findFirst().get());
	}

	private Integer destinyNumberTerminal(Terminal destiny) {
		return getStretchs()
				.indexOf(getStretchs().stream().filter(s -> s.getOrigin().equals(destiny)).findFirst().get());
	}

	public boolean areTheTerminalsThere(Terminal origin, Terminal destiny) {
		return isTheOriginTerminal(origin) && isTheDestinyTerminal(destiny)
				&& isTheOriginTerminalBeforeDestinationTerminal(origin, destiny);
	}

	public boolean isTheDestinyTerminal(Terminal destiny) {
		return getStretchs().stream().anyMatch(s -> s.getDestiny().equals(destiny));
	}

	public boolean isTheOriginTerminal(Terminal origin) {
		return getStretchs().stream().anyMatch(s -> s.getOrigin().equals(origin));
	}

	public boolean isTheOriginTerminalBeforeDestinationTerminal(Terminal origin, Terminal destiny) {
		return originNumberTerminal(origin) < destinyNumberTerminal(destiny);
	}

}
