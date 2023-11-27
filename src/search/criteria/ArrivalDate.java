package search.criteria;

import java.time.LocalDateTime;
import java.util.List;

import terminal.Terminal;
import trip.Trip;

public class ArrivalDate implements Criteria {

	private LocalDateTime arrivalDate;
	private Terminal destiny;

	public ArrivalDate(LocalDateTime arrivalDate, Terminal destiny) {
		this.arrivalDate = arrivalDate;
		this.destiny = destiny;
	}

	@Override
	public List<Trip> filterTrips(List<Trip> trips) {
		return trips.stream()
				.filter(t -> t.hasADestinyTerminal(destiny) && t.dateArrivedToDestiny(destiny).equals(arrivalDate))
				.toList();

	}

	public LocalDateTime getArrivalDate() {
		return arrivalDate;
	}

	public Terminal getDestiny() {
		return destiny;
	}

}