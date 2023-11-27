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

class ArrivalDateTest {

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
	private ArrivalDate arrivalDate; // SUT

	@BeforeEach
	void setUp() {
		// MANAGED TERMINAL
		buenosAires = mock(ManagedTerminal.class);
		// -------------------------------------------------------------
		// MARITIME CIRCUIT
		maritimeCircuitOne = mock(MaritimeCircuit.class);
		when(maritimeCircuitOne.getStretchs()).thenReturn(
				Arrays.asList(buenosAiresSantiago, santiagoQuito, quitoLima, limaCaracas, caracasBuenosAires));

		maritimeCircuitTwo = mock(MaritimeCircuit.class);
		when(maritimeCircuitTwo.getStretchs())
				.thenReturn(Arrays.asList(buenosAiresSantiago, santiagoLima, limaCaracas, caracasBuenosAires));
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
		when(tripOne.getMaritimeCircuit()).thenReturn(maritimeCircuitOne);
		when(tripOne.getStartDate()).thenReturn(LocalDateTime.of(2023, 11, 26, 12, 0));
		when(tripOne.dateArrivedToDestiny(quito)).thenReturn(LocalDateTime.of(2023, 11, 26, 20, 0));
		when(tripOne.hasADestinyTerminal(quito)).thenReturn(true);

		tripTwo = mock(Trip.class);
		when(tripTwo.getMaritimeCircuit()).thenReturn(maritimeCircuitTwo);
		when(tripTwo.getStartDate()).thenReturn(LocalDateTime.of(2023, 12, 1, 12, 0));
		when(tripTwo.dateArrivedToDestiny(quito)).thenReturn(LocalDateTime.of(2023, 12, 3, 12, 0));
		when(tripTwo.hasADestinyTerminal(quito)).thenReturn(false);
		// -------------------------------------------------------------
		arrivalDate = new ArrivalDate(Criteria.EQUALS, LocalDateTime.of(2023, 11, 26, 20, 0), quito);
	}

	@Test
	void testAArrivalDateIsCreated() {
		assertEquals(Criteria.EQUALS, arrivalDate.getCriteria());
		assertEquals(LocalDateTime.of(2023, 11, 26, 20, 0), arrivalDate.getDateForSearch());
		assertEquals(quito, arrivalDate.getDestiny());
	}

	@Test
	void testTheDateIsTheSameAsTheOneYouAreLookingFor() {
		assertEquals(List.of(tripOne), arrivalDate.filterTrips(List.of(tripOne, tripTwo)));
	}

	@Test
	void testTheDateIsLessThanWhatYouAreLookingFor() {
		// Exercise
		arrivalDate.setCriteria(Criteria.LESS_THAN);
		when(tripOne.dateArrivedToDestiny(quito)).thenReturn(LocalDateTime.of(2023, 11, 20, 20, 0));
		// Assert
		assertEquals(List.of(tripOne), arrivalDate.filterTrips(List.of(tripOne, tripTwo)));
	}

	@Test
	void testTheDateIsGreatherThanWhatYouAreLookingFor() {
		// Exercise
		arrivalDate.setCriteria(Criteria.GREATHER_THAN);
		when(tripOne.dateArrivedToDestiny(quito)).thenReturn(LocalDateTime.of(2023, 11, 30, 20, 0));
		// Assert
		assertEquals(List.of(tripOne), arrivalDate.filterTrips(List.of(tripOne, tripTwo)));
	}
}
