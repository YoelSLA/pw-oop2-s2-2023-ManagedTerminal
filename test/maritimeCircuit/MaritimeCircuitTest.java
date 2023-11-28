package maritimeCircuit;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import stretch.Stretch;
import terminal.ManagedTerminal;
import terminal.Terminal;

class MaritimeCircuitTest {

	ManagedTerminal buenosAires = mock(ManagedTerminal.class);
//-------------------------------------------------------------
	Terminal montevideo = mock(Terminal.class);
	Terminal rioDeJaneiro = mock(Terminal.class);
//-------------------------------------------------------------
	private Stretch buenosAiresMontevideo;
	private Stretch montevideoRioDeJaniero;
	private Stretch rioDeJaneiroBuenosAires;
//-------------------------------------------------------------
	private MaritimeCircuit circuitBuenosAiresRioDeJaniero; // SUT

	@BeforeEach
	void setUp() {
		// MANAGED TERMINAL
		buenosAires = mock(ManagedTerminal.class);
		when(buenosAires.getName()).thenReturn("Puerto de Buenos Aires");
//-------------------------------------------------------------		
		// TERMINAL
		montevideo = mock(Terminal.class);
		when(montevideo.getName()).thenReturn("Puerto de Montevideo");

		rioDeJaneiro = mock(Terminal.class);
		when(rioDeJaneiro.getName()).thenReturn("Puerto de Rio de Janeiro");
//-------------------------------------------------------------		
		// STRETCH
		buenosAiresMontevideo = mock(Stretch.class);
		when(buenosAiresMontevideo.getOrigin()).thenReturn(buenosAires);
		when(buenosAiresMontevideo.getDestiny()).thenReturn(montevideo);
		when(buenosAiresMontevideo.getPrice()).thenReturn(500.0);
		when(buenosAiresMontevideo.getTime()).thenReturn(Duration.ofHours(3));

		montevideoRioDeJaniero = mock(Stretch.class);
		when(montevideoRioDeJaniero.getOrigin()).thenReturn(montevideo);
		when(montevideoRioDeJaniero.getDestiny()).thenReturn(rioDeJaneiro);
		when(montevideoRioDeJaniero.getPrice()).thenReturn(800.0);
		when(montevideoRioDeJaniero.getTime()).thenReturn(Duration.ofHours(8));

		rioDeJaneiroBuenosAires = mock(Stretch.class);
		when(rioDeJaneiroBuenosAires.getOrigin()).thenReturn(rioDeJaneiro);
		when(rioDeJaneiroBuenosAires.getDestiny()).thenReturn(buenosAires);
		when(rioDeJaneiroBuenosAires.getPrice()).thenReturn(1500.0);
		when(rioDeJaneiroBuenosAires.getTime()).thenReturn(Duration.ofHours(16));
//-------------------------------------------------------------		
		// MARITIME CIRCUIT
		circuitBuenosAiresRioDeJaniero = new MaritimeCircuit(
				List.of(buenosAiresMontevideo, montevideoRioDeJaniero, rioDeJaneiroBuenosAires));
	}

	@Test
	void getStretchs_ShouldReturnCorrectStretchs_ForCircuitBuenosAiresRioDeJaneiro() {
		assertEquals(List.of(buenosAiresMontevideo, montevideoRioDeJaniero, rioDeJaneiroBuenosAires),
				circuitBuenosAiresRioDeJaniero.getStretchs());
	}

	@Test
	void hasATerminal_ShouldReturnTrue_ForTerminalBuenosAires_InCircuitBuenosAiresRioDeJaneiro() {
		assertTrue(circuitBuenosAiresRioDeJaniero.hasATerminal(buenosAires));
	}

	@Test
	void calculateTimeBetween_ShouldReturnCorrectHours_ForCircuitBuenosAiresRioDeJaneiro() {
		assertEquals(11, circuitBuenosAiresRioDeJaniero.calculateTimeBetween(buenosAires, rioDeJaneiro));
	}

	@Test
	void getPositionOf_ShouldReturnCorrectPosition_ForMontevideo_InCircuitBuenosAiresRioDeJaneiro() {
		assertEquals(1, circuitBuenosAiresRioDeJaniero.getPositionOf(montevideo));
	}

	@Test
	void getPrice_ShouldReturnCorrectPrice_ForCircuitBuenosAiresRioDeJaneiro() {
		assertEquals(2800.00, circuitBuenosAiresRioDeJaniero.getPrice());
	}

	@Test
	void originTerminal_ShouldReturnBuenosAires_ForCircuitBuenosAiresRioDeJaneiro() {
		assertEquals(buenosAires, circuitBuenosAiresRioDeJaniero.originTerminal());
	}
}