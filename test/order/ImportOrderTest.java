package order;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import client.Consignee;
import load.Dry;
import load.Load;
import service.*;
import trip.Trip;

import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

class ImportOrderTest {
	private ImportOrder importOrder;
	private Consignee consignee;
	private Electricity electricityService;
	private Weigh weighService;
	private List<Service> services;
	private Dry load;
	private Trip trip;
	
	@BeforeEach
	void setUp() {
		consignee = mock(Consignee.class);
		electricityService = mock(Electricity.class);
		weighService = mock(Weigh.class);
		load = mock(Dry.class);
		trip = mock(Trip.class);
		services = new ArrayList<>();
		
		services.add(electricityService);
		services.add(electricityService);
		
		importOrder = new ImportOrder(services, load, consignee, trip);
	}

	@Test
	void getCosignee() {
		assertEquals(consignee, importOrder.getConsignee());
	}
	
	@Test
	void getServices() {
		assertEquals(services, importOrder.getServices());
	}
	
	@Test
	void getLoad() {
		assertEquals(load, importOrder.getLoad());
	}
	
	@Test
	void getTrip() {
		assertEquals(trip, importOrder.getTrip());
	}

}
