package routing;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import maritimeCircuit.MaritimeCircuit;
import terminal.Terminal;

// EJEMPLO

// maritimeCircuitOne 
// (Buenos Aires - Santiago | Santiago - Quito | Quito - Lima | Lima - Caracas | Caracas - Buenos Aires ) -> 4 intemerdias
// maritimeCircuitTwo
//(Buenos Aires - Santiago | Santiago - Lima | Lima - Caracas | Caracas -Buenos Aires ) -> 3 intemerdias

class FewerIntemediateTerminals extends RoutingTest {

	private FewerIntermediateTerminals fewerIntermediateTerminals;

	@BeforeEach
	void setUp() {
		fewerIntermediateTerminals = new FewerIntermediateTerminals();
		configureMocks();
	}

	@Test
	void testBestCircuitWithFewestIntermediates() {
		assertEquals(maritimeCircuitTwo, fewerIntermediateTerminals.bestCircuitBetween(buenosAires, caracas,
				Arrays.asList(maritimeCircuitOne, maritimeCircuitTwo)));
	}

	@Test
	void testCannotFindBestCircuitBecauseDestinyTerminalNotInMaritimeCircuits() {
		assertThrows(RuntimeException.class, () -> {
			fewerIntermediateTerminals.bestCircuitBetween(buenosAires, mock(Terminal.class),
					Arrays.asList(maritimeCircuitOne, maritimeCircuitTwo));
		});
	}

	@Test
	void testCannotFindBestCircuitBecauseTheOriginTerminalHasNotCircuits() {
		assertThrows(RuntimeException.class, () -> {
			fewerIntermediateTerminals.bestCircuitBetween(buenosAires, caracas,
					Arrays.asList(mock(MaritimeCircuit.class)));
		});
	}
}
