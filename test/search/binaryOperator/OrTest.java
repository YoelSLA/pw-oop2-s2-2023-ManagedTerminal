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

class OrTest extends BinaryOperatorTest {

	private Or or;

	@BeforeEach
	void setUp() {
		super.setUp();
		// ------------------------------------------------------------------------------------------
		arrivalDate = new ArrivalDate(Criteria.LESS_THAN, LocalDate.of(2023, Month.NOVEMBER, 15), valparaiso); // 15-11-23
		// -------------------------------------------------------------
		destinationTerminal = new DestinationTerminal(rioDeJaneiro);
		// ------------------------------------------------------------------------------------------
		or = new Or(destinationTerminal, departureDate);
		// ------------------------------------------------------------------------------------------
		when(tripOne.hasTerminal(rioDeJaneiro)).thenReturn(false);

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
		assertEquals(List.of(tripTwo), or.filterTrips(List.of(tripOne, tripTwo)));
	}

	@Test
	void testFilterTrips_OrConditionWithMultipleDestinationsAndArrivalDates_ReturnsMatchingTrips() {
		// Set Up
		when(tripOne.hasTerminal(valparaiso)).thenReturn(true);
		when(tripOne.calculateEstimatedArrivalDateToTerminal(valparaiso))
				.thenReturn(LocalDateTime.of(2023, Month.NOVEMBER, 01, 22, 00)); // 01-11-23 | 22:00 Hs.

		when(tripTwo.hasTerminal(rioDeJaneiro)).thenReturn(false);
		when(tripTwo.hasTerminal(buenosAires)).thenReturn(true);
		when(tripTwo.hasTerminal(valparaiso)).thenReturn(true);
		when(tripTwo.calculateEstimatedArrivalDateToTerminal(valparaiso))
				.thenReturn(LocalDateTime.of(2023, Month.NOVEMBER, 13, 01, 00)); // 13-11-23 | 01:00 Hs.

		Or orTwo = new Or(destinationTerminal, departureDate);

		or.setLeftClause(arrivalDate);
		or.setRightClause(orTwo);
		// Assert
		assertEquals(List.of(tripOne, tripTwo), or.filterTrips(List.of(tripOne, tripTwo)));
	}

}
