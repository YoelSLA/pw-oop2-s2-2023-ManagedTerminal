
package truckTransportCompany;

import java.util.ArrayList;
import java.util.List;

import driver.Driver;
import truck.Truck;

public class TruckTransportCompany {

	private String cuit;
	private List<Driver> drivers;
	private String name;
	private List<Truck> trucks;

	public TruckTransportCompany(String cuit, String name) {
		this.cuit = cuit;
		this.drivers = new ArrayList<Driver>();
		this.name = name;
		this.trucks = new ArrayList<Truck>();
	}
	
	public void addDriver(Driver driver) {
		drivers.add(driver);
	}
	
	public void addTruck(Truck truck) {
		trucks.add(truck);
	}

	public String getCuit() {
		return cuit;
	}

	public List<Driver> getDrivers() {
		return drivers;
	}

	public String getName() {
		return name;
	}

	public List<Truck> getTrucks() {
		return trucks;
	}

}