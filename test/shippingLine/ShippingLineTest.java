package shippingLine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.Duration;
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

	private ManagedTerminal buenosAires;
	// ------------------------------------------------------------
	private Terminal valparaiso;
	private Terminal lima;
	private Terminal guayaquil;
	// ------------------------------------------------------------
	private Stretch buenosAiresValparaiso;
	private Stretch valparaisoLima;
	private Stretch limaGuayaquil;
	private Stretch guayaquilBuenosAires;
	// ------------------------------------------------------------
	private MaritimeCircuit maritimeCircuitOne;
	// ------------------------------------------------------------
	private Ship bismarck;
	// ------------------------------------------------------------
	private Trip tripOne;
	// ------------------------------------------------------------
	private ShippingLine apmMaersk; // SUT

	@BeforeEach
	void setUp() {
		buenosAires = mock(ManagedTerminal.class);
		valparaiso = mock(ManagedTerminal.class);
		lima = mock(ManagedTerminal.class);
		guayaquil = mock(ManagedTerminal.class);
		// -------------------------------------------------------------------------------------------
		buenosAiresValparaiso = mock(Stretch.class);
		when(buenosAiresValparaiso.getOrigin()).thenReturn(buenosAires);
		when(buenosAiresValparaiso.getDestiny()).thenReturn(valparaiso);
		when(buenosAiresValparaiso.getPrice()).thenReturn(1.040);
		when(buenosAiresValparaiso.getTime()).thenReturn(Duration.ofHours(13));

		valparaisoLima = mock(Stretch.class);
		when(valparaisoLima.getOrigin()).thenReturn(valparaiso);
		when(valparaisoLima.getDestiny()).thenReturn(lima);
		when(valparaisoLima.getPrice()).thenReturn(2.024);
		when(valparaisoLima.getTime()).thenReturn(Duration.ofHours(9));

		limaGuayaquil = mock(Stretch.class);
		when(limaGuayaquil.getOrigin()).thenReturn(lima);
		when(limaGuayaquil.getDestiny()).thenReturn(guayaquil);
		when(limaGuayaquil.getPrice()).thenReturn(1.821);
		when(limaGuayaquil.getTime()).thenReturn(Duration.ofHours(6));

		guayaquilBuenosAires = mock(Stretch.class);
		when(guayaquilBuenosAires.getOrigin()).thenReturn(guayaquil);
		when(guayaquilBuenosAires.getDestiny()).thenReturn(buenosAires);
		when(guayaquilBuenosAires.getPrice()).thenReturn(2.192);
		when(guayaquilBuenosAires.getTime()).thenReturn(Duration.ofHours(36));
		// -------------------------------------------------------------------------------------------
		maritimeCircuitOne = mock(MaritimeCircuit.class);
		when(maritimeCircuitOne.getStretches())
				.thenReturn(List.of(buenosAiresValparaiso, valparaisoLima, limaGuayaquil, guayaquilBuenosAires));
		// -------------------------------------------------------------------------------------------
		bismarck = mock(Ship.class);
		// -------------------------------------------------------------------------------------------
		// TRIP
		tripOne = mock(Trip.class);
		when(tripOne.getMaritimeCircuit()).thenReturn(maritimeCircuitOne);
		when(tripOne.getShip()).thenReturn(bismarck);
		// -------------------------------------------------------------
		apmMaersk = new ShippingLine("30234051497", "APM Maersk");
	}

	@Test
	void x() {
		assertEquals("30234051497", apmMaersk.getCuit());
	}

	@Test
	void x1() {
		assertEquals(0, apmMaersk.getMaritimeCircuits().size());
	}

	@Test
	void x2() {
		assertEquals("APM Maersk", apmMaersk.getName());
	}

	@Test
	void x3() {
		assertEquals(0, apmMaersk.getShips().size());
	}

	@Test
	void x4() {
		assertEquals(0, apmMaersk.getTrips().size());
	}

	@Test
	void x5() {
		// Exercise
		apmMaersk.registerMaritimeCircuit(maritimeCircuitOne);
		// Assert
		assertTrue(apmMaersk.getMaritimeCircuits().contains(maritimeCircuitOne));
	}

	@Test
	void x6() {
		// Exercise
		apmMaersk.registerShip(bismarck);
		// Assert
		assertTrue(apmMaersk.getShips().contains(bismarck));
	}

	@Test
	void x7() throws Exception {
		// Exercise
		apmMaersk.registerMaritimeCircuit(maritimeCircuitOne);
		apmMaersk.registerShip(bismarck);
		apmMaersk.registerTrip(tripOne);
		// Assert
		assertTrue(apmMaersk.getTrips().contains(tripOne));
	}

	@Test
	void x8() {
		// Exercise
		apmMaersk.registerShip(bismarck);
		// Assert
		assertThrows(RuntimeException.class, () -> apmMaersk.registerTrip(tripOne),
				"The maritime circuit is not registered in the shipping line.");
	}

	@Test
	void x9() {
		// Exercise
		apmMaersk.registerMaritimeCircuit(maritimeCircuitOne);
		// Assert
		assertThrows(RuntimeException.class, () -> apmMaersk.registerTrip(tripOne),
				"The ship is not registered in the shipping line.");
	}

	@Test
	void x10() {
		// Exercise
		when(maritimeCircuitOne.hasATerminal(buenosAires)).thenReturn(true);
		when(maritimeCircuitOne.hasATerminal(lima)).thenReturn(true);
		when(maritimeCircuitOne.calculateTotalHoursBetweenTerminals(buenosAires, lima)).thenReturn(22);
		apmMaersk.registerMaritimeCircuit(maritimeCircuitOne);
		// Assert
		assertEquals(22, apmMaersk.timeItTakesToGetTo(buenosAires, lima));
	}

	@Test
	void x11() {
		// Exercise
		when(maritimeCircuitOne.hasATerminal(buenosAires)).thenReturn(false);
		when(maritimeCircuitOne.hasATerminal(lima)).thenReturn(true);
		apmMaersk.registerMaritimeCircuit(maritimeCircuitOne);
		// Assert
		assertThrows(RuntimeException.class, () -> apmMaersk.timeItTakesToGetTo(buenosAires, lima),
				"Terminal origin not found.");
	}

	@Test
	void x12() {
		// Exercise
		when(maritimeCircuitOne.hasATerminal(buenosAires)).thenReturn(true);
		when(maritimeCircuitOne.hasATerminal(lima)).thenReturn(false);
		apmMaersk.registerMaritimeCircuit(maritimeCircuitOne);
		// Assert
		assertThrows(RuntimeException.class, () -> apmMaersk.timeItTakesToGetTo(buenosAires, lima),
				"There destiny not found.");
	}

	@Test
	void x13() {
		// Exercise
		when(maritimeCircuitOne.hasATerminal(buenosAires)).thenReturn(true);
		apmMaersk.registerMaritimeCircuit(maritimeCircuitOne);
		// Assert
		assertTrue(apmMaersk.hasTerminal(buenosAires));
	}

	@Test
	void x14() {
		// Exercise
		when(bismarck.getIsOnTrip()).thenReturn(true);
		apmMaersk.registerShip(bismarck);
		// Assert
		assertTrue(apmMaersk.shipsInTrip().contains(bismarck));
	}

}