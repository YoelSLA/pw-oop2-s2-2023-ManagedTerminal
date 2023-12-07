package phase;

import ship.Ship;

public class Departing extends Phase {

	@Override
	public Phase nextPhase() {
		return new Outbound();
	}
	
	/**
	 * @author alejandrabesel
	 * Delega la responsabilidad al buque de notificar sobre su partida a la proxima terminal y de setear cual sera la proxima terminal
	 */
	@Override
	protected void communicateWithTerminal(Ship ship) {
		ship.notifyDeparture();
		ship.setNextTerminal();
	}
	
	/**
	 * @author alejandrabesel
	 * Devolvera true solamente si la distancia entre la terminal y el buque es mayor a 1 kilometro
	 */

	@Override
	protected boolean canItGoToTheNextPhase(Ship ship) {
		return ship.calculateDistanceToTerminal() > 1;
	}
}

