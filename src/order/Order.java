package order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import client.Client;
import driver.Driver;
import load.Load;
import service.Service;
import terminal.Terminal;
import trip.Trip;
import truck.Truck;

public abstract class Order {
	
	private Client client;
	private Trip trip;
	private Load load;
	private Terminal origin;
	private Terminal destiny;
	private Driver driver;
	private Truck truck;
	private List<Service> services;
	
	
	public Order(Client client, Trip trip, Load load, Terminal origin, Terminal destiny, Driver driver, Truck truck) {
		this.client = client;
		this.trip = trip;
		this.load = load;
		this.origin = origin;
		this.destiny = destiny;
		this.driver = driver;
		this.truck = truck;
		this.services = new ArrayList<Service>();
	}

	public Client getClient() {
		return client;
	}
	
	public Trip getTrip() {
		return trip;
	}
	
	public Load getLoad() {
		return load;
	}
	
	public Terminal getOrigin() {
		return origin;
	}

	public Terminal getDestiny() {
		return destiny;
	}

	public Driver getDriver() {
		return driver;
	}

	public Truck getTruck() {
		return truck;
	}

	public List<Service> getServices() {
		return services;
	}

	public LocalDateTime arrivalDate() {
		return getTrip().calculateEstimatedArrivalDateToTerminal(getOrigin());
	}
	
	public LocalDateTime departureDate() {
		return getTrip().calculateEstimatedArrivalDateToTerminal(getDestiny());
	}
	
	public Double priceOfServices() {
		return services.stream().mapToDouble(s -> s.getPrice()).sum();
	}
	
	public void registerService(Service service) {
		services.add(service);
	}
	
	public abstract Double travelCost();

}
