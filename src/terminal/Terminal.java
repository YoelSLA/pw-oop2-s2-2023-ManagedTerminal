package terminal;

import position.Position;
import ship.Ship;

public interface Terminal {

	Position getPosition();

	String getName();

	void notifyShipArrival(Ship ship);

	void notifyShipDeparture(Ship ship);

	void notifyShipInminentArrival(Ship ship);

}
