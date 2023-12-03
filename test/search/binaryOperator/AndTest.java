package search.binaryOperator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import maritimeCircuit.MaritimeCircuit;
import search.criteria.Criteria;
import search.selection.DestinationTerminal;
import search.selection.selectionDate.ArrivalDate;
import search.selection.selectionDate.DepartureDate;
import stretch.Stretch;
import terminal.ManagedTerminal;
import terminal.Terminal;
import trip.Trip;

class AndTest {

	// TODO: REFACTORIZAR POR HERENCIA

	private ManagedTerminal buenosAires;
	// -------------------------------------------------------------
	private Terminal guayaquil;
	private Terminal montevideo;
	private Terminal lima;
	private Terminal valparaiso;
	// ------------------------------------------------------------
	private Stretch buenosAiresValparaiso;
	private Stretch valparaisoLima;
	private Stretch limaGuayaquil;
	private Stretch guayaquilBuenosAires;
	private Stretch montevideoBuenosAires;
	private Stretch limaMontevideo;
	// -------------------------------------------------------------
	private MaritimeCircuit maritimeCircuitOne;
	private MaritimeCircuit maritimeCircuitTwo;
	// -------------------------------------------------------------
	private Trip tripOne;
	private Trip tripTwo;
	// -------------------------------------------------------------
	private ArrivalDate arrivalDate;
	// -------------------------------------------------------------
	private DestinationTerminal destinationTerminal;
	// -------------------------------------------------------------
	private DepartureDate departureDate;
	// -------------------------------------------------------------
	private And and; // SUT

	@BeforeEach
	void setUp() {
		// MANAGED TERMINAL
		buenosAires = mock(ManagedTerminal.class);
		// ------------------------------------------------------------------------------------------
		// TERMINAL
		guayaquil = mock(Terminal.class);
		montevideo = mock(Terminal.class);
		lima = mock(Terminal.class);
		valparaiso = mock(Terminal.class);
		// ------------------------------------------------------------------------------------------
		// STRETCH
		buenosAiresValparaiso = mock(Stretch.class);
		when(buenosAiresValparaiso.getOrigin()).thenReturn(buenosAires);
		when(buenosAiresValparaiso.getDestiny()).thenReturn(valparaiso);
		when(buenosAiresValparaiso.getTime()).thenReturn(Duration.ofHours(13));

		valparaisoLima = mock(Stretch.class);
		when(valparaisoLima.getOrigin()).thenReturn(valparaiso);
		when(valparaisoLima.getDestiny()).thenReturn(lima);
		when(valparaisoLima.getTime()).thenReturn(Duration.ofHours(9));

		limaGuayaquil = mock(Stretch.class);
		when(limaGuayaquil.getOrigin()).thenReturn(lima);
		when(limaGuayaquil.getDestiny()).thenReturn(guayaquil);
		when(limaGuayaquil.getTime()).thenReturn(Duration.ofHours(6));

		guayaquilBuenosAires = mock(Stretch.class);
		when(guayaquilBuenosAires.getOrigin()).thenReturn(guayaquil);
		when(guayaquilBuenosAires.getDestiny()).thenReturn(buenosAires);
		when(guayaquilBuenosAires.getTime()).thenReturn(Duration.ofHours(36));

		montevideoBuenosAires = mock(Stretch.class);
		when(montevideoBuenosAires.getOrigin()).thenReturn(montevideo);
		when(montevideoBuenosAires.getDestiny()).thenReturn(buenosAires);
		when(montevideoBuenosAires.getTime()).thenReturn(Duration.ofHours(7));

		limaMontevideo = mock(Stretch.class);
		when(limaMontevideo.getOrigin()).thenReturn(lima);
		when(limaMontevideo.getDestiny()).thenReturn(montevideo);
		when(limaMontevideo.getTime()).thenReturn(Duration.ofHours(3));
		// ------------------------------------------------------------------------------------------
		// MARITIME CIRCUIT
		maritimeCircuitOne = mock(MaritimeCircuit.class);
		when(maritimeCircuitOne.getStretches())
				.thenReturn(List.of(montevideoBuenosAires, buenosAiresValparaiso, valparaisoLima, limaMontevideo));

		maritimeCircuitTwo = mock(MaritimeCircuit.class);
		when(maritimeCircuitTwo.getStretches())
				.thenReturn(List.of(buenosAiresValparaiso, valparaisoLima, limaGuayaquil, guayaquilBuenosAires));
		// ------------------------------------------------------------------------------------------
		// TRIP
		tripOne = mock(Trip.class);
		when(tripOne.getMaritimeCircuit()).thenReturn(maritimeCircuitOne);
		when(tripOne.getStartDate()).thenReturn(LocalDateTime.of(2023, Month.NOVEMBER, 01, 10, 00));
		// 01-11-23 | 10:00 Hs.

		tripTwo = mock(Trip.class);
		when(tripTwo.getMaritimeCircuit()).thenReturn(maritimeCircuitTwo);
		when(tripTwo.getStartDate()).thenReturn(LocalDateTime.of(2023, Month.NOVEMBER, 12, 12, 00));
		// 12-11-23 | 12:00 Hs.
		// ------------------------------------------------------------------------------------------
		arrivalDate = new ArrivalDate(Criteria.GREATHER_THAN, LocalDate.of(2023, Month.NOVEMBER, 10), lima); // 10-11-23
		// ------------------------------------------------------------------------------------------
		// DEPARTURE DATE
		departureDate = new DepartureDate(Criteria.EQUALS, LocalDate.of(2023, Month.NOVEMBER, 12), buenosAires); // 12-11-23
		// ------------------------------------------------------------------------------------------
		// DESTINATION TERMINAL
		destinationTerminal = new DestinationTerminal(guayaquil);
		// ------------------------------------------------------------------------------------------
		// AND
		and = new And(destinationTerminal, departureDate);
	}

	@Test
	void testShouldReturnLeftClauseForAndFilter() {
		assertEquals(destinationTerminal, and.getLeftClause());
	}

	@Test
	void testShouldReturnRightClauseForAndFilter() {
		assertEquals(departureDate, and.getRightClause());
	}

	@Test
	void testShouldFilterTripsWithBothClausesSatisfied() {
		// Set Up
		when(tripOne.hasTerminal(buenosAires)).thenReturn(true);
		when(tripOne.hasTerminal(guayaquil)).thenReturn(false);
		when(tripOne.calculateEstimatedArrivalDateToTerminal(buenosAires))
				.thenReturn(LocalDateTime.of(2023, Month.NOVEMBER, 01, 14, 00)); // 01-11-23 | 14:00 Hs.

		when(tripTwo.hasTerminal(buenosAires)).thenReturn(true);
		when(tripTwo.hasTerminal(guayaquil)).thenReturn(true);
		when(tripTwo.calculateEstimatedArrivalDateToTerminal(buenosAires))
				.thenReturn(LocalDateTime.of(2023, Month.NOVEMBER, 12, 12, 00)); // 12-11-23 | 10:00 Hs.
		when(tripTwo.calculateEstimatedArrivalDateToTerminal(guayaquil))
				.thenReturn(LocalDateTime.of(2023, Month.NOVEMBER, 13, 18, 00)); // 13-11-23 | 18:00 Hs.
		// Assert
		assertEquals(List.of(tripTwo), and.filterTrips(List.of(tripOne, tripTwo)));
	}

	@Test
	void filterTrips_WhenValidArrivalDateAndTerminalConditions_ReturnsMatchingTrips() {
		// Set Up
		when(tripOne.hasTerminal(buenosAires)).thenReturn(true);
		when(tripOne.hasTerminal(guayaquil)).thenReturn(false);
		when(tripOne.hasTerminal(lima)).thenReturn(true);
		when(tripOne.calculateEstimatedArrivalDateToTerminal(buenosAires))
				.thenReturn(LocalDateTime.of(2023, Month.NOVEMBER, 01, 14, 00)); // 01-11-23 | 14:00 Hs.
		when(tripOne.calculateEstimatedArrivalDateToTerminal(lima))
				.thenReturn(LocalDateTime.of(2023, Month.NOVEMBER, 02, 10, 00)); // 02-11-23 | 10:00 Hs.

		when(tripTwo.hasTerminal(buenosAires)).thenReturn(true);
		when(tripTwo.hasTerminal(guayaquil)).thenReturn(true);
		when(tripTwo.hasTerminal(lima)).thenReturn(true);
		when(tripTwo.calculateEstimatedArrivalDateToTerminal(buenosAires))
				.thenReturn(LocalDateTime.of(2023, Month.NOVEMBER, 12, 12, 00)); // 12-11-23 | 10:00 Hs.
		when(tripTwo.calculateEstimatedArrivalDateToTerminal(lima))
				.thenReturn(LocalDateTime.of(2023, Month.NOVEMBER, 13, 10, 00)); // 13-11-23 | 10:00 Hs.
		when(tripTwo.calculateEstimatedArrivalDateToTerminal(guayaquil))
				.thenReturn(LocalDateTime.of(2023, Month.NOVEMBER, 13, 18, 00)); // 13-11-23 | 18:00 Hs.

		And andTwo = new And(destinationTerminal, departureDate);

		and.setLeftClause(arrivalDate);
		and.setRightClause(andTwo);
		// Assert
		assertEquals(List.of(tripTwo), and.filterTrips(List.of(tripOne, tripTwo)));
	}

}
