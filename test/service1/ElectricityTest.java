package service1;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import load.Load;
import order.Order;

import static org.mockito.Mockito.*;

import service.Electricity;

/**
 * Test de unidad para la clase Electricity (SUT).
 * Interactua con la clase Load y Order(DOCs);
 */
class ElectricityTest {

	Electricity electricityA;
	Electricity electricityB;
	Order orderA;
	Load loadA;
	Double priceA = 100.0;
	int hoursA = 10;
	LocalDateTime startDateA = LocalDateTime.of(2023,11,9,9,40);
	LocalDateTime endDateA = startDateA.plusHours(hoursA);
	Double priceB = 300.0;
	LocalDateTime startDateB = LocalDateTime.of(2023,11,10,10,40);
	LocalDateTime endDateB = startDateB.plusMinutes(40);
	
	@BeforeEach
	void setUp() {
		electricityA= new Electricity(priceA, startDateA, endDateA);
		electricityB= new Electricity(priceB, startDateB, endDateB);
	}


	@Test
	void testElecticityClassInitialization() {
		assertEquals(priceA, electricityA.getPrice());
		assertEquals(priceB, electricityB.getPrice());
		assertEquals(startDateA, electricityA.getStartConnection());
		assertEquals(startDateB, electricityB.getStartConnection());
		assertEquals(endDateA, electricityA.getEndConnection());
		assertEquals(endDateB, electricityB.getEndConnection());
		assertEquals("Electricity", electricityA.getName());
		assertEquals("Electricity", electricityB.getName());
	}
	
	@Test
	void testPriceForConsumptionForAGivenLoad() {
		orderA = mock(Order.class);
		loadA = mock(Load.class);
		int energyConsumptionA = 2;
		when(orderA.getLoad()).thenReturn(loadA);
		when(loadA.getEnergyConsumption()).thenReturn(energyConsumptionA);
		Double expectedPrice = hoursA * energyConsumptionA * electricityA.getPrice();
		assertEquals(expectedPrice, electricityA.getPriceFor(orderA));
	}
}
