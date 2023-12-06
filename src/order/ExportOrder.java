package order;

import client.Shipper;
import driver.Driver;
import load.Load;
import terminal.Terminal;
import trip.Trip;
import truck.Truck;

public class ExportOrder extends Order {

	public ExportOrder(Load load, Trip trip, Terminal destiny, Shipper shipper, Driver driver, Truck truck) {
		super(load, trip, trip.getOriginTerminal(), destiny, shipper, driver, truck);
	}

}
