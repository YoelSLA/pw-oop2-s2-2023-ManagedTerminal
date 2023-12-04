package services;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.Month;

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

	private Electricity electricityA;
	
	private Reefer loadA;
	private Double priceA = 100.0;
	private LocalDateTime startDateA = LocalDateTime.of(2023,11,9,9,40);
	private Double energyConsumptionA = 2.0;
	
	@BeforeEach
	void setUp() {
		loadA = mock(Reefer.class);
		when(loadA.getConsumptionkWh()).thenReturn(energyConsumptionA);
		electricityA= new Electricity(priceA, startDateA);
	}


	@Test
	void testGetPrice() {
		assertEquals(priceA, electricityA.getPrice());
	}
	
	@Test
	void testGetStartConnection() {
		assertEquals(startDateA, electricityA.getStartConnection());
	}
	
	@Test
	void testGetName() {
		assertEquals("Electricity", electricityA.getName());
	}
	
	@Test
	void testPriceForConsumptionForAGivenLoad() {	
		//set Up
		electricityA.setEndConnection(LocalDateTime.of(2023,Month.NOVEMBER,10,9,40));
		// assert
		assertEquals(4800.0, electricityA.getPriceFor(loadA));
		verify(loadA, times(1)).getConsumptionkWh();
	}
}
