package order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import client.Shipper;
import driver.Driver;
import load.Dry;
import maritimeCircuit.MaritimeCircuit;
import ship.Ship;
import stretch.Stretch;
import terminal.ManagedTerminal;
import terminal.Terminal;
import trip.Trip;
import truck.Truck;

class ExportOrderTest {

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
	private Trip tripOne;
	// ------------------------------------------------------------
	private Ship bismarck;
	// ------------------------------------------------------------
	private Shipper ivan;
	// ------------------------------------------------------------
	private Driver alberto;
	// ------------------------------------------------------------
	private Truck volvo;
	// ------------------------------------------------------------
	private Dry dry;
	// ------------------------------------------------------------
	private ExportOrder exportOrder;
	// ------------------------------------------------------------

	@BeforeEach
	void setUp() {
		buenosAires = mock(ManagedTerminal.class);
		// -------------------------------------------------------------------------------------------
		valparaiso = mock(Terminal.class);
		lima = mock(Terminal.class);
		guayaquil = mock(Terminal.class);
		// ------------------------------------------------------------------------------------------
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
		// ------------------------------------------------------------------------------------------
		maritimeCircuitOne = mock(MaritimeCircuit.class);
		when(maritimeCircuitOne.getStretches())
				.thenReturn(List.of(buenosAiresValparaiso, valparaisoLima, limaGuayaquil, guayaquilBuenosAires));
		when(maritimeCircuitOne.originTerminal()).thenReturn(buenosAires);
		// -------------------------------------------------------------------------------------------
		tripOne = mock(Trip.class);
		when(tripOne.getStartDate()).thenReturn(LocalDateTime.of(2023, Month.NOVEMBER, 12, 12, 00)); 
		// 12-11-23 | 12:00 Hs.
		when(tripOne.getMaritimeCircuit()).thenReturn(maritimeCircuitOne);
		when(tripOne.getOriginTerminal()).thenReturn(buenosAires);
		// -------------------------------------------------------------------------------------------
		bismarck = mock(Ship.class);
		when(tripOne.getShip()).thenReturn(bismarck);
		// -------------------------------------------------------------------------------------------
		ivan = mock(Shipper.class);
		// -------------------------------------------------------------------------------------------
		alberto = mock(Driver.class);
		// -------------------------------------------------------------------------------------------
		volvo = mock(Truck.class);
		// -------------------------------------------------------------------------------------------
		dry = mock(Dry.class);
		// -------------------------------------------------------------------------------------------
		exportOrder = new ExportOrder(ivan, tripOne, dry, lima, alberto, volvo);
	}
	
	@Test
	void testGetClient_ReturnsCorrectClient() {
	    assertEquals(ivan, exportOrder.getClient());
	}

	@Test
	void testGetTrip_ReturnsCorrectTrip() {
	    assertEquals(tripOne, exportOrder.getTrip());
	}

	@Test
	void testGetLoad_ReturnsCorrectLoad() {
	    assertEquals(dry, exportOrder.getLoad());
	}

	@Test
	void testGetOrigin_ReturnsCorrectOrigin() {
	    assertEquals(buenosAires, exportOrder.getOrigin());
	}

	@Test
	void testGetDestiny_ReturnsCorrectDestiny() {
	    assertEquals(lima, exportOrder.getDestiny());
	}

	@Test
	void testGetDriver_ReturnsCorrectDriver() {
	    assertEquals(alberto, exportOrder.getDriver());
	}

	@Test
	void testGetTruck_ReturnsCorrectTruck() {
	    assertEquals(volvo, exportOrder.getTruck());
	}

	@Test
	void testGetServices_ReturnsEmptyServicesListInitially() {
	    assertTrue(exportOrder.getServices().isEmpty());
	}
}
