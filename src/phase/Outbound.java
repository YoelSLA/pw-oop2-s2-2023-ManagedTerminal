package phase;

import ship.Ship;

public class Outbound extends Phase {

	@Override
	public Phase nextPhase() {
		return new Inbound();
	}
	
	/**
	 * @author alejandrabesel
	 * Delega la responsabilidad al buque de notificar sobre su inminente arribo a la terminal
	 */
	@Override
	protected void communicateWithTerminal(Ship ship) {
		ship.notifyInminentArrival();
	}
	/**
	 * @author alejandrabesel
	 * Devolvera true solamente si la distancia entre la terminal y el buque es menor a 50 kilometros
	 */
	@Override
	protected boolean canItGoToTheNextPhase(Ship ship) {
		return ship.calculateDistanceToTerminal() < 50;
	}
}

