package shippingLine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import maritimeCircuit.MaritimeCircuit;
import ship.Ship;
import trip.Trip;

class ShippingLineTest {

	private ShippingLine apmMaersk; // SUT
	private Trip trip; // DOC
	private Ship nautilus; // DOC
	private MaritimeCircuit buenosAiresToRoma; // DOC

	@BeforeEach
	void setUp() {
		trip = mock(Trip.class);

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
		apmMaersk.registerMaritimeCircuit(buenosAiresToRoma);
		// Assert
		assertEquals(1, apmMaersk.getMaritimeCircuits().size());
	}

	@Test
	void testAShippingLineRegistersATrip() {
		// SetUp
		apmMaersk.registerMaritimeCircuit(buenosAiresToRoma);
		apmMaersk.registerShip(nautilus);
		when(trip.getMaritimeCircuit()).thenReturn(buenosAiresToRoma);
		when(trip.getShip()).thenReturn(nautilus);
		// Excerise
		apmMaersk.registerTrip(trip);
		// Assert
		assertEquals(1, apmMaersk.getTrips().size());
	}

	@Test
	void testAShippingLineCannotRegisterTheShipBecauseItIsNotRegistered() {
		// SetUp
		apmMaersk.registerMaritimeCircuit(buenosAiresToRoma);
		when(trip.getMaritimeCircuit()).thenReturn(buenosAiresToRoma);
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
		when(trip.getMaritimeCircuit()).thenReturn(buenosAiresToRoma);
		when(trip.getShip()).thenReturn(nautilus);
		// Assert
		assertThrows(RuntimeException.class, () -> {
			apmMaersk.registerTrip(trip);
		}, "The maritime circuit is not registered in the shipping line.");
	}
}
