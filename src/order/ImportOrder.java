package order;

import client.Consignee;
import driver.Driver;
import load.Load;
import terminal.Terminal;
import trip.Trip;
import truck.Truck;

public class ImportOrder extends Order {

	public ImportOrder(Load load, Trip trip, Terminal origin, Terminal destiny, Consignee consignee, Driver driver,
			Truck truck) {
		super(load, trip, origin, destiny, consignee, driver, truck);
	}

}
