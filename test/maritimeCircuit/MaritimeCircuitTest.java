package maritimeCircuit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.time.Duration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import section.Section;
import terminal.Terminal;

class MaritimeCircuitTest {

	private Terminal terminalBuenosAires; // DOC
	private Terminal terminalMontevideo; // DOC
	private Terminal terminalRioDeJaneiro; // DOC
	private Section sectionBuenosAiresMontevideo; // DOC
	private Section sectionMontevideoBuenosAires; // DOC
	private MaritimeCircuit maerskLine; // SUT

	@BeforeEach
	void setUp() {
		terminalBuenosAires = mock(Terminal.class);
		terminalMontevideo = mock(Terminal.class);
		terminalRioDeJaneiro = mock(Terminal.class);
		sectionBuenosAiresMontevideo = new Section(terminalBuenosAires, terminalMontevideo, 50.0, Duration.ofHours(2));
		sectionMontevideoBuenosAires = new Section(terminalMontevideo, terminalBuenosAires, 20.0, Duration.ofHours(4));

		maerskLine = new MaritimeCircuit(); // SUT

		maerskLine.addSection(sectionBuenosAiresMontevideo);
		maerskLine.addSection(sectionMontevideoBuenosAires);

	}

	@Test
	void testAMaritimeCircuitHasATwoSections() {
		assertEquals(2, maerskLine.getSections().size());
	}

	@Test
	void testAMaritimeCircuitCalculatesTheTotalPriceBetweenAllItsSections() {
		assertEquals(70.00, maerskLine.getPrice());
	}

	@Test
	void AMaritimeCircuitKnowsIfItHasACertainTerminalInItsSections() {
		assertTrue(maerskLine.itHasASectionWhereItIs(terminalBuenosAires));
	}

	@Test
	void AMaritimeCircuitKnowsIfItHasNotACertainTerminalInItsSections() {
		assertFalse(maerskLine.itHasASectionWhereItIs(terminalRioDeJaneiro));

	}

}
