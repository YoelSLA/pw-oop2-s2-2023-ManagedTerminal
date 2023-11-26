package routing;

import java.util.List;

import maritimeCircuit.MaritimeCircuit;
import terminal.ManagedTerminal;
import terminal.Terminal;

public interface Routing {

	public MaritimeCircuit bestCircuitBetween(ManagedTerminal origin, Terminal destin,
			List<MaritimeCircuit> maritimeCircuits);

}
