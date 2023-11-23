package order;

import client.Shipper;
import driver.Driver;
import load.Load;
import trip.Trip;
import truck.Truck;

public class ExportOrder extends Order {

	private Shipper shipper;

	public ExportOrder(Driver driver, Load load, Shipper shipper, Trip trip, Truck truck) {
		super(driver, load, trip, truck);
		this.shipper = shipper;

	}

	public Shipper getShipper() {
		return shipper;
	}

}
