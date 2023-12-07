package routing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import terminal.Terminal;

class ShorterTimeTest extends RoutingTest {

	private ShorterTime shorterTime;

	@BeforeEach
	void setUp() throws Exception {
		super.setUp();
		// ------------------------------------------------------------------------------------------
		when(buenosAiresValparaiso.getTime()).thenReturn(Duration.ofHours(13));
		when(valparaisoLima.getTime()).thenReturn(Duration.ofHours(9));
		when(limaGuayaquil.getTime()).thenReturn(Duration.ofHours(6));
		when(guayaquilBuenosAires.getTime()).thenReturn(Duration.ofHours(36));
		when(montevideoBuenosAires.getTime()).thenReturn(Duration.ofHours(4));
		when(limaMontevideo.getTime()).thenReturn(Duration.ofHours(26));
		// ------------------------------------------------------------------------------------------
		when(maritimeCircuitOne.getPositionOf(buenosAires)).thenReturn(1);
		when(maritimeCircuitOne.getPositionOf(lima)).thenReturn(3);

		when(maritimeCircuitTwo.getPositionOf(buenosAires)).thenReturn(0);
		when(maritimeCircuitTwo.getPositionOf(lima)).thenReturn(2);
		// ------------------------------------------------------------------------------------------
		shorterTime = new ShorterTime();
	}

	@Test
	void testBestCircuitBetween_ReturnsShortestCircuit_WhenMultipleCircuitsExist() throws Exception {
		assertEquals(maritimeCircuitOne,
				shorterTime.bestCircuitBetween(buenosAires, lima, List.of(maritimeCircuitOne, maritimeCircuitTwo)));
	}

	@Test
	void testBestCircuitBetween_ThrowsException_WhenCircuitListIsEmpty() {
		assertThrows(RuntimeException.class, () -> shorterTime.bestCircuitBetween(buenosAires, lima, List.of()),
				"The maritime circuit must not be empty."); // TODO: REVISAR PARA LA COBERTURA
	}

	@Test
	void testBestCircuitBetween_ThrowsException_WhenDestinationNotInMaritimeCircuits() {
		Terminal rioDeJaneiro = mock(Terminal.class);
		assertThrows(RuntimeException.class,
				() -> shorterTime.bestCircuitBetween(buenosAires, rioDeJaneiro,
						List.of(maritimeCircuitOne, maritimeCircuitTwo)),
				"The destination must be in the maritime circuits."); // TODO: REVISAR PARA LA COBERTURA
	}

}
