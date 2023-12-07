package order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.Month;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import client.Consignee;
import driver.Driver;
import load.Reefer;
import maritimeCircuit.MaritimeCircuit;
import service.Electricity;
import service.ExcessStorage;
import service.Washed;
import service.Weigh;
import terminal.ManagedTerminal;
import terminal.Terminal;
import trip.Trip;
import truck.Truck;

class ImportOrderTest {

	private ManagedTerminal buenosAires;
	// ------------------------------------------------------------
	private Terminal montevideo;
	// ------------------------------------------------------------
	private MaritimeCircuit maritimeCircuitOne;
	// ------------------------------------------------------------
	private Trip tripOne;
	// ------------------------------------------------------------
	private Consignee yoel;
	// ------------------------------------------------------------
	private Driver alberto;
	// ------------------------------------------------------------
	private Truck volvo;
	// ------------------------------------------------------------
	private Reefer reefer;
	// ------------------------------------------------------------
	private Electricity electricity;
	private ExcessStorage excessStorage;
	private Washed washed;
	private Weigh weigh;
	// ------------------------------------------------------------
	private ImportOrder importOrder;
	// ------------------------------------------------------------

	@BeforeEach
	void setUp() {
		buenosAires = mock(ManagedTerminal.class);
		// -------------------------------------------------------------------------------------------
		montevideo = mock(Terminal.class); 
		// ------------------------------------------------------------------------------------------
		maritimeCircuitOne = mock(MaritimeCircuit.class);
		when(maritimeCircuitOne.originTerminal()).thenReturn(buenosAires);
		// -------------------------------------------------------------------------------------------
		tripOne = mock(Trip.class);
		when(tripOne.getStartDate()).thenReturn(LocalDateTime.of(2023, Month.NOVEMBER, 01, 10, 00)); 
		// 01-11-23 | 10:00 Hs.
		when(tripOne.getMaritimeCircuit()).thenReturn(maritimeCircuitOne);
		when(tripOne.getOriginTerminal()).thenReturn(montevideo);
		// -------------------------------------------------------------------------------------------
		yoel = mock(Consignee.class);
		// -------------------------------------------------------------------------------------------
		alberto = mock(Driver.class);
		// -------------------------------------------------------------------------------------------
		volvo = mock(Truck.class);
		// -------------------------------------------------------------------------------------------
		reefer = mock(Reefer.class);
		// -------------------------------------------------------------------------------------------
		electricity = mock(Electricity.class);
		when(electricity.getPrice()).thenReturn(1000.0);
		
		excessStorage = mock(ExcessStorage.class);
		when(excessStorage.getPrice()).thenReturn(2000.0);

		washed = mock(Washed.class);
		when(washed.getPrice()).thenReturn(3000.0);
		
		weigh = mock(Weigh.class);
		when(weigh.getPrice()).thenReturn(4000.0);
		// -------------------------------------------------------------------------------------------
		importOrder = new ImportOrder(yoel, tripOne, reefer, montevideo, buenosAires, alberto, volvo);
	}
	
	@Test
	void testGetClient_ReturnsCorrectClient() {
	    assertEquals(yoel, importOrder.getClient());
	}

	@Test
	void testGetTrip_ReturnsCorrectTrip() {
	    assertEquals(tripOne, importOrder.getTrip());
	}

	@Test
	void testGetLoad_ReturnsCorrectLoad() {
	    assertEquals(reefer, importOrder.getLoad());
	}

	@Test
	void testGetOrigin_ReturnsCorrectOrigin() {
	    assertEquals(montevideo, importOrder.getOrigin());
	}

	@Test
	void testGetDestiny_ReturnsCorrectDestiny() {
	    assertEquals(buenosAires, importOrder.getDestiny());
	}

	@Test
	void testGetDriver_ReturnsCorrectDriver() {
	    assertEquals(alberto, importOrder.getDriver());
	}

	@Test
	void testGetTruck_ReturnsCorrectTruck() {
	    assertEquals(volvo, importOrder.getTruck());
	}

	@Test
	void testGetServices_ReturnsEmptyServicesListInitially() {
	    assertTrue(importOrder.getServices().isEmpty());
	}

	@Test
	void testArrivalDate_ReturnsCorrectArrivalDate() {
	    // Set Up
	    LocalDateTime arrivalDate = LocalDateTime.of(2023, Month.NOVEMBER, 01, 10, 00);
	    when(tripOne.calculateEstimatedArrivalDateToTerminal(montevideo)).thenReturn(arrivalDate);
	    
	    // Assert
	    assertEquals(arrivalDate, importOrder.arrivalDate());
	}

	@Test
	void testDepartureDate_ReturnsCorrectDepartureDate() {
	    // Set Up
	    LocalDateTime departureDate = LocalDateTime.of(2023, Month.NOVEMBER, 01, 14, 00);
	    when(tripOne.calculateEstimatedArrivalDateToTerminal(buenosAires)).thenReturn(departureDate);
	    
	    // Assert
	    assertEquals(departureDate, importOrder.departureDate());
	}

	@Test
	void testPriceOfServices_ReturnsCorrectTotalPrice() {
	    // Set Up
	    importOrder.registerService(electricity);
	    importOrder.registerService(excessStorage);
	    importOrder.registerService(weigh);
	    importOrder.registerService(washed);
	    
	    // Assert
	    assertEquals(10000.00, importOrder.priceOfServices());
	}

	@Test
	void testTravelCost_ReturnsCostForNoServices() {
	    // Set Up
	    when(maritimeCircuitOne.getPriceBetween(montevideo, buenosAires)).thenReturn(2905.00);
	    
	    // Assert
	    assertEquals(2905.00, importOrder.travelCost());
	}
}
