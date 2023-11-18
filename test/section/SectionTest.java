package section;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.time.Duration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import terminal.Terminal;

class SectionTest {

	private Terminal terminalBuenosAires; // DOC
	private Terminal terminalMontevideo; // DOC
	private Terminal terminalRioDeJaneiro; // DOC
	private Section sectionBuenosAiresMontevideo; // SUT
	private Section sectionMontevideoRioDeJainero; // SUT

	@BeforeEach
	void setUp() {
		terminalBuenosAires = mock(Terminal.class);
		terminalMontevideo = mock(Terminal.class);
		terminalRioDeJaneiro = mock(Terminal.class);
		sectionBuenosAiresMontevideo = new Section(terminalBuenosAires, terminalMontevideo, 50.0, Duration.ofHours(2));
		sectionMontevideoRioDeJainero = new Section(terminalMontevideo, terminalRioDeJaneiro, 20.0,
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