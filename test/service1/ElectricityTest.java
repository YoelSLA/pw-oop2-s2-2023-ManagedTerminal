package service1;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import load.Load;
import load.Reefer;
import order.Order;

import static org.mockito.Mockito.*;

import service.Electricity;

/**
 * Test de unidad para la clase Electricity (SUT).
 * Interactua con la clase Load(DOC);
 */
class ElectricityTest {

	Electricity electricityA;
	Electricity electricityB;
	
	Reefer loadA;
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
		assertEquals(startDateA, electricityA.getStartConnection());
		assertEquals(endDateA, electricityA.getEndConnection());
		assertEquals("Electricity", electricityA.getName());
		assertEquals(priceB, electricityB.getPrice());
		assertEquals(startDateB, electricityB.getStartConnection());
		assertEquals(endDateB, electricityB.getEndConnection());
		assertEquals("Electricity", electricityB.getName());
	}
	
	@Test
	void testPriceForConsumptionForAGivenLoad() {
		loadA = mock(Reefer.class);
		int energyConsumptionA = 2;
		when(loadA.getConsumptionkWh()).thenReturn((double) energyConsumptionA);
		Double expectedPrice = hoursA * energyConsumptionA * electricityA.getPrice();
		assertEquals(expectedPrice, electricityA.getPriceFor(loadA));
	}
}
