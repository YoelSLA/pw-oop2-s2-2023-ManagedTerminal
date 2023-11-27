package search.selection.selectionDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import maritimeCircuit.MaritimeCircuit;
import search.criteria.Criteria;
import stretch.Stretch;
import terminal.ManagedTerminal;
import terminal.Terminal;
import trip.Trip;

class DepartureDateTest {

	private LocalDateTime dateForSearch;
// -------------------------------------------------------------
	private ManagedTerminal buenosAires;
// -------------------------------------------------------------
	private MaritimeCircuit maritimeCircuitOne;
	private MaritimeCircuit maritimeCircuitTwo;
// -------------------------------------------------------------
	private Stretch buenosAiresSantiago;
	private Stretch santiagoQuito;
	private Stretch quitoLima;
	private Stretch limaCaracas;
	private Stretch caracasBuenosAires;
	private Stretch santiagoLima;
// -------------------------------------------------------------
	private Terminal santiago;
	private Terminal quito;
	private Terminal lima;
	private Terminal caracas;
// -------------------------------------------------------------
	private Trip tripOne;
	private Trip tripTwo;
// -------------------------------------------------------------
	private DepartureDate departureDate; // SUT

	@BeforeEach
	void setUp() {
		// MANAGED TERMINAL
		buenosAires = mock(ManagedTerminal.class);
		// -------------------------------------------------------------
		// MARITIME CIRCUIT
		maritimeCircuitOne = mock(MaritimeCircuit.class);
		when(maritimeCircuitOne.getStretchs()).thenReturn(
				Arrays.asList(buenosAiresSantiago, santiagoQuito, quitoLima, limaCaracas, caracasBuenosAires));
		when(maritimeCircuitOne.originTerminal()).thenReturn(buenosAires);

		maritimeCircuitTwo = mock(MaritimeCircuit.class);
		when(maritimeCircuitTwo.getStretchs())
				.thenReturn(Arrays.asList(buenosAiresSantiago, santiagoLima, limaCaracas, caracasBuenosAires));
		when(maritimeCircuitTwo.originTerminal()).thenReturn(buenosAires);
		when(maritimeCircuitOne.calculateTimeBetween(buenosAires, quito)).thenReturn(8);
		// -------------------------------------------------------------
		// STRETCH
		buenosAiresSantiago = mock(Stretch.class);
		when(buenosAiresSantiago.getOrigin()).thenReturn(buenosAires);
		when(buenosAiresSantiago.getDestiny()).thenReturn(santiago);
		when(buenosAiresSantiago.getTime()).thenReturn(Duration.ofHours(3));

		santiagoQuito = mock(Stretch.class);
		when(santiagoQuito.getOrigin()).thenReturn(santiago);
		when(santiagoQuito.getDestiny()).thenReturn(quito);
		when(santiagoQuito.getTime()).thenReturn(Duration.ofHours(5));

		quitoLima = mock(Stretch.class);
		when(quitoLima.getOrigin()).thenReturn(quito);
		when(quitoLima.getDestiny()).thenReturn(lima);
		when(quitoLima.getTime()).thenReturn(Duration.ofHours(7));

		limaCaracas = mock(Stretch.class);
		when(limaCaracas.getOrigin()).thenReturn(lima);
		when(limaCaracas.getDestiny()).thenReturn(caracas);
		when(limaCaracas.getTime()).thenReturn(Duration.ofHours(9));

		caracasBuenosAires = mock(Stretch.class);
		when(caracasBuenosAires.getOrigin()).thenReturn(caracas);
		when(caracasBuenosAires.getDestiny()).thenReturn(buenosAires);
		when(caracasBuenosAires.getTime()).thenReturn(Duration.ofHours(7));

		santiagoLima = mock(Stretch.class);
		when(santiagoLima.getOrigin()).thenReturn(buenosAires);
		when(santiagoLima.getDestiny()).thenReturn(santiago);
		when(santiagoLima.getTime()).thenReturn(Duration.ofHours(3));
		// -------------------------------------------------------------
		// TERMINAL
		santiago = mock(Terminal.class);
		quito = mock(Terminal.class);
		lima = mock(Terminal.class);
		caracas = mock(Terminal.class);
		// -------------------------------------------------------------
		// TRIP
		tripOne = mock(Trip.class);
		LocalDateTime startDateTripOne = LocalDateTime.of(2023, 11, 26, 20, 0);
		when(tripOne.getStartDate()).thenReturn(startDateTripOne);
		when(tripOne.getMaritimeCircuit()).thenReturn(maritimeCircuitOne);
		when(tripOne.originTerminal()).thenReturn(buenosAires);

		tripTwo = mock(Trip.class);
		LocalDateTime startDateTripTwo = LocalDateTime.of(2023, 10, 1, 12, 0);
		when(tripTwo.getStartDate()).thenReturn(startDateTripTwo);
		when(tripTwo.getMaritimeCircuit()).thenReturn(maritimeCircuitTwo);
		when(tripTwo.originTerminal()).thenReturn(buenosAires);
		// -------------------------------------------------------------
		dateForSearch = startDateTripOne;
		departureDate = new DepartureDate(Criteria.EQUALS, dateForSearch, buenosAires);
	}

	@Test
	void testADepartureDateIsCreated() {
		assertEquals(Criteria.EQUALS, departureDate.getCriteria());
		assertEquals(dateForSearch, departureDate.getDateForSearch());
		assertEquals(buenosAires, departureDate.getOrigin());
	}

	@Test
	void testTheDateIsTheSameAsTheOneYouAreLookingFor() {
		assertEquals(List.of(tripOne), departureDate.filterTrips(List.of(tripOne, tripTwo)));
	}

	@Test
	void testTheDateIsLessThanWhatYouAreLookingFor() {
		// Exercise
		departureDate.setCriteria(Criteria.LESS_THAN);
		when(tripOne.getStartDate()).thenReturn(LocalDateTime.of(2023, 11, 20, 20, 0));
		when(tripTwo.getStartDate()).thenReturn(LocalDateTime.of(2023, 12, 01, 10, 0));
		// Assert
		assertEquals(List.of(tripOne), departureDate.filterTrips(List.of(tripOne, tripTwo)));
	}

	@Test
	void testTheDateIsGreatherThanWhatYouAreLookingFor() {
		// Exercise
		departureDate.setCriteria(Criteria.GREATHER_THAN);
		when(tripOne.getStartDate()).thenReturn(LocalDateTime.of(2023, 11, 30, 20, 0));
		// Assert
		assertEquals(List.of(tripOne), departureDate.filterTrips(List.of(tripOne, tripTwo)));
	}
}
