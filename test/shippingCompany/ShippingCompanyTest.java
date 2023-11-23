package shippingCompany;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import maritimeCircuit.MaritimeCircuit;
import section.Section;
import terminal.Terminal;
import trip.Trip;

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
	
	private MaritimeCircuit circuitA = mock(MaritimeCircuit.class);
	private MaritimeCircuit circuitB = mock(MaritimeCircuit.class);
	private Trip tripA = mock(Trip.class);
	private Trip tripB = mock(Trip.class);
	private Trip tripC = mock(Trip.class);

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
	
	@Test
	void testGivenADate_returnsAListOfTripsThatStartOnThatDate(){
		company.addTrip(tripA);
		company.addTrip(tripB);
		company.addTrip(tripC);
		List<Trip> expectedTrips = new ArrayList<Trip>();
		expectedTrips.add(tripA);
		expectedTrips.add(tripB);
		LocalDate startDateSearched = LocalDate.of(2023, 10, 10);
		when(tripA.getStartDate()).thenReturn(startDateSearched);
		when(tripB.getStartDate()).thenReturn(startDateSearched);
		when(tripC.getStartDate()).thenReturn(LocalDate.now());
		
		assertEquals(expectedTrips, company.getTripsThatStartOn(startDateSearched));
	}
	
	@Test
	void testGivenADate_returnsAListOfCircuitsWithTripsThatStartOnThatDate(){
		company.addTrip(tripA);
		company.addTrip(tripB);
		company.addTrip(tripC);
		List<MaritimeCircuit> expectedCircuits = new ArrayList<MaritimeCircuit>();
		expectedCircuits.add(circuitA);
		expectedCircuits.add(circuitB);
		LocalDate startDateSearched = LocalDate.of(2023, 10, 10);
		when(tripA.getStartDate()).thenReturn(startDateSearched);
		when(tripB.getStartDate()).thenReturn(startDateSearched);
		when(tripC.getStartDate()).thenReturn(LocalDate.now());
		when(tripA.getMaritimeCircuit()).thenReturn(circuitA);
		when(tripB.getMaritimeCircuit()).thenReturn(circuitB);
		
		assertEquals(expectedCircuits, company.getCircuitsWithTripsThatStartOn(startDateSearched));
	}

}