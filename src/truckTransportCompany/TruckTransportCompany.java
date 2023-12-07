
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

	public void registerDriver(Driver driver) {
		drivers.add(driver);
	}

	public void registerTruck(Truck truck) {
		trucks.add(truck);
	}

}