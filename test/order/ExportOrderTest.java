package order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.Month;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import client.Shipper;
import driver.Driver;
import load.Dry;
import maritimeCircuit.MaritimeCircuit;
import service.ExcessStorage;
import service.Washed;
import service.Weigh;
import terminal.ManagedTerminal;
import terminal.Terminal;
import trip.Trip;
import truck.Truck;

class ExportOrderTest {

	private ManagedTerminal buenosAires;
	// ------------------------------------------------------------
	private Terminal lima;
	// ------------------------------------------------------------
	private MaritimeCircuit maritimeCircuitOne;
	// ------------------------------------------------------------
	private Trip tripOne;
	// ------------------------------------------------------------
	private Shipper ivan;
	// ------------------------------------------------------------
	private Driver alberto;
	// ------------------------------------------------------------
	private Truck volvo;
	// ------------------------------------------------------------
	private Dry dry;
	// ------------------------------------------------------------
	private ExcessStorage excessStorage;
	private Washed washed;
	private Weigh weigh;
	// ------------------------------------------------------------
	private ExportOrder exportOrder;
	// ------------------------------------------------------------

	@BeforeEach
	void setUp() {
		buenosAires = mock(ManagedTerminal.class);
		// -------------------------------------------------------------------------------------------
		lima = mock(Terminal.class);
		// ------------------------------------------------------------------------------------------
		maritimeCircuitOne = mock(MaritimeCircuit.class);
		when(maritimeCircuitOne.originTerminal()).thenReturn(buenosAires);
		// -------------------------------------------------------------------------------------------
		tripOne = mock(Trip.class);
		when(tripOne.getStartDate()).thenReturn(LocalDateTime.of(2023, Month.NOVEMBER, 12, 12, 00)); 
		// 12-11-23 | 12:00 Hs.
		when(tripOne.getMaritimeCircuit()).thenReturn(maritimeCircuitOne);
		when(tripOne.getOriginTerminal()).thenReturn(buenosAires);
		// -------------------------------------------------------------------------------------------
		ivan = mock(Shipper.class);
		// -------------------------------------------------------------------------------------------
		alberto = mock(Driver.class);
		// -------------------------------------------------------------------------------------------
		volvo = mock(Truck.class);
		// -------------------------------------------------------------------------------------------
		dry = mock(Dry.class);
		// -------------------------------------------------------------------------------------------	
		excessStorage = mock(ExcessStorage.class);
		when(excessStorage.getPrice()).thenReturn(2000.0);

		washed = mock(Washed.class);
		when(washed.getPrice()).thenReturn(3000.0);
		
		weigh = mock(Weigh.class);
		when(weigh.getPrice()).thenReturn(4000.0);
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

	@Test
	void testArrivalDate_ReturnsCorrectArrivalDate() {
	    // Set Up
	    LocalDateTime arrivalDate = LocalDateTime.of(2023, Month.NOVEMBER, 12, 12, 00);
	    when(tripOne.calculateEstimatedArrivalDateToTerminal(buenosAires)).thenReturn(arrivalDate);
	    
	    // Assert
	    assertEquals(arrivalDate, exportOrder.arrivalDate());
	}

	@Test
	void testDepartureDate_ReturnsCorrectDepartureDate() {
	    // Set Up
	    LocalDateTime departureDate = LocalDateTime.of(2023, Month.NOVEMBER, 13, 18, 00);
	    when(tripOne.calculateEstimatedArrivalDateToTerminal(lima)).thenReturn(departureDate);
	    
	    // Assert
	    assertEquals(departureDate, exportOrder.departureDate());
	}

	@Test
	void testPriceOfServices_ReturnsCorrectTotalPrice() {
	    // Set Up
	    exportOrder.registerService(excessStorage);
	    exportOrder.registerService(weigh);
	    exportOrder.registerService(washed);
	    
	    // Assert
	    assertEquals(9000.00, exportOrder.priceOfServices());
	}

	@Test
	void tesTravelCost_ReturnsZeroForNoServices() {
	    assertEquals(0.0, exportOrder.travelCost());
	}

}
