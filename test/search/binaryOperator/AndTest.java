package search.binaryOperator;

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
import search.Search;
import search.criteria.Criteria;
import search.selection.DestinationTerminal;
import search.selection.selectionDate.ArrivalDate;
import stretch.Stretch;
import terminal.ManagedTerminal;
import terminal.Terminal;
import trip.Trip;

class AndTest {

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
	private And and; // SUT
	private Search leftClause;
	private Search rightClause;

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
		// ------------------------------------------------------------ -
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
		when(tripOne.dateArrivedToDestiny(lima)).thenReturn(LocalDateTime.of(2023, 6, 1, 12, 0));
		when(tripOne.hasADestinyTerminal(lima)).thenReturn(true);

		tripTwo = mock(Trip.class);
		when(tripTwo.getMaritimeCircuit()).thenReturn(maritimeCircuitTwo);
		when(tripTwo.getStartDate()).thenReturn(LocalDateTime.of(2023, 12, 1, 12, 0));
		when(tripTwo.dateArrivedToDestiny(lima)).thenReturn(LocalDateTime.of(2023, 8, 1, 12, 0));
		when(tripTwo.hasADestinyTerminal(lima)).thenReturn(true);
		// -------------------------------------------------------------
		leftClause = new DestinationTerminal(lima);
		rightClause = new ArrivalDate(Criteria.GREATHER_THAN, LocalDateTime.of(2022, 12, 1, 12, 0), lima);

		and = new And(leftClause, rightClause);
	}

	@Test
	void testAAndIsCreated() {
		assertEquals(leftClause, and.getLeftClause());
		assertEquals(rightClause, and.getRightClause());

	}

	@Test
	void testAnd() {
		assertEquals(List.of(tripOne, tripTwo), and.filterTrips(List.of(tripOne, tripTwo)));

	}

}
