package phase;

import ship.Ship;

public class Arrived extends Phase {

	@Override
	public Phase nextPhase() {
		return new Working();
	}

	/**
	 * @author alejandrabesel
	 * Delega la responsabilidad al buque de notificar sobre su arribo a la terminal
	 */
	@Override
	protected void communicateWithTerminal(Ship ship) {
		ship.notifyArrival();
	}

}
