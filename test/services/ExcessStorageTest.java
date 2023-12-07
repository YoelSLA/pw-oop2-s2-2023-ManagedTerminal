package services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import load.Load;
import load.Tank;
import order.Order;
import service.ExcessStorage;

/**
 * Test de unidad para la clase ExcessStorage (SUT).
 * Interactua con la clase Load (DOC);
 */
class ExcessStorageTest {
	
	private ExcessStorage storageA;
	private Double priceA = 100.0;
	private Integer hoursA = 2;
	
	private ExcessStorage storageB;
	private Double priceB = 300.0;
	private Integer hoursB = 48;
	
	private Load loadA;
	private Load loadB;
	
	@BeforeEach
	void setUp() {
		storageA= new ExcessStorage(priceA, hoursA);
		storageB= new ExcessStorage(priceB, hoursB);
		loadA = mock(Tank.class);
		loadB = mock(Tank.class);
	}
	
	@Test
	void testGetPrice() {
		assertEquals(priceA, storageA.getPrice());
		assertEquals(priceB, storageB.getPrice());
	}
	
	@Test
	void testGetHoursOfStay() {
		assertEquals(hoursA, storageA.getHoursOfStay());
		assertEquals(hoursB, storageB.getHoursOfStay());		
	}
	
	@Test
	void testGetName() {
		assertEquals("ExcessStorage", storageA.getName());		
	}
	
	@Test
	void testPriceForExcessStorageForAGivenLoadOnTime() {
		
		Double expectedPrice = priceA * hoursA;
		assertEquals(expectedPrice, storageA.getPriceFor(loadA));
	}
	
	@Test
	void testPriceForExcessStorageForAGivenLoadDelayed() {
		
		Double expectedPrice = priceB * hoursB;
		assertEquals(expectedPrice, storageB.getPriceFor(loadB));
	}

}
