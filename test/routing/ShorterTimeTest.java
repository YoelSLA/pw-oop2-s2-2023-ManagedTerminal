package routing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.Duration;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import maritimeCircuit.MaritimeCircuit;
import stretch.Stretch;
import terminal.ManagedTerminal;
import terminal.Terminal;

//maritimeCircuitOne 
//(Buenos Aires - Santiago (3) | Santiago - Quito (5) | Quito - Lima (7) | Lima - Caracas (9) | Caracas - Buenos Aires (7) ) -> 4 intemerdias
//maritimeCircuitTwo
//(Buenos Aires - Santiago (3) | Santiago - Lima (3) | Lima - Caracas (9) | Caracas -Buenos Aires (7) ) -> 3 intemerdias

class ShorterTimeTest {

	private ShorterTime shorterTime;
	private ManagedTerminal buenosAires;
	private Terminal caracas;
	private MaritimeCircuit maritimeCircuitOne;
	private MaritimeCircuit maritimeCircuitTwo;

	@BeforeEach
	void setUp() {
		shorterTime = new ShorterTime();
		configureMocks();
	}

	private void configureMocks() {
		buenosAires = mock(ManagedTerminal.class);
		Terminal santiago = mock(Terminal.class);
		Terminal quito = mock(Terminal.class);
		Terminal lima = mock(Terminal.class);
		caracas = mock(Terminal.class);
		Stretch buenosAiresSantiago = mock(Stretch.class);
		Stretch santiagoQuito = mock(Stretch.class);
		Stretch quitoLima = mock(Stretch.class);
		Stretch limaCaracas = mock(Stretch.class);
		Stretch caracasBuenosAires = mock(Stretch.class);
		Stretch santiagoLima = mock(Stretch.class);
		maritimeCircuitOne = mock(MaritimeCircuit.class);
		maritimeCircuitTwo = mock(MaritimeCircuit.class);

		when(buenosAiresSantiago.getOrigin()).thenReturn(buenosAires);
		when(buenosAiresSantiago.getDestiny()).thenReturn(santiago);
		when(buenosAiresSantiago.getTime()).thenReturn(Duration.ofHours(3));
		when(santiagoQuito.getOrigin()).thenReturn(santiago);
		when(santiagoQuito.getDestiny()).thenReturn(quito);
		when(santiagoQuito.getTime()).thenReturn(Duration.ofHours(5));
		when(quitoLima.getOrigin()).thenReturn(quito);
		when(quitoLima.getDestiny()).thenReturn(lima);
		when(quitoLima.getTime()).thenReturn(Duration.ofHours(7));
		when(limaCaracas.getOrigin()).thenReturn(lima);
		when(limaCaracas.getDestiny()).thenReturn(caracas);
		when(limaCaracas.getTime()).thenReturn(Duration.ofHours(9));
		when(caracasBuenosAires.getOrigin()).thenReturn(caracas);
		when(caracasBuenosAires.getDestiny()).thenReturn(buenosAires);
		when(caracasBuenosAires.getTime()).thenReturn(Duration.ofHours(7));
		when(santiagoLima.getOrigin()).thenReturn(buenosAires);
		when(santiagoLima.getDestiny()).thenReturn(buenosAires);
		when(santiagoLima.getTime()).thenReturn(Duration.ofHours(3));

		when(maritimeCircuitOne.getStretchs()).thenReturn(
				Arrays.asList(buenosAiresSantiago, santiagoQuito, quitoLima, limaCaracas, caracasBuenosAires));
		when(maritimeCircuitTwo.getStretchs())
				.thenReturn(Arrays.asList(buenosAiresSantiago, santiagoLima, limaCaracas, caracasBuenosAires));
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
