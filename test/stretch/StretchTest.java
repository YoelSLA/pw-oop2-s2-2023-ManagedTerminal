package stretch;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import java.time.Duration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import terminal.ManagedTerminal;
import terminal.Terminal;

class StretchTest {

	private ManagedTerminal buenosAires;
//-------------------------------------------------------------
	private Terminal montevideo;
//-------------------------------------------------------------
	private Stretch buenosAiresMontevideo; // SUT

	@BeforeEach
	void setUp() throws Exception {
		// MANAGED TERMINAL
		buenosAires = mock(ManagedTerminal.class);
//-------------------------------------------------------------
		// TERMINAL
		montevideo = mock(Terminal.class);
//-------------------------------------------------------------
		// STRETCH
		buenosAiresMontevideo = new Stretch(buenosAires, montevideo, 200.0, Duration.ofHours(6));
	}

	@Test
	void getDestiny_ShouldReturnMontevideo_ForStretchBuenosAiresMontevideo() {
		assertEquals(montevideo, buenosAiresMontevideo.getDestiny());
	}

	@Test
	void getOrigin_ShouldReturnBuenosAires_ForStretchBuenosAiresMontevideo() {
		assertEquals(buenosAires, buenosAiresMontevideo.getOrigin());
	}

	@Test
	void getPrice_ShouldReturn50_ForStretchBuenosAiresMontevideo() {
		assertEquals(200.0, buenosAiresMontevideo.getPrice());
	}

	@Test
	void getTime_ShouldReturn1Hour_ForStretchBuenosAiresMontevideo() {
		assertEquals(Duration.ofHours(6), buenosAiresMontevideo.getTime());
	}

	@Test
	void setPrice_ShouldSetCorrectPrice_ForStretchBuenosAiresMontevideo() throws Exception {
		// Exercise
		buenosAiresMontevideo.setPrice(200.0);
		// Assert
		assertEquals(200.0, buenosAiresMontevideo.getPrice());
	}

	@Test
	void constructor_ShouldThrowRuntimeException_WhenNegativePriceProvided() {
		assertThrows(RuntimeException.class, () -> buenosAiresMontevideo.setPrice(-20.0));
	}

	@Test
	void hasATerminal_ShouldReturnTrue_ForOrigin_InStretchBuenosAiresMontevideo() {
		assertTrue(buenosAiresMontevideo.hasTerminal(buenosAires));
	}

	@Test
	void hasATerminal_ShouldReturnTrue_ForDestiny_InStretchBuenosAiresMontevideo() {
		assertTrue(buenosAiresMontevideo.hasTerminal(montevideo));
	}

	@Test
	void hasATerminal_ShouldReturnFalse_ForMockedTerminal_InStretchBuenosAiresMontevideo() {
		assertFalse(buenosAiresMontevideo.hasTerminal(mock(Terminal.class)));
	}

	@Test
	void setTime_ShouldSetCorrectDuration_ForStretchBuenosAiresMontevideo() {
		// Exercise
		buenosAiresMontevideo.setTime(Duration.ofHours(10));
		// Assert
		assertEquals(Duration.ofHours(10), buenosAiresMontevideo.getTime());
	}

}