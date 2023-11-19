package shippingCompany;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.time.Duration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import maritimeCircuit.MaritimeCircuit;
import section.Section;
import terminal.Terminal;

class ShippingCompanyTest {

	private ShippingCompany company; // SUT
	private Terminal terminalBuenosAires; // DOC
	private Terminal terminalMontevideo; // DOC
	private Terminal terminalRioDeJaneiro; // DOC
	private Section sectionRioDeJaneiroMontevideo; // DOC
	private Section sectionMontevideoRioDeJaneiro; // DOC
	private Section sectionBuenosAiresMontevideo; // DOC
	private Section sectionMontevideoBuenosAires; // DOC
	private MaritimeCircuit maerskLine; // DOC
	private MaritimeCircuit maerskLineTwo; // DOC

	@BeforeEach
	void setUp() {
		company = new ShippingCompany(42341174, "Company");
		terminalBuenosAires = mock(Terminal.class);
		terminalMontevideo = mock(Terminal.class);
		terminalRioDeJaneiro = mock(Terminal.class);
		sectionBuenosAiresMontevideo = new Section(terminalBuenosAires, terminalMontevideo, 50.0, Duration.ofHours(2));
		sectionMontevideoBuenosAires = new Section(terminalMontevideo, terminalBuenosAires, 20.0, Duration.ofHours(4));
		sectionRioDeJaneiroMontevideo = new Section(terminalRioDeJaneiro, terminalMontevideo, 70.0,
				Duration.ofHours(1));
		sectionMontevideoRioDeJaneiro = new Section(terminalMontevideo, terminalRioDeJaneiro, 100.0,
				Duration.ofHours(10));
		maerskLine = new MaritimeCircuit();
		maerskLineTwo = new MaritimeCircuit();

		maerskLine.addSection(sectionBuenosAiresMontevideo);
		maerskLine.addSection(sectionMontevideoBuenosAires);

		maerskLineTwo.addSection(sectionRioDeJaneiroMontevideo);
		maerskLineTwo.addSection(sectionMontevideoRioDeJaneiro);

	}

	@Test
	void testAShippingCompanyHasTwoMaritimeCircuits() {

		company.addMaritimeCircuit(maerskLine);
		company.addMaritimeCircuit(maerskLineTwo);

		assertEquals(2, company.getMaritimeCircuits().size());

	}

	@Test
	void testAShippingCompanyHasMaritimeCircuitsWhereTheTerminalIsLocated() {

		company.addMaritimeCircuit(maerskLine);
		company.addMaritimeCircuit(maerskLineTwo);

		assertEquals(1, company.maritimeCircuitsWhereTheTerminal(terminalBuenosAires).size());

	}

	@Test
	void testAShippingCompanyHasNotAMaritimeCircuitsWhereTheTerminalIsLocated() {

		company.addMaritimeCircuit(maerskLineTwo);

		assertEquals(0, company.maritimeCircuitsWhereTheTerminal(terminalBuenosAires).size());

	}

}