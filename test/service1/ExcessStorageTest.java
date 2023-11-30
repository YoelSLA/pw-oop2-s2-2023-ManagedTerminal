package service1;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import order.Order;
import service.ExcessStorage;

/**
 * Test de unidad para la clase ExcessStorage (SUT).
 * Interactua con la clase Order (DOC);
 */
class ExcessStorageTest {
	
	ExcessStorage storageA;
	Double priceA = 100.0;
	
	ExcessStorage storageB;
	Double priceB = 300.0;
	
	Order orderOnTime;
	Order orderDelayed;
	
	@BeforeEach
	void setUp() {
		storageA= new ExcessStorage(priceA);
		storageB= new ExcessStorage(priceB);
	}
	
	@Test
	void testExcessStorageClassInitialization() {
		assertEquals(priceA, storageA.getPrice());
		assertEquals(priceB, storageB.getPrice());
		assertEquals("ExcessStorage", storageA.getName());
		assertEquals("ExcessStorage", storageB.getName());
	}
	
	@Test
	void testPriceForExcessStorageForAGivenOrdenWithLoadOnTime() {
		orderOnTime = mock(Order.class);
		LocalDateTime dateA = LocalDateTime.now().minusHours(4);
		when(orderOnTime.getDateTruck()).thenReturn(dateA);
		Double expectedPrice = 0.0;
		assertEquals(expectedPrice, storageA.getPriceFor(orderOnTime));
	}
	
	@Test
	void testPriceForExcessStorageForAGivenOrdenWithLoadDelayed() {
		orderDelayed = mock(Order.class);
		LocalDateTime dateA = LocalDateTime.now().minusDays(2);
		when(orderDelayed.getDateTruck()).thenReturn(dateA);
		Double expectedPrice = priceA * 2;
		assertEquals(expectedPrice, storageA.getPriceFor(orderDelayed));
	}

}
