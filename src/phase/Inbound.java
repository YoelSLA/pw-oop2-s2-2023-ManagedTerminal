package phase;

import ship.Ship;

public class Inbound extends Phase {

	@Override
	public Phase nextPhase() {
		return new Arrived();
	}
	
	/**
	 * @author alejandrabesel
	 * Delega la responsabilidad al buque de notificar sobre su arribo a la terminal
	 */
	@Override
	protected void communicateWithTerminal(Ship ship) {
		ship.notifyArrival();
	}
	
	/**
	 * @author alejandrabesel
	 * Devolvera true solamente si la distancia entre la terminal y el buque es igual a 0 kilometros, es decir, '
	 * si el buque se encuentra en la terminal
	 */
	@Override
	protected boolean canItGoToTheNextPhase(Ship ship) {
		return ship.calculateDistanceToTerminal() == 0;
	}
}

