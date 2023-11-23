package order;

import java.util.List;

import client.Consignee;
import service.Service;
import load.Load;
import trip.Trip;


public class ImportOrder extends Order {
	private Consignee consignee;

	public ImportOrder(List<Service> servicesList, Load load, Consignee consignee, Trip trip) {
		super(servicesList, load, trip);
		this.consignee = consignee;
	}
	
	public Consignee getConsignee() {
		return consignee;
	}	
	
}
