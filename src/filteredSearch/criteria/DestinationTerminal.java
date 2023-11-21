package filteredSearch.criteria;

import java.util.List;
import java.util.stream.Collectors;

import maritimeCircuit.MaritimeCircuit;
import terminal.Terminal;

public class DestinationTerminal implements Criteria {
	
	private Terminal destinationTerminal;

	public DestinationTerminal(Terminal destinationTerminal) {
		this.destinationTerminal= destinationTerminal;
	}

	@Override
	public List<MaritimeCircuit> filterCircuits(List<MaritimeCircuit> maritimeCircuits) {
		return	maritimeCircuits.stream().filter(m -> m.itHasASectionWhereItIs(destinationTerminal)).collect(Collectors.toList());
	}
	
	public Terminal getDestinationTerminal() {
		return destinationTerminal;
	}
}
