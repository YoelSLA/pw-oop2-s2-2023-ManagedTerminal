package service1;

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
	
	ExcessStorage storageA;
	Double priceA = 100.0;
	Integer hoursA = 2;
	
	ExcessStorage storageB;
	Double priceB = 300.0;
	Integer hoursB = 48;
	
	Load loadA;
	Load loadB;
	
	@BeforeEach
	void setUp() {
		storageA= new ExcessStorage(priceA, hoursA);
		storageB= new ExcessStorage(priceB, hoursB);
	}
	
	@Test
	void testExcessStorageClassInitialization() {
		assertEquals(priceA, storageA.getPrice());
		assertEquals("ExcessStorage", storageA.getName());
		assertEquals(hoursA, storageA.getHoursOfStay());
		assertEquals(priceB, storageB.getPrice());
		assertEquals("ExcessStorage", storageB.getName());
		assertEquals(hoursB, storageB.getHoursOfStay());
	}
	
	@Test
	void testPriceForExcessStorageForAGivenLoadOnTime() {
		loadA = mock(Tank.class);
		Double expectedPrice = priceA * hoursA;
		assertEquals(expectedPrice, storageA.getPriceFor(loadA));
	}
	
	@Test
	void testPriceForExcessStorageForAGivenLoadDelayed() {
		loadB = mock(Tank.class);
		Double expectedPrice = priceB * hoursB;
		assertEquals(expectedPrice, storageB.getPriceFor(loadB));
	}

}
