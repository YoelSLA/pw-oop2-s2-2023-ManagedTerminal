package order;

import client.Client;
import client.Consignee;
import terminal.Terminal;
import load.Load;
import trip.Trip;
import truck.Truck;
import driver.Driver;


public class ImportOrder extends Order {
	
	private Consignee consignee;

	public ImportOrder(Load load, Trip trip, Terminal origin, Terminal destiny, 
						Client consignee, Driver driver,	Truck truck) {
		super(load, trip, origin, destiny, consignee, driver, truck);
		this.consignee = (Consignee) consignee;
	}
	
	public Consignee getConsignee() {
		return consignee;
	}	
	
}
