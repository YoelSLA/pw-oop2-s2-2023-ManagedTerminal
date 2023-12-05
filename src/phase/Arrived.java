package phase;

import ship.Ship;
import terminal.ManagedTerminal;

public final class Arrived implements Phase {

	@Override
	public Phase updatePhase(Ship ship, ManagedTerminal managedTerminal) {
		if (ship.getPosition() == managedTerminal.getPosition()) return new Arrived();
		else return new Inbound();
	}
	
}
 