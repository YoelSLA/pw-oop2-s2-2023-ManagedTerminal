package order;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import client.Shipper;
import driver.Driver;
import load.Dry;
import trip.Trip;
import truck.Truck;

import static org.mockito.Mockito.mock;

class ExportOrderTest {
	private ExportOrder exportOrder;
	private Shipper shipper;
	private Driver driver;
	private Dry dryLoad;
	private Trip trip;
	private Truck truck;
	
	@BeforeEach
	void setUp() {
		shipper = mock(Shipper.class);
		driver = mock(Driver.class);
		dryLoad = mock(Dry.class);
		trip = mock(Trip.class);
		truck = mock(Truck.class);
		
		exportOrder = new ExportOrder(driver, dryLoad, shipper, trip, truck);
	}
	
	@Test
	void getShipper() {
		assertEquals(shipper, exportOrder.getShipper());
	}
	
	@Test
	void getDriver() {
		assertEquals(driver, exportOrder.getDriver());
	}
	
	@Test
	void getLoad() {
		assertEquals(dryLoad, exportOrder.getLoad());
	}
	
	@Test
	void getTrip() {
		assertEquals(trip, exportOrder.getTrip());
	}
	
	@Test
	void getTruck() {
		assertEquals(truck, exportOrder.getTruck());
	}
	
}
