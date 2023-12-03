package search.selection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import maritimeCircuit.MaritimeCircuit;
import stretch.Stretch;
import terminal.ManagedTerminal;
import terminal.Terminal;
import trip.Trip;

class DestinationTerminalTest {

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
	private DestinationTerminal destinationTerminal; // SUT

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

		tripTwo = mock(Trip.class);
		when(tripTwo.getMaritimeCircuit()).thenReturn(maritimeCircuitTwo);
		// -------------------------------------------------------------
		destinationTerminal = new DestinationTerminal(lima);
	}

	@Test
	void testShouldReturnCorrectTerminalForDestinationTerminal() {
		assertEquals(lima, destinationTerminal.getTerminal());
	}

	@Test
	void testShouldFilterTripsWithMatchingTerminals() {
		// Set Up
		when(tripOne.hasTerminal(lima)).thenReturn(true);

		when(tripTwo.hasTerminal(lima)).thenReturn(true);
		// Assert
		assertEquals(List.of(tripOne, tripTwo), destinationTerminal.filterTrips(List.of(tripOne, tripTwo)));
	}

	@Test
	void testShouldFilterTripsWithSpecificTerminalExcludingOthers() {
		// Set Up
		when(tripOne.hasTerminal(guayaquil)).thenReturn(false);

		when(tripTwo.hasTerminal(guayaquil)).thenReturn(true);

		destinationTerminal.setTerminal(guayaquil);

		// Assert
		assertEquals(List.of(tripTwo), destinationTerminal.filterTrips(List.of(tripOne, tripTwo)));
	}

}
