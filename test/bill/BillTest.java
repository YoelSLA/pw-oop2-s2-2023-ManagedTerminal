package bill;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

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
	
	
	private Bill billA = new Bill(orderA);
	private Bill billB = new Bill(orderB);
		
	@Test
	void testInitializationBillClass() {
		assertEquals(orderA, billA.getOrderToProcess());
		assertEquals(0.0, billA.getTotalAmountToPay());
		assertEquals(orderB, billB.getOrderToProcess());
		assertEquals(0.0, billB.getTotalAmountToPay());
	}
	
	@Test
	void testTotalAmountPerServices() {
		Service service1= mock(Service.class);
		Service service2= mock(Service.class);
		when(orderA.getServices()).thenReturn(List.of(service1,service2));
		when(service1.getPriceFor(orderA)).thenReturn(1000.0);
		when(service2.getPriceFor(orderA)).thenReturn(9000.0);
		assertEquals(10000, billA.getTotalAmountPerServices());
	}
	
	@Test
	void testTotalAmountPerTrip() {
		// costoTripA = 100;
		// TODO: como calcular la sumatoria de secciones para un viaje con inicio y fin??
		//assertEquals(100, billA.getTotalAmountPerTrip());
	}
	
	

}
