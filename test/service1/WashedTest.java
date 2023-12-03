package service1;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import load.Load;
import load.Tank;
import order.Order;
import service.Washed;

/**
 * Test de unidad para la clase Washed (SUT).
 * Interactua con la clase Load(DOC);
 */
class WashedTest {
	
	Washed washedA;
	Double priceA = 100.0;
	Double priceAExtra = 150.0;
	
	Washed washedB;
	Double priceB = 300.0;
	Double priceBExtra = 450.0;
	
	Load loadA = mock(Tank.class);
	Load loadB = mock(Tank.class);

	@BeforeEach
	void setUp() {
		washedA= new Washed(priceA, priceAExtra);
		washedB= new Washed(priceB, priceBExtra);
	}
		
	@Test
	void testWeighClassInitialization() {
		assertEquals(priceA, washedA.getPrice());
		assertEquals(priceAExtra, washedA.getCostPerBigLoad());
		assertEquals(priceB, washedB.getPrice());
		assertEquals(priceBExtra, washedB.getCostPerBigLoad());
		assertEquals("Washed", washedA.getName());
		assertEquals("Washed", washedB.getName());
	}
	@Test
	void testRegularPriceForGivenRegularLoad() {

		Double volumeRegular = 50.0;
		when(loadA.getVolume()).thenReturn(volumeRegular);
		assertEquals(priceA, washedA.getPriceFor(loadA));
	}
	
	@Test
	void testExtraPriceForGivenBiggerLoad() {
	
		Double biggerVolume = 100.0;
		when(loadA.getVolume()).thenReturn(biggerVolume);
		assertEquals(priceBExtra, washedB.getPriceFor(loadA));
	}
	
	@Test
	void testRegularPriceForGivenLoadWithExactSameLimitVolumeForMinimumFee() {
	
		Double limitVolume = 70.0;//el maxVolumePerMinimumFee
		when(loadA.getVolume()).thenReturn(limitVolume);
		assertEquals(priceA, washedA.getPriceFor(loadA));
	}
}
