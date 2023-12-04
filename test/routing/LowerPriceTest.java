package routing;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LowerPriceTest extends RoutingTest {

	private LowerPrice lowerPrice;

	@BeforeEach
	void setUp() throws Exception {
		super.setUp();
		// ------------------------------------------------------------------------------------------
		when(buenosAiresValparaiso.getPrice()).thenReturn(1040.00);
		when(valparaisoLima.getPrice()).thenReturn(2024.00);
		when(limaGuayaquil.getPrice()).thenReturn(1821.00);
		when(guayaquilBuenosAires.getPrice()).thenReturn(2192.00);
		when(montevideoBuenosAires.getPrice()).thenReturn(2905.00);
		when(limaMontevideo.getPrice()).thenReturn(1497.00);
		// ------------------------------------------------------------------------------------------
		lowerPrice = new LowerPrice();
	}

	@Test
	void testBestCircuitBetween_ReturnsExpectedCircuit_WhenUsingLowerPriceStrategy() throws Exception {
		assertEquals(maritimeCircuitOne,
				lowerPrice.bestCircuitBetween(buenosAires, lima, List.of(maritimeCircuitOne, maritimeCircuitTwo)));
	}

}
