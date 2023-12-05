package phase;

import ship.Ship;
import terminal.ManagedTerminal;

public final class Inbound implements Phase {

	@Override
	public Phase updatePhase(Ship ship, ManagedTerminal managedTerminal) {
		this.indicarBuqueDarPreaviso(ship);
		return new Arrived();
	}
	
	private void indicarBuqueDarPreaviso(Ship ship) {
		ship.darPreaviso();
	}
}
