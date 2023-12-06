package shippingLine;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
//		// TRIP
//		tripBuenosAiresRioDeJaneiro = mock(Trip.class);
//		when(tripBuenosAiresRioDeJaneiro.getMaritimeCircuit()).thenReturn(circuitBuenosAiresRioDeJaneiro);
//		when(tripBuenosAiresRioDeJaneiro.getShip()).thenReturn(nautilus);

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

//	@Test
//	void setMaritimeCircuits_ShouldUpdateMaritimeCircuitsList_ForApmMaersk() {
//		// Exercise
//		apmMaersk.setMaritimeCircuits(List.of(mock(MaritimeCircuit.class)));
//		// Assert
//		assertEquals(1, apmMaersk.getMaritimeCircuits().size());
//	}
//
//	@Test
//	void registerMaritimeCircuit_ShouldIncreaseMaritimeCircuitsCount_ForApmMaersk() {
//		// Exercise
//		apmMaersk.registerMaritimeCircuit(circuitBuenosAiresRioDeJaneiro);
//		// Assert
//		assertEquals(1, apmMaersk.getMaritimeCircuits().size());
//	}
//
//	@Test
//	void registerShip_ShouldIncreaseShipsCount_ForApmMaersk() {
//		// Exercise
//		apmMaersk.registerShip(nautilus);
//		// Assert
//		assertEquals(1, apmMaersk.getShips().size());
//	}
//
//	@Test
//	void registerTrip_ShouldIncreaseTripsCount_ForApmMaersk() throws Exception {
//		// Exercise
//		apmMaersk.registerMaritimeCircuit(circuitBuenosAiresRioDeJaneiro);
//		apmMaersk.registerShip(nautilus);
//		apmMaersk.registerTrip(tripBuenosAiresRioDeJaneiro);
//		// Assert
//		assertEquals(1, apmMaersk.getTrips().size());
//	}
//
//	@Test
//	void registerTrip_WithoutRegisteredMaritimeCircuit_ShouldThrowRuntimeException() {
//		// Exercise
//		apmMaersk.registerShip(nautilus);
//		// Assert
//		assertThrows("The maritime circuit is not registered in the shipping line.", RuntimeException.class,
//				() -> apmMaersk.registerTrip(tripBuenosAiresRioDeJaneiro));
//	}
//
//	@Test
//	void registerTrip_WithoutRegisteredShip_ShouldThrowRuntimeException() {
//		// Exercise
//		apmMaersk.registerMaritimeCircuit(circuitBuenosAiresRioDeJaneiro);
//		// Assert
//		assertThrows("The ship is not registered in the shipping line.", RuntimeException.class,
//				() -> apmMaersk.registerTrip(tripBuenosAiresRioDeJaneiro));
//	}
//
//	@Test
//	void registerShip_StartTrip_ShouldNotBeInShipsInTripList() {
//		// Exercise
//		apmMaersk.registerShip(nautilus);
//		nautilus.startTrip();
//		// Assert
//		assertEquals(List.of(), apmMaersk.getShipsInTrip());
//	}
//
//	@Test
//	void registerShip_StartTripWithAnotherShip_ShouldBeInShipsInTripList() {
//		// Exercise
//		Ship bismarck = mock(Ship.class);
//		apmMaersk.registerShip(nautilus);
//		apmMaersk.registerShip(bismarck);
//		nautilus.startTrip();
//		// Assert
//		assertEquals(List.of(bismarck), apmMaersk.getShipsInTrip());
//	}
}
