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

class OrTest {

	// TODO: REFACTORIZAR POR HERENCIA

	private ManagedTerminal buenosAires;
	// -------------------------------------------------------------
	private Terminal guayaquil;
	private Terminal montevideo;
	private Terminal lima;
	private Terminal valparaiso;
	private Terminal rioDeJaneiro;
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
	private Or or; // SUT

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
		rioDeJaneiro = mock(Terminal.class);
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
		// -------------------------------------------------------------
		arrivalDate = new ArrivalDate(Criteria.LESS_THAN, LocalDate.of(2023, Month.NOVEMBER, 15), valparaiso); // 15-11-23
		// -------------------------------------------------------------
		// DEPARTURE DATE
		departureDate = new DepartureDate(Criteria.EQUALS, LocalDate.of(2023, Month.NOVEMBER, 12), buenosAires); // 12-11-23
		// -------------------------------------------------------------
		// DESTINATION TERMINAL
		destinationTerminal = new DestinationTerminal(rioDeJaneiro);
		// -------------------------------------------------------------
		// OR
		or = new Or(destinationTerminal, departureDate);
	}

	@Test
	void testShouldReturnLeftClauseForAndFilter() {
		assertEquals(destinationTerminal, or.getLeftClause());
	}

	@Test
	void testShouldReturnRightClauseForAndFilter() {
		assertEquals(departureDate, or.getRightClause());
	}

	@Test
	void testFilterTrips_OrConditionWithDifferentArrivalDates_ReturnsMatchingTrips() {
		// Set Up
		when(tripOne.hasTerminal(rioDeJaneiro)).thenReturn(false);
		when(tripOne.hasTerminal(buenosAires)).thenReturn(true);
		when(tripOne.calculateEstimatedArrivalDateToTerminal(buenosAires))
				.thenReturn(LocalDateTime.of(2023, Month.NOVEMBER, 01, 14, 00)); // 01-11-23 | 14:00 Hs.

		when(tripTwo.hasTerminal(rioDeJaneiro)).thenReturn(false);
		when(tripTwo.hasTerminal(buenosAires)).thenReturn(true);
		when(tripTwo.calculateEstimatedArrivalDateToTerminal(buenosAires))
				.thenReturn(LocalDateTime.of(2023, Month.NOVEMBER, 12, 12, 00)); // 12-11-23 | 10:00 Hs.
		// Assert
		assertEquals(List.of(tripTwo), or.filterTrips(List.of(tripOne, tripTwo)));
	}

	@Test
	void testFilterTrips_OrConditionWithMultipleDestinationsAndArrivalDates_ReturnsMatchingTrips() {
		// Set Up

		when(tripOne.hasTerminal(rioDeJaneiro)).thenReturn(false);
		when(tripOne.hasTerminal(buenosAires)).thenReturn(true);
		when(tripOne.hasTerminal(valparaiso)).thenReturn(true);
		when(tripOne.calculateEstimatedArrivalDateToTerminal(buenosAires))
				.thenReturn(LocalDateTime.of(2023, Month.NOVEMBER, 01, 14, 00)); // 01-11-23 | 14:00 Hs.
		when(tripOne.calculateEstimatedArrivalDateToTerminal(valparaiso))
				.thenReturn(LocalDateTime.of(2023, Month.NOVEMBER, 01, 22, 00)); // 01-11-23 | 22:00 Hs.

		when(tripTwo.hasTerminal(rioDeJaneiro)).thenReturn(false);
		when(tripTwo.hasTerminal(buenosAires)).thenReturn(true);
		when(tripTwo.hasTerminal(valparaiso)).thenReturn(true);
		when(tripTwo.calculateEstimatedArrivalDateToTerminal(buenosAires))
				.thenReturn(LocalDateTime.of(2023, Month.NOVEMBER, 12, 12, 00)); // 12-11-23 | 12:00 Hs.
		when(tripTwo.calculateEstimatedArrivalDateToTerminal(valparaiso))
				.thenReturn(LocalDateTime.of(2023, Month.NOVEMBER, 13, 01, 00)); // 13-11-23 | 01:00 Hs.

		Or orTwo = new Or(destinationTerminal, departureDate);

		or.setLeftClause(arrivalDate);
		or.setRightClause(orTwo);
		// Assert
		assertEquals(List.of(tripOne, tripTwo), or.filterTrips(List.of(tripOne, tripTwo)));
	}

}
