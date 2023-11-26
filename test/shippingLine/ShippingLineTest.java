package shippingLine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import maritimeCircuit.MaritimeCircuit;
import ship.Ship;
import stretch.Stretch;
import terminal.ManagedTerminal;
import terminal.Terminal;
import trip.Trip;

class ShippingLineTest {

	private ShippingLine apmMaersk; // SUT
	private Trip trip; // DOC
	private Ship nautilus; // DOC
	private Stretch buenosAiresMontevideo; // DOC
	private Stretch montevideoRioDeJaneiro; // DOC
	private Stretch rioDeJaneiroBuenosAires; // DOC
	private ManagedTerminal terminalBuenosAires; // DOC
	private Terminal terminalMontevideo; // DOC
	private Terminal terminalRioDeJaneiro; // DOC

	@BeforeEach
	void setUp() {
		trip = mock(Trip.class);
		buenosAiresMontevideo = mock(Stretch.class);
		montevideoRioDeJaneiro = mock(Stretch.class);
		rioDeJaneiroBuenosAires = mock(Stretch.class);
		terminalBuenosAires = mock(ManagedTerminal.class);
		terminalMontevideo = mock(Terminal.class);
		terminalRioDeJaneiro = mock(Terminal.class);

		buenosAiresToRioDeJaneiro = new MaritimeCircuit(
				List.of(buenosAiresMontevideo, montevideoRioDeJaneiro, rioDeJaneiroBuenosAires));
		apmMaersk = new ShippingLine("30234051497", "APM Maersk");

	}

	@Test
	void testAShippingLineIsCreated() {
		// Assert
		assertEquals("30234051497", apmMaersk.getCuit());
		assertEquals(0, apmMaersk.getMaritimeCircuits().size());
		assertEquals("APM Maersk", apmMaersk.getName());
		assertEquals(0, apmMaersk.getShips().size());
		assertEquals(0, apmMaersk.getTrips().size());
	}

	@Test
	void testAShippingLineRegistersAShip() {
		// Excerise
		apmMaersk.registerShip(nautilus);
		// Assert
		assertEquals(1, apmMaersk.getShips().size());
	}

	@Test
	void testAShippingLineRegistersAMaritimeCircuit() {
		// Excerise
		apmMaersk.registerMaritimeCircuit(buenosAiresToRioDeJaneiro);
		// Assert
		assertEquals(1, apmMaersk.getMaritimeCircuits().size());
	}

	@Test
	void testAShippingLineRegistersATrip() throws Exception {
		// SetUp
		apmMaersk.registerMaritimeCircuit(buenosAiresToRioDeJaneiro);
		apmMaersk.registerShip(nautilus);
		when(trip.getMaritimeCircuit()).thenReturn(buenosAiresToRioDeJaneiro);
		when(trip.getShip()).thenReturn(nautilus);
		// Excerise
		apmMaersk.registerTrip(trip);
		// Assert
		assertEquals(1, apmMaersk.getTrips().size());
	}

	@Test
	void testAShippingLineCannotRegisterTheShipBecauseItIsNotRegistered() {
		// SetUp
		apmMaersk.registerMaritimeCircuit(buenosAiresToRioDeJaneiro);
		when(trip.getMaritimeCircuit()).thenReturn(buenosAiresToRioDeJaneiro);
		when(trip.getShip()).thenReturn(nautilus);
		// Assert
		assertThrows(RuntimeException.class, () -> {
			apmMaersk.registerTrip(trip);
		}, "The ship is not registered in the shipping line.");
	}

	@Test
	void testAShippingLineCannotRegisterTheMaritimeCircuitBecauseItIsNotRegistered() {
		// SetUp
		apmMaersk.registerShip(nautilus);
		when(trip.getMaritimeCircuit()).thenReturn(buenosAiresToRioDeJaneiro);
		when(trip.getShip()).thenReturn(nautilus);
		// Assert
		assertThrows(RuntimeException.class, () -> {
			apmMaersk.registerTrip(trip);
		}, "The maritime circuit is not registered in the shipping line.");
	}

	@Test
	void testAshippingLineContainsMaritimeCircuitsWithTheSelectedTerminals() {
		// SetUp
		when(buenosAiresMontevideo.getOrigin()).thenReturn(terminalBuenosAires);
		when(buenosAiresMontevideo.getDestiny()).thenReturn(terminalMontevideo);
		when(montevideoRioDeJaneiro.getOrigin()).thenReturn(terminalMontevideo);
		when(montevideoRioDeJaneiro.getDestiny()).thenReturn(terminalRioDeJaneiro);
		when(rioDeJaneiroBuenosAires.getOrigin()).thenReturn(terminalRioDeJaneiro);
		when(rioDeJaneiroBuenosAires.getDestiny()).thenReturn(terminalBuenosAires);

		assertEquals(List.of(buenosAiresToRioDeJaneiro),
				apmMaersk.maritimeCircuitsContaining(terminalBuenosAires, terminalRioDeJaneiro));
	}
}
