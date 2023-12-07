package order;

import client.Consignee;
import driver.Driver;
import load.Load;
import terminal.Terminal;
import trip.Trip;
import truck.Truck;

public class ImportOrder extends Order {

	public ImportOrder(Consignee consignee, Trip trip, Load load, Terminal origin, Terminal destiny, Driver driver,
			Truck truck) {
		super(consignee, trip, load, origin, destiny, driver, truck);
	}

	@Override
	public Double travelCost() {
		return getTrip().getMaritimeCircuit().getPriceBetween(getOrigin(), getDestiny());
	}

}
