package truckTransportCompany;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import driver.Driver;
import truck.Truck;

class TruckTransportCompanyTest {

	private Driver yoel; // DOC
	private Truck volvoTruck; // DOC
	private TruckTransportCompany truckTransportCompany; // SUT
	
	@BeforeEach
	void setUp() {
		
		yoel = mock(Driver.class);
		volvoTruck = mock(Truck.class);
		
		truckTransportCompany = new TruckTransportCompany("42341174", "Transport");
	}
	
	@Test
	void testATruckTransportCompanyCreation() {
		
		assertEquals("42341174", truckTransportCompany.getCuit());
		assertEquals(0, truckTransportCompany.getDrivers().size());
		assertEquals("Transport", truckTransportCompany.getName());
		assertEquals(0, truckTransportCompany.getTrucks().size());
	}
	
	@Test
	void testATruckTransportCompanyHasADriver() {

		truckTransportCompany.addDriver(yoel);
		assertEquals(1, truckTransportCompany.getDrivers().size());
	
	}
	
	@Test
	void testATruckTransportCompanyHasATruck() {

		truckTransportCompany.addTruck(volvoTruck);
		assertEquals(1, truckTransportCompany.getTrucks().size());
	}

}