package search.selection.selectionDate;

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
import stretch.Stretch;
import terminal.ManagedTerminal;
import terminal.Terminal;
import trip.Trip;

class ArrivalDateTest {

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
	private ArrivalDate arrivalDate; // SUT

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
		when(tripOne.hasTerminal(lima)).thenReturn(true);
		when(tripOne.calculateEstimatedArrivalDateToTerminal(lima))
				.thenReturn(LocalDateTime.of(2023, Month.NOVEMBER, 02, 10, 00)); // 02-11-23 | 10:00 Hs.

		tripTwo = mock(Trip.class);
		when(tripTwo.getMaritimeCircuit()).thenReturn(maritimeCircuitTwo);
		when(tripTwo.getStartDate()).thenReturn(LocalDateTime.of(2023, Month.NOVEMBER, 12, 12, 00));
		// 12-11-23 | 12:00 Hs.
		when(tripTwo.hasTerminal(lima)).thenReturn(true);
		when(tripTwo.calculateEstimatedArrivalDateToTerminal(lima))
				.thenReturn(LocalDateTime.of(2023, Month.NOVEMBER, 13, 10, 00)); // 13-11-23 | 10:00 Hs.
		// -------------------------------------------------------------
		arrivalDate = new ArrivalDate(Criteria.GREATHER_THAN, LocalDate.of(2023, Month.NOVEMBER, 10), lima);
	}

	@Test
	void testArrivalDateCriteriaEquals() {
		assertEquals(Criteria.GREATHER_THAN, arrivalDate.getCriteria());
	}

	@Test
	void testArrivalDateSearchDate() {
		assertEquals(LocalDate.of(2023, Month.NOVEMBER, 10), arrivalDate.getSearchDate());
	}

	@Test
	void testArrivalDateTerminal() {
		assertEquals(lima, arrivalDate.getTerminal());
	}

	@Test
	void testDifferentArrivalDates_FilterByUnknownCriteria() {
		assertEquals(List.of(tripTwo), arrivalDate.filterTrips(List.of(tripOne, tripTwo)));
	}

	@Test
	void testSameArrivalDateAndEqualsCriteria_FilterBySpecificDate() {
		// Set Up
		arrivalDate.setCriteria(Criteria.EQUALS);
		arrivalDate.setSearchDate(LocalDate.of(2023, Month.NOVEMBER, 02)); // 02-11-23
		// Assert
		assertEquals(List.of(tripOne), arrivalDate.filterTrips(List.of(tripOne, tripTwo)));
	}

	@Test
	void testSameArrivalDateAndLessThanCriteria_FilterByDifferentDate() {
		// Set Up
		arrivalDate.setCriteria(Criteria.LESS_THAN);
		arrivalDate.setSearchDate(LocalDate.of(2023, Month.NOVEMBER, 15)); // 15-11-23
		// Assert
		assertEquals(List.of(tripOne, tripTwo), arrivalDate.filterTrips(List.of(tripOne, tripTwo)));
	}

	@Test
	void noLimaTerminalAndEqualsCriteria_FilterByGuayaquilAndDate() {
		// Set Up
		when(tripOne.hasTerminal(lima)).thenReturn(false);

		when(tripTwo.hasTerminal(guayaquil)).thenReturn(true);
		when(tripTwo.calculateEstimatedArrivalDateToTerminal(guayaquil))
				.thenReturn(LocalDateTime.of(2023, Month.NOVEMBER, 13, 18, 00)); // 13-11-23 | 18:00 Hs.

		arrivalDate.setCriteria(Criteria.EQUALS);
		arrivalDate.setSearchDate(LocalDate.of(2023, Month.NOVEMBER, 13)); // 13-11-23
		arrivalDate.setTerminal(guayaquil);
		// Assert
		assertEquals(List.of(tripTwo), arrivalDate.filterTrips(List.of(tripOne, tripTwo)));
	}

}
