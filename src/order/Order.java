package order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import bill.Bill;
import driver.Driver;
import load.Load;
import service.Service;
import service.Washed;
import trip.Trip;
import truck.Truck;

public abstract class Order {

	private Bill bill;
	private LocalDateTime dateTruck;
	private Driver driver;
	private Load load;
	private static Integer number;
	private List<Service> services;
	private Trip trip;
	private Truck truck;

	public Order(Driver driver, Load load, Trip trip, Truck truck) {
		this.bill = new Bill(this);
		this.driver = driver;
		Order.number = number++;
		this.load = load;
		this.services = new ArrayList<Service>(List.of(new Washed(100.0)));
		this.truck = truck;
		this.trip = trip;
	}

	public Bill getBill() {
		return bill;
	}

	public LocalDateTime getDateTruck() {
		return dateTruck;
	}

	public Driver getDriver() {
		return driver;
	}

	public Load getLoad() {
		return load;
	}

	public List<Service> getServices() {
		return services;
	}

	public Trip getTrip() {
		return trip;
	}

	public Truck getTruck() {
		return truck;
	}

	public static Integer number() {
		return number;
	}

	public void setDateTruck(LocalDateTime dateTruck) {
		this.dateTruck = dateTruck;
	}

}
