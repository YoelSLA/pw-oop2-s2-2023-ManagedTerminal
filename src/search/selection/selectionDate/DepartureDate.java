package search.selection.selectionDate;

import java.time.LocalDate;
import java.util.List;

import search.criteria.Criteria;
import terminal.ManagedTerminal;
import trip.Trip;

public class DepartureDate extends SelectionDate {

	public DepartureDate(Criteria criteria, LocalDate departureDate, ManagedTerminal origin) {
		super(criteria, departureDate, origin);
	}

	/**
	 * Filtra una lista de viajes basándose en la fecha de llegada y el criterio
	 * establecido.
	 *
	 * @param trips Lista de viajes a filtrar.
	 * @return Lista de viajes filtrados según la fecha de llegada y el criterio.
	 */
	@Override
	public List<Trip> filterTrips(List<Trip> trips) {
		return trips.stream().filter(trip -> {
			try {
				return searchByCriteriaTo(calculateArrivalDate(trip));
			} catch (Exception e) {
			}
			return false;
		}).toList();
	}

}
