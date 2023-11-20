package service1;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import order.Order;
import service.Washed;

/**
 * Test de unidad para la clase Washed (SUT).
 * Interactua con la clase Order(DOC);
 * @author Gabriela Fascetta
 */
class WashedTest {
	
	Washed washedA;
	Double priceA = 100.0;
	Double priceAExtra = 150.0;
	
	Washed washedB;
	Double priceB = 300.0;
	Double priceBExtra = 450.0;
	
	Order orderA;

	@BeforeEach
	void setUp() {
		washedA= new Washed(priceA, priceAExtra);
		washedB= new Washed(priceB, priceBExtra);
	}
		
	@Test
	void testWeighClassInitialization() {
		assertEquals(priceA, washedA.getPrice());
		assertEquals(priceAExtra, washedA.getBigVolumePrice());
		assertEquals(priceB, washedB.getPrice());
		assertEquals(priceBExtra, washedB.getBigVolumePrice());
	}
	@Test
	void testRegularPriceForGivenOrdenWithSomeRegularLoad() {
		orderA = mock(Order.class);
		Double volumeRegular = 50.0;
		when(orderA.getLoadVolume()).thenReturn(volumeRegular);
		assertEquals(priceA, washedA.getPriceFor(orderA));
	}
	
	@Test
	void testExtraPriceForGivenOrdenWithSomeBiggerLoad() {
		orderA = mock(Order.class);
		Double biggerVolume = 100.0;
		when(orderA.getLoadVolume()).thenReturn(biggerVolume);
		assertEquals(priceAExtra, washedA.getPriceFor(orderA));
	}
	
	@Test
	void testRegularPriceForGivenOrdenWithLoadWithExactSameLimitVolumeForMinimumFee() {
		orderA = mock(Order.class);
		Double limitVolume = 70.0;//debe coincidir con el maxVolumePerMinimumFee
		when(orderA.getLoadVolume()).thenReturn(limitVolume);
		assertEquals(priceA, washedA.getPriceFor(orderA));
	}
}
