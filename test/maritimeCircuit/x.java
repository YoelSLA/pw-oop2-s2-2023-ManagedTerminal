package maritimeCircuit;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import stretch.Stretch;
import terminal.ManagedTerminal;
import terminal.Terminal;

class x {

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

		when(buenosAiresMontevideo.getOrigin()).thenReturn(terminalBuenosAires);
		when(buenosAiresMontevideo.getDestiny()).thenReturn(terminalMontevideo);
		when(montevideoRioDeJaneiro.getOrigin()).thenReturn(terminalMontevideo);
		when(montevideoRioDeJaneiro.getDestiny()).thenReturn(terminalRioDeJaneiro);
		when(rioDeJaneiroBuenosAires.getOrigin()).thenReturn(terminalRioDeJaneiro);
		when(rioDeJaneiroBuenosAires.getDestiny()).thenReturn(terminalBuenosAires);

		maritimeCircuit = new MaritimeCircuit(
				List.of(buenosAiresMontevideo, montevideoRioDeJaneiro, rioDeJaneiroBuenosAires));
	}

	@Test
	void test1() {
		assertTrue(maritimeCircuit.areTheTerminalsThere(terminalBuenosAires, terminalRioDeJaneiro));
	}

	@Test
	void test2() {
		assertFalse(maritimeCircuit.areTheTerminalsThere(terminalMontevideo, terminalBuenosAires));
	}
}
