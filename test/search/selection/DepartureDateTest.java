package search.selection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import search.criteria.Criteria;
import search.selection.selectionDate.DepartureDate;

class DepartureDateTest extends SelectionTest {

	private DepartureDate departureDate;

	@BeforeEach
	void setUp() {
		super.setUp();
		// ------------------------------------------------------------------------------------------
		when(buenosAiresValparaiso.getTime()).thenReturn(Duration.ofHours(13));
		when(valparaisoLima.getTime()).thenReturn(Duration.ofHours(9));
		when(limaGuayaquil.getTime()).thenReturn(Duration.ofHours(6));
		when(guayaquilBuenosAires.getTime()).thenReturn(Duration.ofHours(36));
		when(montevideoBuenosAires.getTime()).thenReturn(Duration.ofHours(7));
		when(limaMontevideo.getTime()).thenReturn(Duration.ofHours(3));
		// ------------------------------------------------------------------------------------------
		when(tripOne.getStartDate()).thenReturn(LocalDateTime.of(2023, Month.NOVEMBER, 01, 10, 00));
		// 01-11-23 | 10:00 Hs.
		when(tripOne.calculateEstimatedArrivalDateToTerminal(buenosAires))
				.thenReturn(LocalDateTime.of(2023, Month.NOVEMBER, 01, 14, 00)); // 01-11-23 | 14:00 Hs.

		when(tripTwo.getStartDate()).thenReturn(LocalDateTime.of(2023, Month.NOVEMBER, 12, 12, 00));
		// 12-11-23 | 12:00 Hs.
		when(tripTwo.calculateEstimatedArrivalDateToTerminal(buenosAires))
				.thenReturn(LocalDateTime.of(2023, Month.NOVEMBER, 12, 10, 00)); // 12-11-23 | 12:00 Hs.
		// -------------------------------------------------------------
		departureDate = new DepartureDate(Criteria.GREATHER_THAN, LocalDate.of(2023, Month.NOVEMBER, 10), buenosAires);
	}

	@Test
	void testDepartureDateCriteriaEquals() {
		assertEquals(Criteria.GREATHER_THAN, departureDate.getCriteria());
	}

	@Test
	void testDepartureDateSearchDate() {
		assertEquals(LocalDate.of(2023, Month.NOVEMBER, 10), departureDate.getSearchDate());
	}

	@Test
	void testDepartureDateTerminal() {
		assertEquals(buenosAires, departureDate.getTerminal());
	}

	@Test
	void testShouldFilterTripsByDepartureDateWithDefaultCriteria() {
		assertEquals(List.of(tripTwo), departureDate.filterTrips(List.of(tripOne, tripTwo)));
	}

	@Test
	void shouldFilterTripsByDepartureDateWithEqualityCriteria() {
		// Set Up
		departureDate.setCriteria(Criteria.EQUALS);
		departureDate.setSearchDate(LocalDate.of(2023, Month.NOVEMBER, 01)); // 01-11-23
		// Assert
		assertEquals(List.of(tripOne), departureDate.filterTrips(List.of(tripOne, tripTwo)));
	}

	@Test
	void testShouldFilterTripsByDepartureDateWithLessThanCriteria() {
		// Set Up
		departureDate.setCriteria(Criteria.LESS_THAN);
		departureDate.setSearchDate(LocalDate.of(2023, Month.NOVEMBER, 15)); // 15-11-23
		// Assert
		assertEquals(List.of(tripOne, tripTwo), departureDate.filterTrips(List.of(tripOne, tripTwo)));
	}
}
