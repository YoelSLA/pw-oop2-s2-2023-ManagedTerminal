package routing;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import maritimeCircuit.MaritimeCircuit;
import terminal.Terminal;

//maritimeCircuitOne 
//(Buenos Aires - Santiago (100) | Santiago - Quito (200) | Quito - Lima (300) | Lima - Caracas (400) | Caracas - Buenos Aires (500) ) -> 4 intemerdias
//maritimeCircuitTwo
//(Buenos Aires - Santiago (100) | Santiago - Lima | Lima - Caracas (400) | Caracas -Buenos Aires (500) ) -> 3 intemerdias

class LowerPriceTest extends RoutingTest {

	private LowerPrice lowerPrice;

	@BeforeEach
	void setUp() {
		lowerPrice = new LowerPrice();
		configureMocks();
	}

	@Override
	protected void configureMocks() {
		super.configureMocks();
		when(buenosAiresSantiago.getPrice()).thenReturn(100.0);
		when(santiagoQuito.getPrice()).thenReturn(200.0);
		when(quitoLima.getPrice()).thenReturn(300.0);
		when(limaCaracas.getPrice()).thenReturn(400.0);
		when(caracasBuenosAires.getPrice()).thenReturn(500.0);
		when(caracasBuenosAires.getPrice()).thenReturn(1000.0);
	}

	@Test
	void testBestCircuitWithLowerPrice() {
		assertEquals(maritimeCircuitOne, lowerPrice.bestCircuitBetween(buenosAires, caracas,
				Arrays.asList(maritimeCircuitOne, maritimeCircuitTwo)));
	}

	@Test
	void testCannotFindBestCircuitBecauseDestinyTerminalNotInMaritimeCircuits() {
		assertThrows(RuntimeException.class, () -> {
			lowerPrice.bestCircuitBetween(buenosAires, mock(Terminal.class),
					Arrays.asList(maritimeCircuitOne, maritimeCircuitTwo));
		});
	}

	@Test
	void testCannotFindBestCircuitBecauseTheOriginTerminalHasNotCircuits() {
		assertThrows(RuntimeException.class, () -> {
			lowerPrice.bestCircuitBetween(buenosAires, caracas, Arrays.asList(mock(MaritimeCircuit.class)));
		});
	}

}
