package maritimeCircuit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
	// ------------------------------------------------------------
	private Terminal valparaiso;
	private Terminal lima;
	private Terminal guayaquil;
//-------------------------------------------------------------
	private Stretch buenosAiresValparaiso;
	private Stretch valparaisoLima;
	private Stretch limaGuayaquil;
	private Stretch guayaquilBuenosAires;
//-------------------------------------------------------------
	private MaritimeCircuit maritimeCircuitOne; // SUT

	@BeforeEach
	void setUp() {
		buenosAires = mock(ManagedTerminal.class);
		// ------------------------------------------------------------------------------------------
		valparaiso = mock(Terminal.class);
		lima = mock(Terminal.class);
		guayaquil = mock(Terminal.class);
		// ------------------------------------------------------------------------------------------
		buenosAiresValparaiso = mock(Stretch.class);
		when(buenosAiresValparaiso.getOrigin()).thenReturn(buenosAires);
		when(buenosAiresValparaiso.getDestiny()).thenReturn(valparaiso);
		when(buenosAiresValparaiso.getTime()).thenReturn(Duration.ofHours(13));
		when(buenosAiresValparaiso.getPrice()).thenReturn(1040.00);
		when(buenosAiresValparaiso.hasTerminal(buenosAires)).thenReturn(true);

		valparaisoLima = mock(Stretch.class);
		when(valparaisoLima.getOrigin()).thenReturn(valparaiso);
		when(valparaisoLima.getDestiny()).thenReturn(lima);
		when(valparaisoLima.getTime()).thenReturn(Duration.ofHours(9));
		when(valparaisoLima.getPrice()).thenReturn(2040.00);

		limaGuayaquil = mock(Stretch.class);
		when(limaGuayaquil.getOrigin()).thenReturn(lima);
		when(limaGuayaquil.getDestiny()).thenReturn(guayaquil);
		when(limaGuayaquil.getTime()).thenReturn(Duration.ofHours(6));
		when(limaGuayaquil.getPrice()).thenReturn(1821.00);
		when(limaGuayaquil.hasTerminal(lima)).thenReturn(true);

		guayaquilBuenosAires = mock(Stretch.class);
		when(guayaquilBuenosAires.getOrigin()).thenReturn(guayaquil);
		when(guayaquilBuenosAires.getDestiny()).thenReturn(buenosAires);
		when(guayaquilBuenosAires.getTime()).thenReturn(Duration.ofHours(36));
		when(guayaquilBuenosAires.getPrice()).thenReturn(2192.00);
		// ------------------------------------------------------------------------------------------
		maritimeCircuitOne = new MaritimeCircuit(
				List.of(buenosAiresValparaiso, valparaisoLima, limaGuayaquil, guayaquilBuenosAires));
	}

	@Test
	void testStretchesInMaritimeCircuitOne() {
		assertEquals(List.of(buenosAiresValparaiso, valparaisoLima, limaGuayaquil, guayaquilBuenosAires),
				maritimeCircuitOne.getStretches());
	}

	@Test
	void testGetPositionOfTerminalBuenosAiresInMaritimeCircuitOne() throws Exception {
		assertEquals(0, maritimeCircuitOne.getPositionOf(buenosAires));
	}

	@Test
	void testGetPositionOfUnknownTerminalInMaritimeCircuitOneThrowsException() throws Exception {
		assertThrows(Exception.class, () -> maritimeCircuitOne.getPositionOf(mock(Terminal.class)),
				"Terminal not found");
	}

	@Test
	void testGetPriceOfMaritimeCircuitOne() {
		assertEquals(7093.00, maritimeCircuitOne.getPrice());
	}

	@Test
	void testGetTimeOfMaritimeCircuitOne() {
		assertEquals(64, maritimeCircuitOne.getTime());
	}

	@Test
	void testCalculateTotalHoursBetweenTerminalsInMaritimeCircuitOne() throws Exception {
		assertEquals(22, maritimeCircuitOne.calculateTotalHoursBetweenTerminals(buenosAires, lima));
	}

	@Test
	void testHasTerminalBuenosAiresInMaritimeCircuitOne() {
		assertTrue(maritimeCircuitOne.hasATerminal(buenosAires));
	}

	@Test
	void TestHasUnknownTerminalInMaritimeCircuitOneReturnsFalse() {
		assertFalse(maritimeCircuitOne.hasATerminal(mock(Terminal.class)));
	}

	@Test
	void testOriginTerminalOfMaritimeCircuitOne() {
		assertEquals(buenosAires, maritimeCircuitOne.originTerminal());
	}

	@Test
	void testGetNextTerminalInCircuitFromBuenosAiresInMaritimeCircuitOne() {
		assertEquals(valparaiso, maritimeCircuitOne.getNextTerminalInCircuit(buenosAires));
	}
}
