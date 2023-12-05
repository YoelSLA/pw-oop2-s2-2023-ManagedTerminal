package phase;

import ship.Ship;
import terminal.ManagedTerminal;

public final class Outbound implements Phase {
	
	@Override
	public Phase updatePhase(Ship ship, ManagedTerminal managedTerminal) {
		if (this.getDistanceBetween(ship, managedTerminal) <= 50) return new Inbound();
		else return new Outbound();
	}

	public double getDistanceBetween(Ship ship, ManagedTerminal managedTerminal) {
	
	        // Radio de la Tierra en kilómetros
	        final double EarthRadio = 6371.0;

	        double shipLatitude = Math.toRadians(ship.getPosition().getLatitude());
	        double shipLongitude = Math.toRadians(ship.getPosition().getLongitude());
	        double terminalLatitude = Math.toRadians(managedTerminal.getPosition().getLatitude());
	        double terminalLongitude = Math.toRadians(managedTerminal.getPosition().getLongitude());

	        double distanceLat = terminalLatitude - shipLatitude;
	        double distanceLon = terminalLongitude - shipLongitude;
	        // Math.sin() calcula el seno de un ángulo en radianes
	        double a = Math.sin(distanceLat / 2) * Math.sin(distanceLat / 2) +
	                Math.cos(shipLatitude) * Math.cos(terminalLatitude) *
	                        Math.sin(distanceLon / 2) * Math.sin(distanceLon / 2);

	        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

	        return EarthRadio * c;
	}
	
	

}

