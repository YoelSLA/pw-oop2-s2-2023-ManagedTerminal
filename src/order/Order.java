package order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import bill.Bill;
import client.Client;
import driver.Driver;
import load.Load;
import service.Service;
import terminal.Terminal;
import trip.Trip;
import truck.Truck;
import turn.Turn;

public abstract class Order {

	private Bill bill;
	private Client client;
	private Terminal destiny;
	private Load load;
	private Terminal origin;
	private Trip trip;
	private Turn turn;

	private List<Service> services;

	public Order(Load load, Trip trip, Terminal origin, Terminal destiny, Client client, Driver driver, Truck truck) {
		this.trip = trip;
		this.load = load;
		this.origin = origin;
		this.destiny = destiny;
		this.client = client;
		this.bill = new Bill(this);
		this.services = new ArrayList<Service>();
		this.turn = new Turn(driver, truck);
	}

	public Bill getBill() {
		return bill;
	}

	public Client getClient() {
		return client;
	}

	public Terminal getDestiny() {
		return destiny;
	}

	public Driver getDriver() {
		return turn.getDriver();
	}

	public Load getLoad() {
		return load;
	}

	public Terminal getOrigin() {
		return origin;
	}

	public Trip getTrip() {
		return trip;
	}

	public Truck getTruck() {
		return turn.getTruck();
	}

	public Turn getTurn() {
		return turn;
	}

	public List<Service> getServices() {
		return services;
	}

	public void setTurn(Turn turn) {
		this.turn = turn;
	}

	public LocalDateTime arrivalDate() {
		return trip.calculateEstimatedArrivalDateToTerminal(destiny);
	}

	public Double getPriceOfServices() {
		return services.stream().mapToDouble(s -> s.getPrice()).sum();
	}

	public Double getPriceOfTrip() {
		return 0.0;
	}

	public void registerService(Service service) {
		services.add(service);
	}

}
