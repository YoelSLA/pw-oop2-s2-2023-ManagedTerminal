package order;

import client.Shipper;
import driver.Driver;
import load.Load;
import terminal.Terminal;
import trip.Trip;
import truck.Truck;

public class ExportOrder extends Order {

	public ExportOrder(Shipper shipper, Trip trip, Load load, Terminal destiny, Driver driver, Truck truck) {
		super(shipper, trip, load, trip.getOriginTerminal(), destiny, driver, truck);
	}
	
	@Override
	public Double travelCost() {
		return 0.0;
	}

}
