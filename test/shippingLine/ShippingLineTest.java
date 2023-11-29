package shippingLine;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import maritimeCircuit.MaritimeCircuit;
import ship.Ship;
import stretch.Stretch;
import trip.Trip;

class ShippingLineTest {

	private Stretch buenosAiresMontevideo;
	private Stretch montevideoRioDeJaneiro;
	private Stretch rioDeJaneiroBuenosAires;
// -------------------------------------------------------------
	private MaritimeCircuit circuitBuenosAiresRioDeJaneiro;
// -------------------------------------------------------------
	private Ship nautilus;
// -------------------------------------------------------------
	private Trip tripBuenosAiresRioDeJaneiro;
// -------------------------------------------------------------
	private ShippingLine apmMaersk; // SUT

	@BeforeEach
	void setUp() {
// -------------------------------------------------------------
		// STRETCH
		buenosAiresMontevideo = mock(Stretch.class);
		montevideoRioDeJaneiro = mock(Stretch.class);
		rioDeJaneiroBuenosAires = mock(Stretch.class);
// -------------------------------------------------------------
		// MARITIME CIRCUIT
		circuitBuenosAiresRioDeJaneiro = mock(MaritimeCircuit.class);
		when(circuitBuenosAiresRioDeJaneiro.getStretches())
				.thenReturn(List.of(buenosAiresMontevideo, montevideoRioDeJaneiro, rioDeJaneiroBuenosAires));
// -------------------------------------------------------------
		// SHIP
		nautilus = mock(Ship.class);
		when(nautilus.getIsOnTrip()).thenReturn(true);
// -------------------------------------------------------------
		// TRIP
		tripBuenosAiresRioDeJaneiro = mock(Trip.class);
		when(tripBuenosAiresRioDeJaneiro.getMaritimeCircuit()).thenReturn(circuitBuenosAiresRioDeJaneiro);
		when(tripBuenosAiresRioDeJaneiro.getShip()).thenReturn(nautilus);

		// -------------------------------------------------------------
		apmMaersk = new ShippingLine("30234051497", "APM Maersk");
	}

	@Test
	void getCuit_ShouldReturnCorrectCuit_ForApmMaersk() {
		assertEquals("30234051497", apmMaersk.getCuit());
	}

	@Test
	void getMaritimeCircuits_ShouldReturnEmptyList_ForNewShippingLine() {
		assertEquals(0, apmMaersk.getMaritimeCircuits().size());
	}

	@Test
	void getName_ShouldReturnApmMaersk_ForNewShippingLine() {
		assertEquals("APM Maersk", apmMaersk.getName());
	}

	@Test
	void getShips_ShouldReturnEmptyList_ForNewShippingLine() {
		assertEquals(0, apmMaersk.getShips().size());
	}

	@Test
	void getTrips_ShouldReturnEmptyList_ForNewShippingLine() {
		assertEquals(0, apmMaersk.getTrips().size());
	}

	@Test
	void setMaritimeCircuits_ShouldUpdateMaritimeCircuitsList_ForApmMaersk() {
		// Exercise
		apmMaersk.setMaritimeCircuits(List.of(mock(MaritimeCircuit.class)));
		// Assert
		assertEquals(1, apmMaersk.getMaritimeCircuits().size());
	}

	@Test
	void registerMaritimeCircuit_ShouldIncreaseMaritimeCircuitsCount_ForApmMaersk() {
		// Exercise
		apmMaersk.registerMaritimeCircuit(circuitBuenosAiresRioDeJaneiro);
		// Assert
		assertEquals(1, apmMaersk.getMaritimeCircuits().size());
	}

	@Test
	void registerShip_ShouldIncreaseShipsCount_ForApmMaersk() {
		// Exercise
		apmMaersk.registerShip(nautilus);
		// Assert
		assertEquals(1, apmMaersk.getShips().size());
	}

	@Test
	void registerTrip_ShouldIncreaseTripsCount_ForApmMaersk() throws Exception {
		// Exercise
		apmMaersk.registerMaritimeCircuit(circuitBuenosAiresRioDeJaneiro);
		apmMaersk.registerShip(nautilus);
		apmMaersk.registerTrip(tripBuenosAiresRioDeJaneiro);
		// Assert
		assertEquals(1, apmMaersk.getTrips().size());
	}

	@Test
	void registerTrip_WithoutRegisteredMaritimeCircuit_ShouldThrowRuntimeException() {
		// Exercise
		apmMaersk.registerShip(nautilus);
		// Assert
		assertThrows("The maritime circuit is not registered in the shipping line.", RuntimeException.class,
				() -> apmMaersk.registerTrip(tripBuenosAiresRioDeJaneiro));
	}

	@Test
	void registerTrip_WithoutRegisteredShip_ShouldThrowRuntimeException() {
		// Exercise
		apmMaersk.registerMaritimeCircuit(circuitBuenosAiresRioDeJaneiro);
		// Assert
		assertThrows("The ship is not registered in the shipping line.", RuntimeException.class,
				() -> apmMaersk.registerTrip(tripBuenosAiresRioDeJaneiro));
	}

	@Test
	void registerShip_StartTrip_ShouldNotBeInShipsInTripList() {
		// Exercise
		apmMaersk.registerShip(nautilus);
		nautilus.startTrip();
		// Assert
		assertEquals(List.of(), apmMaersk.getShipsInTrip());
	}

	@Test
	void registerShip_StartTripWithAnotherShip_ShouldBeInShipsInTripList() {
		// Exercise
		Ship bismarck = mock(Ship.class);
		apmMaersk.registerShip(nautilus);
		apmMaersk.registerShip(bismarck);
		nautilus.startTrip();
		// Assert
		assertEquals(List.of(bismarck), apmMaersk.getShipsInTrip());
	}
}
