package truckTransportCompany;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import driver.Driver;
import truck.Truck;

class TruckTransportCompanyTest {

	@Mock private Driver yoel; // DOC
	@Mock private Truck volvoTruck; // DOC
	 private TruckTransportCompany transporteVesprini; // SUT
	
	@BeforeEach
	void setUp() {
		transporteVesprini = new TruckTransportCompany("24678729716", "Transporte Vesprini");
	}
	
	@Test
	void testATruckTransportCompanyIsCreated() {
		// Assert
		assertEquals("24678729716", transporteVesprini.getCuit());
		assertEquals(0, transporteVesprini.getDrivers().size());
		assertEquals("Transporte Vesprini", transporteVesprini.getName());
		assertEquals(0, transporteVesprini.getTrucks().size());
		
	}
	
	@Test
	void testATruckTransportCompanyRegistersADriver() {
		// Exercise
		transporteVesprini.registerDriver(yoel);
		// Assert
		assertEquals(1, transporteVesprini.getDrivers().size());
		assertTrue(transporteVesprini.getDrivers().contains(yoel));
	}
	
	@Test
	void testATruckTransportCompanyRegistersATruck() {
		// Exercise
		transporteVesprini.registerTruck(volvoTruck);
		// Assert
		assertEquals(1, transporteVesprini.getTrucks().size());
		assertTrue(transporteVesprini.getTrucks().contains(volvoTruck));
	}

}