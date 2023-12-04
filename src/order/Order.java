package order;

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

	private Load load;
	private Trip trip;
	private Terminal origin;
	private Terminal destiny;
	private Client client;
	private Driver driver;
	private Truck truck;

	private Bill bill;
	private List<Service> services;
	private Turn turn;

	public Order(Load load, Trip trip, Terminal origin, Terminal destiny, Client client, Driver driver, Truck truck) {
		this.load = load;
		this.trip = trip;
		this.origin = origin;
		this.destiny = destiny;
		this.client = client;
		this.driver = driver;
		this.truck = truck;
		this.bill = new Bill(this);
		this.services = new ArrayList<Service>();
		this.turn = new Turn(driver, truck, null); // TODO: REVISAR
	}

	/*
	 * public Order(List<Service> servicesList, Load load, Trip trip) {
	 * this.services = servicesList; this.load = load; this.trip = trip; }
	 */

	public Bill getBill() {
		return bill;
	}

	public Client getClient() {
		return client;
	}

	public Terminal getDestiny() {
		return destiny;
	}

	public Terminal getOrigin() {
		return origin;
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

	public Driver getDriver() {
		return driver;
	}

	public Turn getTurn() {
		return turn;
	}

	public void setTurn(Turn turn) {
		this.turn = turn;
	}

	public void registerService(Service service) {
		services.add(service);

	}
}
