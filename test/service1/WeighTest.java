package service1;

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
	
	Weigh weighA;
	Double priceA = 100.0;
	
	Weigh weighB;
	Double priceB = 300.0;
	
	Load loadA;

	@BeforeEach
	void setUp() {
		weighA= new Weigh(priceA);
		weighB= new Weigh(priceB);
	}
		
	@Test
	void testWeighClassInitialization() {
		assertEquals(priceA, weighA.getPrice());
		assertEquals("Weigh", weighA.getName());
		assertEquals(priceB, weighB.getPrice());
		assertEquals("Weigh", weighB.getName());
	}
	
	@Test
	void testWeighServiceForAGivenLoad() {
		loadA = mock(Tank.class);
		Double priceExpectedLoadA = priceA;
		assertEquals(priceExpectedLoadA, weighA.getPriceFor(loadA));
	}

}
