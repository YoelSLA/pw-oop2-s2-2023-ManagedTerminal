package routing;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import maritimeCircuit.MaritimeCircuit;
import stretch.Stretch;
import terminal.ManagedTerminal;
import terminal.Terminal;

//maritimeCircuitOne 
//(Buenos Aires - Santiago (100) | Santiago - Quito (200) | Quito - Lima (300) | Lima - Caracas (400) | Caracas - Buenos Aires (500) ) -> 4 intemerdias
//maritimeCircuitTwo
//(Buenos Aires - Santiago (100) | Santiago - Lima | Lima - Caracas (400) | Caracas -Buenos Aires (500) ) -> 3 intemerdias

class LowerPriceTest {

	private LowerPrice lowerPrice;
	private ManagedTerminal buenosAires;
	private Terminal caracas;
	private MaritimeCircuit maritimeCircuitOne;
	private MaritimeCircuit maritimeCircuitTwo;

	@BeforeEach
	void setUp() {
		lowerPrice = new LowerPrice();
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
		when(buenosAiresSantiago.getPrice()).thenReturn(100.0);
		when(santiagoQuito.getOrigin()).thenReturn(santiago);
		when(santiagoQuito.getDestiny()).thenReturn(quito);
		when(santiagoQuito.getPrice()).thenReturn(200.0);
		when(quitoLima.getOrigin()).thenReturn(quito);
		when(quitoLima.getDestiny()).thenReturn(lima);
		when(quitoLima.getPrice()).thenReturn(300.0);
		when(limaCaracas.getOrigin()).thenReturn(lima);
		when(limaCaracas.getDestiny()).thenReturn(caracas);
		when(limaCaracas.getPrice()).thenReturn(400.0);
		when(caracasBuenosAires.getOrigin()).thenReturn(caracas);
		when(caracasBuenosAires.getDestiny()).thenReturn(buenosAires);
		when(caracasBuenosAires.getPrice()).thenReturn(500.0);
		when(santiagoLima.getOrigin()).thenReturn(buenosAires);
		when(santiagoLima.getDestiny()).thenReturn(buenosAires);
		when(caracasBuenosAires.getPrice()).thenReturn(1000.0);

		when(maritimeCircuitOne.getStretchs()).thenReturn(
				Arrays.asList(buenosAiresSantiago, santiagoQuito, quitoLima, limaCaracas, caracasBuenosAires));
		when(maritimeCircuitTwo.getStretchs())
				.thenReturn(Arrays.asList(buenosAiresSantiago, santiagoLima, limaCaracas, caracasBuenosAires));
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
