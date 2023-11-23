package bill;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import order.Order;
import service.Service;


/**
 * Test de la clase Bill (factura) (SUT)
 * Interactua con clases: Order, service (DOCs)
 * 
 * @author Gabriela Fascetta
 * */
class BillTest {
	
	private Order orderA= mock(Order.class);
	private Order orderB= mock(Order.class);
	Service service1= mock(Service.class);
	Service service2= mock(Service.class);
	Double priceTripA = 100.0;
	
	private Bill billA = new Bill(orderA);
	private Bill billB = new Bill(orderB);
	
	@BeforeEach
	void setUp() {
		
		when(service1.getPriceFor(orderA)).thenReturn(1000.0);
		when(service2.getPriceFor(orderA)).thenReturn(9000.0);
		
	}
		
	@Test
	void testInitializationBillClass() {
		assertEquals(orderA, billA.getOrderToProcess());
		assertEquals(0.0, billA.getTotalAmountToPay());
		assertEquals(orderB, billB.getOrderToProcess());
		assertEquals(0.0, billB.getTotalAmountToPay());
	}
	
	@Test
	void testTotalAmountPerServices() {
		when(orderA.getServices()).thenReturn(List.of(service1,service2));
		
		assertEquals(10000, billA.getTotalAmountPerServices());
	}
	
	@Test
	void testTotalAmountPerTrip() {
		when(orderA.getTripCost()).thenReturn(priceTripA);
		
		assertEquals(priceTripA, billA.getTotalAmountPerTrip());
	}
	
	@Test
	void testGetTotalAmountToPay() {
		when(orderA.getServices()).thenReturn(List.of(service1,service2));
		when(orderA.getTripCost()).thenReturn(priceTripA);
		
		assertEquals(priceTripA + 10000, billA.getTotalAmountToPay());
	}

}
