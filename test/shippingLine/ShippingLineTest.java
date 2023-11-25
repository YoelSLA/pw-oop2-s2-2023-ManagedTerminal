package shippingLine;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import maritimeCircuit.MaritimeCircuit;
import ship.Ship;
import trip.Trip;

class ShippingLineTest {

	private ShippingLine apmMaersk; // SUT
	private Trip trip;
	private Ship nautilus;
	private MaritimeCircuit buenosAiresToRoma;

	@BeforeEach
	void setUp() {
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

}
