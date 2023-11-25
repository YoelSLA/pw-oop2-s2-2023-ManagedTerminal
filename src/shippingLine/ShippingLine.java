package shippingLine;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

	public void registerMaritimeCircuit(MaritimeCircuit maritimeCircuit) {
		maritimeCircuits.add(maritimeCircuit);
	}

	public void registerShip(Ship ship) {
		ships.add(ship);
	}

	public void registerTrip(Trip trip) {
		validateShipRegistrationIn(trip);
		validateMaritimeCircuitRegistrationIn(trip);
		trips.add(trip);
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

	public List<Trip> getTrips() {
		return trips;
	}

	public List<MaritimeCircuit> maritimeCircuitsWhereTheTerminal(Terminal terminal) {
		return maritimeCircuits.stream().filter(m -> m.itHasASectionWhereItIs(terminal)).collect(Collectors.toList());
	}

	/**
	 * Metodo que retorna la lista de viajes que comienzan en la fecha dada.
	 * 
	 * @author Gabriela Fascetta
	 */
	public List<Trip> getTripsThatStartOn(LocalDate date) {
		return trips.stream().filter(t -> t.getStartDate().isEqual(date)).collect(Collectors.toList());
	}

	/**
	 * Metodo que retorna la lista de circuitos maritimos a los que pertenece un
	 * viaje que comienzan en la fecha dada.
	 * 
	 * @author Gabriela Fascetta
	 */
	public List<MaritimeCircuit> getCircuitsWithTripsThatStartOn(LocalDate date) {

		List<MaritimeCircuit> circuits = new ArrayList<>();

		for (Trip t : getTripsThatStartOn(date)) {
			circuits.add(t.getMaritimeCircuit());
		}

		return circuits;
	}

	private void validateMaritimeCircuitRegistrationIn(Trip trip) {
		if (!getMaritimeCircuits().contains(trip.getMaritimeCircuit())) {
			throw new RuntimeException("The maritime circuit is not registered in the shipping line.");
		}
	}

	private void validateShipRegistrationIn(Trip trip) {
		if (!getShips().contains(trip.getShip())) {
			throw new RuntimeException("The ship is not registered in the shipping line.");
		}
	}
}
