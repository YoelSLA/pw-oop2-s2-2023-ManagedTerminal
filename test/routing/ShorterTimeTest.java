package routing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.Duration;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import terminal.Terminal;

//maritimeCircuitOne 
//(Buenos Aires - Santiago (3) | Santiago - Quito (5) | Quito - Lima (7) | Lima - Caracas (9) | Caracas - Buenos Aires (7) ) -> 4 intemerdias
//maritimeCircuitTwo
//(Buenos Aires - Santiago (3) | Santiago - Lima (3) | Lima - Caracas (9) | Caracas -Buenos Aires (7) ) -> 3 intemerdias

class ShorterTimeTest extends RoutingTest {

	private ShorterTime shorterTime;

	@BeforeEach
	void setUp() {
		shorterTime = new ShorterTime();
		configureMocks();
	}

	@Override
	protected void configureMocks() {
		super.configureMocks();
		when(buenosAiresSantiago.getTime()).thenReturn(Duration.ofHours(3));
		when(santiagoQuito.getTime()).thenReturn(Duration.ofHours(5));
		when(quitoLima.getTime()).thenReturn(Duration.ofHours(7));
		when(limaCaracas.getTime()).thenReturn(Duration.ofHours(9));
		when(caracasBuenosAires.getTime()).thenReturn(Duration.ofHours(7));
		when(santiagoLima.getTime()).thenReturn(Duration.ofHours(3));
	}

	@Test
	void testBestCircuitWithLowerPrice() {
		assertEquals(maritimeCircuitTwo, shorterTime.bestCircuitBetween(buenosAires, caracas,
				Arrays.asList(maritimeCircuitOne, maritimeCircuitTwo)));
	}

	@Test
	void testCannotFindBestCircuitBecauseDestinyTerminalNotInMaritimeCircuits() {
		assertThrows(RuntimeException.class, () -> {
			shorterTime.bestCircuitBetween(buenosAires, mock(Terminal.class),
					Arrays.asList(maritimeCircuitOne, maritimeCircuitTwo));
		});
	}

	@Test
	void testCannotFindBestCircuitBecauseTheOriginTerminalHasNotCircuits() {
		assertThrows(RuntimeException.class, () -> {
			shorterTime.bestCircuitBetween(buenosAires, caracas, Arrays.asList());
		});
	}

}
