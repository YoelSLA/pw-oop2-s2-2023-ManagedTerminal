package service1;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import load.Load;
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
	
	Order orderA;

	@BeforeEach
	void setUp() {
		weighA= new Weigh(priceA);
		weighB= new Weigh(priceB);
	}
		
	@Test
	void testWeighClassInitialization() {
		assertEquals(priceA, weighA.getPrice());
		assertEquals(priceB, weighB.getPrice());
		assertEquals("Weigh", weighA.getName());
		assertEquals("Weigh", weighB.getName());
	}
	
	@Test
	void testWeighOnAGivenLoadReturnItsOwnWeight() {
		loadA = mock(Load.class);
		Double weightExpectedLoadA = 54.0;
		when(loadA.getWeight()).thenReturn(weightExpectedLoadA);
		assertEquals(weightExpectedLoadA, weighA.weighOn(loadA));
	}
	
	@Test
	void testWeighServiceForAGivenLoad() {
		orderA = mock(Order.class);
		Double priceExpectedLoadA = priceA;
		assertEquals(priceExpectedLoadA, weighA.getPriceFor(orderA));
	}

}
