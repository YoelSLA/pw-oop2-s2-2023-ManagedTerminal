package search.selection.selectionDate;

import java.time.LocalDate;
import java.util.List;

import search.criteria.Criteria;
import terminal.Terminal;
import trip.Trip;

public class ArrivalDate extends SelectionDate {

	public ArrivalDate(Criteria criteria, LocalDate arrivalDate, Terminal destiny) {
		super(criteria, arrivalDate, destiny);
	}

	/**
	 * Filtra los viajes según la fecha de llegada y el terminal de destino.
	 *
	 * @param trips Lista de viajes a filtrar.
	 * @return Lista de viajes filtrados según la fecha de llegada y el terminal de
	 *         destino.
	 */
	@Override
	public List<Trip> filterTrips(List<Trip> trips) {
		return trips.stream()
				.filter(trip -> trip.hasTerminal(getTerminal()) && searchByCriteriaTo(calculateArrivalDate(trip)))
				.toList();

	}
}
