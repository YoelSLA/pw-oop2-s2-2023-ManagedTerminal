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
import search.selection.selectionDate.ArrivalDate;

class ArrivalDateTest extends SelectionTest {

	private ArrivalDate arrivalDate; // SUT

	@BeforeEach
	void setUp() throws Exception {
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
		when(tripOne.hasTerminal(lima)).thenReturn(true);
		when(tripOne.calculateEstimatedArrivalDateToTerminal(lima))
				.thenReturn(LocalDateTime.of(2023, Month.NOVEMBER, 02, 10, 00)); // 02-11-23 | 10:00 Hs.

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
	void noLimaTerminalAndEqualsCriteria_FilterByGuayaquilAndDate() throws Exception {
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
