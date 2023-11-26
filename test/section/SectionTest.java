package section;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.time.Duration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import stretch.Stretch;
import terminal.Terminal;

class SectionTest {

	private Terminal terminalBuenosAires; // DOC
	private Terminal terminalMontevideo; // DOC
	private Terminal terminalRioDeJaneiro; // DOC
	private Stretch sectionBuenosAiresMontevideo; // SUT
	private Stretch sectionMontevideoRioDeJainero; // SUT

	@BeforeEach
	void setUp() {
		terminalBuenosAires = mock(Terminal.class);
		terminalMontevideo = mock(Terminal.class);
		terminalRioDeJaneiro = mock(Terminal.class);
		sectionBuenosAiresMontevideo = new Stretch(terminalBuenosAires, terminalMontevideo, 50.0, Duration.ofHours(2));
		sectionMontevideoRioDeJainero = new Stretch(terminalMontevideo, terminalRioDeJaneiro, 20.0,
				Duration.ofHours(4));

	}

	@Test
	void testASectionHasACertainTerminal() {
		assertTrue(sectionBuenosAiresMontevideo.isItHasATerminal(terminalBuenosAires));
	}

	@Test
	void testASectionHasNotACertainTerminal() {
		assertFalse(sectionMontevideoRioDeJainero.isItHasATerminal(terminalBuenosAires));
	}

}