package services;

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
	
	private Washed washedA;
	private Double priceA = 100.0;
	private Double priceAExtra = 150.0;
	
	private Washed washedB;
	private Double priceB = 300.0;
	private Double priceBExtra = 450.0;
	
	private Load loadA = mock(Tank.class);
	private Load loadB = mock(Tank.class);
	private Load loadC = mock(Tank.class);

	Double volumeRegular = 50.0;
	Double biggerVolume = 100.0;
	Double limitVolume = 70.0;//el maxVolumePerMinimumFee
	
	@BeforeEach
	void setUp() {
		
		washedA= new Washed(priceA, priceAExtra);
		washedB= new Washed(priceB, priceBExtra);
		
		when(loadA.getVolume()).thenReturn(volumeRegular);
		when(loadB.getVolume()).thenReturn(biggerVolume);
		when(loadC.getVolume()).thenReturn(limitVolume);
	}
		
	@Test
	void testWeighClassInitialization() {
		assertEquals(priceA, washedA.getPrice());
		assertEquals(priceB, washedB.getPrice());
	}
	
	@Test
	void getOtherPrice() {
		assertEquals(priceAExtra, washedA.getOtherPrice());
		assertEquals(priceBExtra, washedB.getOtherPrice());		
	}
	
	@Test
	void getName() {
		assertEquals("Washed", washedA.getName());		
	}
	
	@Test
	void testRegularPriceForGivenBigLoad() {

		assertEquals(priceAExtra, washedA.getPriceFor(loadA));
	}
	
	@Test
	void testExtraPriceForGivenSmallLoad() {
		
		assertEquals(priceB, washedB.getPriceFor(loadB));
	}
	
	@Test
	void testRegularPriceForGivenLoadWithExactSameLimitVolume() {
		
		assertEquals(priceAExtra, washedA.getPriceFor(loadC));
	}
}
