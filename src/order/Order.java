package order;


import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import bill.Bill;
import driver.Driver;
import load.Load;
import service.Service;
import truck.Truck;
import trip.Trip;

public abstract class Order {
	
	private Bill bill;
	private LocalDate dateTruck;
	private Driver driver;
	private Integer id;
	private Load load;
	private List<Service> services;
	private Truck truck;
	private Trip trip;
	
	public LocalDate getDateTruck() {
		return dateTruck;
	}

	public Driver getDriver() {
		return driver;
	}

	public Integer getId() {
		return id;
	}

	public Load getLoad() {
		return load;
	}

	public Truck getTruck() {
		return truck;
	}

	public Trip getTrip() {
		return trip;
	}

	public List<Service> getServices() {
		return services;
	}

	public Bill getBill() {
		return bill;
	}

	public void setDateTruck(LocalDate dateTruck) {
		this.dateTruck = dateTruck;
	}
	
	
	

}
