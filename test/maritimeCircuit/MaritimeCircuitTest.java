package maritimeCircuit;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import stretch.Stretch;
import terminal.ManagedTerminal;
import terminal.Terminal;

class MaritimeCircuitTest {

	private MaritimeCircuit maritimeCircuit; // SUT
	private Stretch buenosAiresMontevideo; // DOC
	private Stretch montevideoRioDeJaneiro; // DOC
	private Stretch rioDeJaneiroBuenosAires; // DOC
	private ManagedTerminal terminalBuenosAires; // DOC
	private Terminal terminalMontevideo; // DOC
	private Terminal terminalRioDeJaneiro; // DOC

	@BeforeEach
	void setUp() {
		buenosAiresMontevideo = mock(Stretch.class);
		montevideoRioDeJaneiro = mock(Stretch.class);
		rioDeJaneiroBuenosAires = mock(Stretch.class);
		terminalBuenosAires = mock(ManagedTerminal.class);
		terminalMontevideo = mock(Terminal.class);
		terminalRioDeJaneiro = mock(Terminal.class);

		maritimeCircuit = new MaritimeCircuit(
				List.of(buenosAiresMontevideo, montevideoRioDeJaneiro, rioDeJaneiroBuenosAires));
	}

	@Test
	void testAMaritimeCircuitHasATwoSections() {
		assertTrue(maritimeCircuit.areTheTerminalsThere(terminalBuenosAires, terminalMontevideo));
	}
//
//	@Test
//	void testAMaritimeCircuitCalculatesTheTotalPriceBetweenAllItsSections() {
//		assertEquals(70.00, maritimeCircuit.getPrice());
//	}
//
//	@Test
//	void AMaritimeCircuitKnowsIfItHasACertainTerminalInItsSections() {
//		assertTrue(maritimeCircuit.itHasASectionWhereItIs(terminalBuenosAires));
//	}
//
//	@Test
//	void AMaritimeCircuitKnowsIfItHasNotACertainTerminalInItsSections() {
//		assertFalse(maritimeCircuit.itHasASectionWhereItIs(terminalRioDeJaneiro));
//	}

//	@Test
//	void hola2() {
//		// Set Up
//		when(terminalBuenosAires.getName()).thenReturn("Puerto de Buenos Aires");
//		when(terminalMontevideo.getName()).thenReturn("Puerto de Montevideo");
//		when(terminalRioDeJaneiro.getName()).thenReturn("Puerto de Rio de Janeiro");
//		when(buenosAiresMontevideo.getOrigin()).thenReturn(terminalBuenosAires);
//		when(buenosAiresMontevideo.getDestiny()).thenReturn(terminalMontevideo);
//		when(montevideoRioDeJaneiro.getOrigin()).thenReturn(terminalMontevideo);
//		when(montevideoRioDeJaneiro.getDestiny()).thenReturn(terminalRioDeJaneiro);
//		when(rioDeJaneiroBuenosAires.getOrigin()).thenReturn(terminalRioDeJaneiro);
//		when(rioDeJaneiroBuenosAires.getDestiny()).thenReturn(terminalBuenosAires);
//		// Assert
//		assertTrue(maritimeCircuit.exists(terminalBuenosAires));
//	}

}
