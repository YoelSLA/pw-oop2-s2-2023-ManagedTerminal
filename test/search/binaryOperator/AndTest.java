package search.binaryOperator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import search.criteria.Criteria;
import search.selection.DestinationTerminal;
import search.selection.selectionDate.ArrivalDate;

class AndTest extends BinaryOperatorTest {

	private And and;

	@BeforeEach
	void setUp() throws Exception {
		super.setUp();
		// ------------------------------------------------------------------------------------------
		arrivalDate = new ArrivalDate(Criteria.GREATHER_THAN, LocalDate.of(2023, Month.NOVEMBER, 10), lima); // 10-11-23
		// ------------------------------------------------------------------------------------------
		destinationTerminal = new DestinationTerminal(guayaquil);
		// ------------------------------------------------------------------------------------------
		and = new And(destinationTerminal, departureDate);
		// ------------------------------------------------------------------------------------------
		when(tripOne.hasTerminal(guayaquil)).thenReturn(false);

		when(tripTwo.hasTerminal(buenosAires)).thenReturn(true);
		when(tripTwo.hasTerminal(guayaquil)).thenReturn(true);
		when(tripTwo.calculateEstimatedArrivalDateToTerminal(guayaquil))
				.thenReturn(LocalDateTime.of(2023, Month.NOVEMBER, 13, 18, 00)); // 13-11-23 | 18:00 Hs.
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
		assertEquals(List.of(tripTwo), and.filterTrips(List.of(tripOne, tripTwo)));
	}

	@Test
	void filterTrips_WhenValidArrivalDateAndTerminalConditions_ReturnsMatchingTrips() throws Exception {
		// Set Up
		when(tripOne.hasTerminal(lima)).thenReturn(true);
		when(tripOne.calculateEstimatedArrivalDateToTerminal(lima))
				.thenReturn(LocalDateTime.of(2023, Month.NOVEMBER, 02, 10, 00)); // 02-11-23 | 10:00 Hs.

		when(tripTwo.hasTerminal(lima)).thenReturn(true);
		when(tripTwo.calculateEstimatedArrivalDateToTerminal(lima))
				.thenReturn(LocalDateTime.of(2023, Month.NOVEMBER, 13, 10, 00)); // 13-11-23 | 10:00 Hs.

		And andTwo = new And(destinationTerminal, departureDate);

		and.setLeftClause(arrivalDate);
		and.setRightClause(andTwo);
		// Assert
		assertEquals(List.of(tripTwo), and.filterTrips(List.of(tripOne, tripTwo)));
	}

}
