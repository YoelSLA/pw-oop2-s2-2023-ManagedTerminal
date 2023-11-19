package shippingCompany;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import maritimeCircuit.MaritimeCircuit;
import ship.Ship;
import terminal.Terminal;
import trip.Trip;

public class ShippingCompany {

	private Integer cuit;
	private List<MaritimeCircuit> maritimeCircuits;
	private String name;
	private List<Ship> ships;
	private List<Trip> trips;

	public ShippingCompany(Integer cuit, String name) {
		this.cuit = cuit;
		this.maritimeCircuits = new ArrayList<MaritimeCircuit>();
		this.name = name;
		this.ships = new ArrayList<Ship>();
		this.trips = new ArrayList<Trip>();
	}

	public void addMaritimeCircuit(MaritimeCircuit maritimeCircuit) {
		maritimeCircuits.add(maritimeCircuit);
	}

	public void addShip(Ship ship) {
		ships.add(ship);
	}

	public void addTrip(Trip trip) {
		trips.add(trip);
	}

	public Integer getCuit() {
		return cuit;
	}

	public List<MaritimeCircuit> getMaritimeCircuits() {
		return maritimeCircuits;
	}

	public String getName() {
		return name;
	}

	public List<Ship> getShips() {
		return ships;
	}

	public List<Trip> getTrips() {
		return trips;
	}

	public List<MaritimeCircuit> maritimeCircuitsWhereTheTerminal(Terminal terminal) {
		return maritimeCircuits.stream().filter(m -> m.itHasASectionWhereItIs(terminal)).collect(Collectors.toList());
	}

}
