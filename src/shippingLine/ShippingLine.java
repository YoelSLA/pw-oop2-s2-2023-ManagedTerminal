package shippingLine;

import java.util.ArrayList;
import java.util.List;

import maritimeCircuit.MaritimeCircuit;
import ship.Ship;
import terminal.Terminal;
import trip.Trip;

public class ShippingLine {

	private String cuit;
	private List<MaritimeCircuit> maritimeCircuits;
	private String name;
	private List<Ship> ships;
	private List<Trip> trips;

	public ShippingLine(String cuit, String name) {
		this.cuit = cuit;
		this.maritimeCircuits = new ArrayList<MaritimeCircuit>();
		this.name = name;
		this.ships = new ArrayList<Ship>();
		this.trips = new ArrayList<Trip>();
	}

	public String getCuit() {
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

	public List<Ship> getShipsInTrip() {
		return ships.stream().filter(s -> !s.getIsOnTrip()).toList();
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

	public void registerToTerminal(Terminal terminal) {
		System.out.println("CIRCUITOS DE BUENOS AIRES | " + maritimeCircuitsForTerminal(terminal));

		// Obtén los circuitos marítimos relevantes para esta terminal
		List<MaritimeCircuit> relevantCircuits = maritimeCircuitsForTerminal(terminal);

		// Establece los circuitos marítimos relevantes en la compañía naviera
		setMaritimeCircuits(relevantCircuits);

		System.out.println("CIRCUITOS | " + maritimeCircuits);
	}

	public void registerTrip(Trip trip) throws Exception {
		validateMaritimeCircuitRegistration(trip);
		validateShipRegistration(trip);
		trips.add(trip);
	}

	public void setMaritimeCircuits(List<MaritimeCircuit> maritimeCircuits) {
		this.maritimeCircuits = maritimeCircuits;
	}

	private void validateMaritimeCircuitRegistration(Trip trip) throws Exception {
		if (!maritimeCircuits.contains(trip.getMaritimeCircuit())) {
			throw new RuntimeException("The maritime circuit is not registered in the shipping line.");
		}
	}

	private void validateShipRegistration(Trip trip) throws Exception {
		if (!ships.contains(trip.getShip())) {
			throw new RuntimeException("The ship is not registered in the shipping line.");
		}
	}

	public List<MaritimeCircuit> maritimeCircuitsForTerminal(Terminal terminal) {
		return maritimeCircuits.stream().filter(m -> m.hasATerminal(terminal)).toList();
	}

}
