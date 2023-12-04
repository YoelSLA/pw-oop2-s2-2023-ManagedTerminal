package search.selection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DestinationTerminalTest extends SelectionTest {

	private DestinationTerminal destinationTerminal;

	@BeforeEach
	void setUp() throws Exception {
		super.setUp();
		// -------------------------------------------------------------
		destinationTerminal = new DestinationTerminal(lima);
		// -------------------------------------------------------------
		when(tripOne.hasTerminal(lima)).thenReturn(true);
		when(tripOne.hasTerminal(guayaquil)).thenReturn(false);

		when(tripTwo.hasTerminal(lima)).thenReturn(true);
		when(tripTwo.hasTerminal(guayaquil)).thenReturn(true);
	}

	@Test
	void testShouldReturnCorrectTerminalForDestinationTerminal() {
		assertEquals(lima, destinationTerminal.getTerminal());
	}

	@Test
	void testShouldFilterTripsWithMatchingTerminals() {
		assertEquals(List.of(tripOne, tripTwo), destinationTerminal.filterTrips(List.of(tripOne, tripTwo)));
	}

	@Test
	void testShouldFilterTripsWithSpecificTerminalExcludingOthers() {
		// Set Up
		destinationTerminal.setTerminal(guayaquil);
		// Assert
		assertEquals(List.of(tripTwo), destinationTerminal.filterTrips(List.of(tripOne, tripTwo)));
	}

}
