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

// EJEMPLO

// maritimeCircuitOne 
// (Buenos Aires - Santiago | Santiago - Quito | Quito - Lima | Lima - Caracas | Caracas - Buenos Aires ) -> 4 intemerdias
// maritimeCircuitTwo
//(Buenos Aires - Santiago | Santiago - Lima | Lima - Caracas | Caracas -Buenos Aires ) -> 3 intemerdias

class FewerIntemediateTerminals {

	private FewerIntermediateTerminals fewerIntermediateTerminals;
	private ManagedTerminal buenosAires;
	private Terminal caracas;
	private MaritimeCircuit maritimeCircuitOne;
	private MaritimeCircuit maritimeCircuitTwo;

	@BeforeEach
	void setUp() {
		fewerIntermediateTerminals = new FewerIntermediateTerminals();
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
		when(santiagoQuito.getOrigin()).thenReturn(santiago);
		when(santiagoQuito.getDestiny()).thenReturn(quito);
		when(quitoLima.getOrigin()).thenReturn(quito);
		when(quitoLima.getDestiny()).thenReturn(lima);
		when(limaCaracas.getOrigin()).thenReturn(lima);
		when(limaCaracas.getDestiny()).thenReturn(caracas);
		when(caracasBuenosAires.getOrigin()).thenReturn(caracas);
		when(caracasBuenosAires.getDestiny()).thenReturn(buenosAires);
		when(santiagoLima.getOrigin()).thenReturn(buenosAires);
		when(santiagoLima.getDestiny()).thenReturn(buenosAires);
		when(maritimeCircuitOne.getStretchs()).thenReturn(
				Arrays.asList(buenosAiresSantiago, santiagoQuito, quitoLima, limaCaracas, caracasBuenosAires));
		when(maritimeCircuitTwo.getStretchs())
				.thenReturn(Arrays.asList(buenosAiresSantiago, santiagoLima, limaCaracas, caracasBuenosAires));
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
}
