package shippingLine;

import java.util.ArrayList;
import java.util.List;

import maritimeCircuit.MaritimeCircuit;
import ship.Ship;
import terminal.Terminal;
import trip.Trip;

public class ShippingLine {

	private String cuit;
	private String name;
	private List<MaritimeCircuit> maritimeCircuits;
	private List<Ship> ships;
	private List<Trip> trips;

	public ShippingLine(String cuit, String name) {
		this.cuit = cuit;
		this.name = name;
		this.maritimeCircuits = new ArrayList<MaritimeCircuit>();
		this.ships = new ArrayList<Ship>();
		this.trips = new ArrayList<Trip>();
	}

	public String getCuit() {
		return cuit;
	}

	public String getName() {
		return name;
	}

	public List<MaritimeCircuit> getMaritimeCircuits() {
		return maritimeCircuits;
	}

	public List<Ship> getShips() {
		return ships;
	}

	public List<Trip> getTrips() {
		return trips;
	}

	public void registerMaritimeCircuit(MaritimeCircuit maritimeCircuit) {
		maritimeCircuits.add(maritimeCircuit);
	}

	public void registerShip(Ship ship) {
		ships.add(ship);
	}

	public void registerTrip(Trip trip) throws Exception {
		if (!maritimeCircuits.contains(trip.getMaritimeCircuit())) {
			throw new RuntimeException("The maritime circuit is not registered in the shipping line.");
		}
		if (!ships.contains(trip.getShip())) {
			throw new RuntimeException("The ship is not registered in the shipping line.");
		}
		trips.add(trip);
	}

	public List<Ship> shipsInTrip() {
		return ships.stream().filter(s -> !s.getIsOnTrip()).toList();
	}

	public Integer timeItTakesToGetTo(Terminal origin, Terminal destiny) {
		System.out.println(maritimeCircuits.stream().noneMatch(m -> m.hasATerminal(origin)));
		if (maritimeCircuits.stream().noneMatch(m -> m.hasATerminal(origin))) {
			throw new RuntimeException("Terminal origin not found.");
		}
		System.out.println(destiny);
		if (maritimeCircuits.stream().noneMatch(m -> m.hasATerminal(destiny))) {
			throw new RuntimeException("There destiny not found.");
		}
		return maritimeCircuits.stream().mapToInt(m -> m.calculateTotalHoursBetweenTerminals(origin, destiny)).min()
				.orElse(0);
	}
}
