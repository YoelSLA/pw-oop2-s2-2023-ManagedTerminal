package truckTransportCompany;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import driver.Driver;
import truck.Truck;

class TruckTransportCompanyTest {

	private Driver yoel; // DOC
	private Truck volvoTruck; // DOC
	private TruckTransportCompany transporteVesprini; // SUT

	@BeforeEach
	void setUp() {
		yoel = mock(Driver.class);
		volvoTruck = mock(Truck.class);
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