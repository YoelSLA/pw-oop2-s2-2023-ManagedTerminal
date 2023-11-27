package search.selection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import maritimeCircuit.MaritimeCircuit;
import stretch.Stretch;
import terminal.ManagedTerminal;
import terminal.Terminal;
import trip.Trip;

class DestinationTerminalTest {

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
	private Terminal rioDeJaneiro;
	// -------------------------------------------------------------
	private Trip tripOne;
	private Trip tripTwo;
	// -------------------------------------------------------------
	private DestinationTerminal destinationTerminal; // SUT

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
		// -------------------------------------------------------------
		// STRETCH
		buenosAiresSantiago = mock(Stretch.class);
		when(buenosAiresSantiago.getOrigin()).thenReturn(buenosAires);
		when(buenosAiresSantiago.getDestiny()).thenReturn(santiago);

		santiagoQuito = mock(Stretch.class);
		when(santiagoQuito.getOrigin()).thenReturn(santiago);
		when(santiagoQuito.getDestiny()).thenReturn(quito);

		quitoLima = mock(Stretch.class);
		when(quitoLima.getOrigin()).thenReturn(quito);
		when(quitoLima.getDestiny()).thenReturn(lima);

		limaCaracas = mock(Stretch.class);
		when(limaCaracas.getOrigin()).thenReturn(lima);
		when(limaCaracas.getDestiny()).thenReturn(caracas);

		caracasBuenosAires = mock(Stretch.class);
		when(caracasBuenosAires.getOrigin()).thenReturn(caracas);
		when(caracasBuenosAires.getDestiny()).thenReturn(buenosAires);

		santiagoLima = mock(Stretch.class);
		when(santiagoLima.getOrigin()).thenReturn(buenosAires);
		when(santiagoLima.getDestiny()).thenReturn(santiago);
		// -------------------------------------------------------------
		// TERMINAL
		santiago = mock(Terminal.class);
		quito = mock(Terminal.class);
		lima = mock(Terminal.class);
		caracas = mock(Terminal.class);
		rioDeJaneiro = mock(Terminal.class);
		// -------------------------------------------------------------
		// TRIP
		tripOne = mock(Trip.class);
		when(tripOne.getMaritimeCircuit()).thenReturn(maritimeCircuitOne);
		when(tripOne.hasADestinyTerminal(quito)).thenReturn(true);

		tripTwo = mock(Trip.class);
		when(tripTwo.getMaritimeCircuit()).thenReturn(maritimeCircuitTwo);
		when(tripTwo.hasADestinyTerminal(quito)).thenReturn(false);
		// -------------------------------------------------------------
		destinationTerminal = new DestinationTerminal(quito);
	}

	@Test
	void testAArrivalDateIsCreated() {
		// Assert
		assertEquals(quito, destinationTerminal.getDestiny());
	}

	@Test
	void testTheDestinyIsTheSameAsTheOneYouAreLookingFor() {
		assertEquals(List.of(tripOne), destinationTerminal.filterTrips(List.of(tripOne, tripTwo)));
	}

	@Test
	void testTheDestinyIsNotTheSameAsTheOneYouAreLookingFor() {
		// Exercise
		destinationTerminal.setDestiny(rioDeJaneiro);
		// Assert
		assertEquals(List.of(), destinationTerminal.filterTrips(List.of(tripOne, tripTwo)));
	}

}
