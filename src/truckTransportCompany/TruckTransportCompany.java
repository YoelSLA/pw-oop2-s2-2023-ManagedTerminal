package truckTransportCompany;

import java.util.ArrayList;
import java.util.List;

import driver.Driver;
import truck.Truck;

public class TruckTransportCompany {

	private Integer cuit;
	private List<Driver> drivers;
	private String name;
	private List<Truck> trucks;

	public TruckTransportCompany(Integer cuit, String name) {
		this.cuit = cuit;
		this.drivers = new ArrayList<Driver>();
		this.name = name;
		this.trucks = new ArrayList<Truck>();
	}

	public Integer getCuit() {
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
