package terminal;

import geographicalPosition.GeographicalPosition;
import ship.Ship;

public interface Terminal {

	String getName();

	GeographicalPosition getPosition();
	
	void updateShipInminentArrival(Ship ship);
	
	void updateShipArrival(Ship ship);
	
}
