package search.selection;

import java.util.List;

import terminal.Terminal;
import trip.Trip;

public class DestinationTerminal extends Selection {

	public DestinationTerminal(Terminal destiny) {
		super(destiny);
	}

	/**
	 * Filtra una lista de viajes basándose en el terminal especificado.
	 *
	 * @param trips Lista de viajes a filtrar.
	 * @return Lista de viajes filtrados que tienen el terminal especificado.
	 */
	@Override
	public List<Trip> filterTrips(List<Trip> trips) {
		return trips.stream().filter(trip -> trip.hasTerminal(getTerminal())).toList();
	}
}
