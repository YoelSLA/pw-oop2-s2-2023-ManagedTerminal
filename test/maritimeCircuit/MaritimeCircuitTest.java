package maritimeCircuit;

import static org.junit.Assert.assertFalse;
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

	private ManagedTerminal buenosAires;
//-------------------------------------------------------------
	private Terminal montevideo;
	private Terminal rioDeJaneiro;
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
//-------------------------------------------------------------		
		// TERMINAL
		montevideo = mock(Terminal.class);
		rioDeJaneiro = mock(Terminal.class);
//-------------------------------------------------------------		
		// STRETCH
		buenosAiresMontevideo = mock(Stretch.class);
		when(buenosAiresMontevideo.getOrigin()).thenReturn(buenosAires);
		when(buenosAiresMontevideo.getDestiny()).thenReturn(montevideo);
		when(buenosAiresMontevideo.getTime()).thenReturn(Duration.ofHours(3));

		montevideoRioDeJaniero = mock(Stretch.class);
		when(montevideoRioDeJaniero.getOrigin()).thenReturn(montevideo);
		when(montevideoRioDeJaniero.getDestiny()).thenReturn(rioDeJaneiro);
		when(montevideoRioDeJaniero.getTime()).thenReturn(Duration.ofHours(8));

		rioDeJaneiroBuenosAires = mock(Stretch.class);
		when(rioDeJaneiroBuenosAires.getOrigin()).thenReturn(rioDeJaneiro);
		when(rioDeJaneiroBuenosAires.getDestiny()).thenReturn(buenosAires);
		when(rioDeJaneiroBuenosAires.getTime()).thenReturn(Duration.ofHours(16));
//-------------------------------------------------------------		
		// MARITIME CIRCUIT
		circuitBuenosAiresRioDeJaniero = new MaritimeCircuit(
				List.of(buenosAiresMontevideo, montevideoRioDeJaniero, rioDeJaneiroBuenosAires));
	}

	@Test
	void getStretches_ShouldReturnCorrectStretchs_ForCircuitBuenosAiresRioDeJaneiro() {
		assertEquals(List.of(buenosAiresMontevideo, montevideoRioDeJaniero, rioDeJaneiroBuenosAires),
				circuitBuenosAiresRioDeJaniero.getStretches());
	}

	@Test
	void hasATerminal_ShouldReturnFalse_ForMockedTerminal_InCircuitBuenosAiresRioDeJaneiro() {
		assertFalse(circuitBuenosAiresRioDeJaniero.hasATerminal(mock(Terminal.class)));
	}

	@Test
	void hasATerminal_ShouldReturnTrue_ForTerminalBuenosAires_InCircuitBuenosAiresRioDeJaneiro() {
		assertTrue(circuitBuenosAiresRioDeJaniero.hasATerminal(buenosAires));
	}

	@Test
	void calculateTotalHoursBetweenTerminals_ShouldReturnCorrectHours_ForCircuitBuenosAiresRioDeJaneiro() {
		assertEquals(11, circuitBuenosAiresRioDeJaniero.calculateTotalHoursBetweenTerminals(buenosAires, rioDeJaneiro));
	}

	@Test
	void getTime_ShouldReturnCorrectTime_ForCircuitBuenosAiresRioDeJaneiro() {
		assertEquals(27, circuitBuenosAiresRioDeJaniero.getTime());
	}

	@Test
	void getPositionOf_ShouldReturnCorrectPosition_ForMontevideo_InCircuitBuenosAiresRioDeJaneiro() {
		assertEquals(1, circuitBuenosAiresRioDeJaniero.getPositionOf(montevideo));
	}

	@Test
	void getPrice_ShouldReturnCorrectPrice_ForCircuitBuenosAiresRioDeJaneiro() {
		// Exercise
		when(buenosAiresMontevideo.getPrice()).thenReturn(500.0);
		when(montevideoRioDeJaniero.getPrice()).thenReturn(800.0);
		when(rioDeJaneiroBuenosAires.getPrice()).thenReturn(1500.0);
		// Assert
		assertEquals(2800.00, circuitBuenosAiresRioDeJaniero.getPrice());
	}

	@Test
	void originTerminal_ShouldReturnBuenosAires_ForCircuitBuenosAiresRioDeJaneiro() {
		assertEquals(buenosAires, circuitBuenosAiresRioDeJaniero.originTerminal());
	}
}
