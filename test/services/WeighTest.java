package services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import load.Load;
import load.Tank;
import order.Order;
import service.Weigh;


/**
 * Test de unidad para la clase Weigh (SUT).
 * Interactua con la clase Load (DOC);
 */
class WeighTest {
	
	private Weigh weighA;
	private Double priceA = 100.0;
	
	private Weigh weighB;
	private Double priceB = 300.0;
	
	private Load loadA;
	
	private Double priceExpectedLoadA = priceA;

	@BeforeEach
	void setUp() {
		weighA= new Weigh(priceA);
		weighB= new Weigh(priceB);
		
		loadA = mock(Tank.class);
	}
		
	@Test
	void testGetPrice() {
		assertEquals(priceA, weighA.getPrice());
		assertEquals(priceB, weighB.getPrice());
	}
	
	@Test
	void testGetName() {
		assertEquals("Weigh", weighA.getName());		
	}
	
	@Test
	void testWeighServiceForAGivenLoad() {
		
		assertEquals(priceExpectedLoadA, weighA.getPriceFor(loadA));
	}

}
